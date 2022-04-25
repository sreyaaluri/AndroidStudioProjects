package com.example.habitbuilder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBClass extends SQLiteOpenHelper {
    // singleton instance of database to use across entire application
    private static DBClass database;

    // database name
    private static final String DATABASE_NAME = "UserInfo";

    // error tag for SQLite
    private static final String ERROR_TAG="---FIX";

    // variables for table and column names in Users Table (for authentication)
    private String TABLE_USER_INFO="Users";
    private static final String UNAME_COL = "username";
    private static final String PASSWORD_COL = "password";
    private static final String NAME_COL = "name";

    // variables for table and column names in Habits Table
    private String TABLE_HABITS="Habits";
    // private static final String UNAME_COL = "username";
    private static final String IDENTITY_COL = "identity";
    private static final String HABIT_COL = "habit";
    private static final String WHY_COL = "why";
    private static final String FREQ_COL = "frequency";
    private static final String REMIND_COL = "reminder";
    private static final String HR_COL = "hour";
    private static final String MIN_COL = "min";
    private static final String CUE_COL = "cue";
    private static final String CRAVING_COL = "craving";
    private static final String RESPONSE_COL = "response";
    private static final String REWARD_COL = "reward";

    // variables for table and column names in Scorecard Table
    private String TABLE_SCORECARD="Scorecard";
    // private static final String UNAME_COL = "username";
    private static final String DATETIME_COL = "datetime";
    private static final String ACTIVITY_COL = "activity";
    private static final String RATING_COL = "rating";

    /**
     * method to use the database instance outside DBclass across application
     * @param context
     * @return
     */
    public static synchronized DBClass getDBInstance(Context context) {
        if (database == null) {
            database = new DBClass(context.getApplicationContext());
        }
        return database;
    }

    /**
     * private constructor to support use of one database across entire application
     * @param context
     */
    private DBClass(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    // TODO edit here on

    /**
     * called when db is created for the first time
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //user credentials table
        String userinfoQuery = "CREATE TABLE " + TABLE_USER_INFO  + "("
                + UNAME_COL + " TEXT PRIMARY KEY, "
                + PASSWORD_COL + " TEXT, "
                + NAME_COL + " TEXT)";

        // habits table
        String habitsQuery = "CREATE TABLE " + TABLE_HABITS + "("
                + UNAME_COL + " TEXT, "
                + IDENTITY_COL + " TEXT, "
                + HABIT_COL + " TEXT, "
                + WHY_COL + " TEXT, "
                + FREQ_COL +" TEXT, "
                + REMIND_COL + " INTEGER, " // 0 reminder off, 1 reminder on
                + HR_COL + " INTEGER, "     // storing time for reminder
                + MIN_COL + " INTEGER, "
                + CUE_COL + " TEXT, "
                + CRAVING_COL + " TEXT, "
                + RESPONSE_COL + " TEXT, "
                + REWARD_COL + " TEXT)";

        // scorecard table
        String scorecardQuery = "CREATE TABLE " + TABLE_SCORECARD + "("
                + UNAME_COL + " TEXT, "
                + DATETIME_COL + " TEXT, "
                + ACTIVITY_COL + " TEXT, "
                + RATING_COL + " INTEGER)"; // -1 - Bad, 0 - Neutral, 1 - good

        // TODO add tracker table

        // running the queries
        db.execSQL(userinfoQuery);
        db.execSQL(habitsQuery);
        db.execSQL(scorecardQuery);
    }

    /**
     * called when the database needs to be upgraded.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dropping all tables that exist in schema old version and recreate new empty ones
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORECARD);
        onCreate(db);
    }

    /**
     * called to check if username is available
     * @param username
     * @return boolean whether usename is available
     */
    public boolean unameIsAvailable(String username){
        // open the database for reading
        SQLiteDatabase db = getReadableDatabase();

        // select username from diary table for entries of specific user
        String SELECT_QUERY =
                String.format("SELECT %s FROM %s WHERE %s = \'%s\'", UNAME_COL, TABLE_USER_INFO, UNAME_COL, username);

        // run query and get cursor object
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        try { // reading data from cursor
            if (cursor.moveToFirst()) { // username exists
                return false;
            }
            else { // user doesn't exist
                return true;
            }
        }
        catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to check if username exists");
            return false;
        }
        finally { // closing the cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    /**
     * called to check if username and password match database records
     * @param username
     * @param hashedPwd
     * @return -1 if user doesn't exist, 1 if success, 0 if fail
     */
    public int authenticateUser(String username, String hashedPwd) {
        // open the database for reading
        SQLiteDatabase db = getReadableDatabase();

        // select * from diary table for entries of specific user
        String SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = \'%s\'", TABLE_USER_INFO, UNAME_COL, username);

        // run query and get cursor object
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        try { // reading data from cursor
            if (cursor.moveToFirst()) { // user exists
                String savedPassword = cursor.getString(cursor.getColumnIndex(PASSWORD_COL));
                if(hashedPwd.equals(savedPassword))
                    return 1; // success
                else
                    return 0; // wrong password
            }
            else { // user doesn't exist
                return -1;    // user doesn't exist
            }
        }
        catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to read user info for authentication.");
            return -1;
        }
        finally { // closing the cursor and the database
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    /**
     * called to add new user to database
     * @param username
     * @param password
     * @param name
     */
    public void addUser(String username, String password, String name) {
        // open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // starting transaction
        db.beginTransaction();

        try {// adding row to table
            //including user's info as field values
            ContentValues values = new ContentValues();
            values.put(UNAME_COL, username);
            values.put(PASSWORD_COL, password);
            values.put(NAME_COL, name);

            // adding values to table
            db.insertOrThrow(TABLE_USER_INFO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to add new user to database");
        } finally { // closing the database
            db.endTransaction();
        }
    }

    /**
     * get the name of a user given username
     * @param username
     * @return name
     */
    public String getName(String username){
        // open the database for reading
        SQLiteDatabase db = getReadableDatabase();

        // run query and get cursor object
        String query="SELECT "+NAME_COL+" FROM "+TABLE_USER_INFO;
        Cursor cursor = db.rawQuery(query,null);

        // getting name from cursor object (note: assuming it exists as user has been authenticted)
        cursor.moveToFirst();
        String name = cursor.getString(0);
        cursor.close();
        db.close();
        return name;
    }

    /**
     * method to add a new habit for specific user to database
     * @param username
     * @param h
     */
    public void addHabit(String username, Habit h){
        // open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // starting transaction
        db.beginTransaction();

        try { // adding row to table
            // including user's entries as field values
            ContentValues values = new ContentValues();
            values.put(UNAME_COL, username);
            values.put(IDENTITY_COL, h.identity);
            values.put(HABIT_COL, h.hname);
            values.put(WHY_COL, h.why);
            values.put(FREQ_COL, h.freq);
            values.put(REMIND_COL, h.reminder ? 1 : 0);
            values.put(HR_COL, h.hr);
            values.put(MIN_COL, h.min);
            values.put(CUE_COL, h.cue);
            values.put(CRAVING_COL, h.craving);
            values.put(RESPONSE_COL, h.response);
            values.put(REWARD_COL, h.reward);

            // adding values to table
            db.insertOrThrow(TABLE_HABITS, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to add habit to database");
        }
        finally { // closing the database
            db.endTransaction();
        }
    }

    /**
     * get habits related to a user
     * @param username
     * @return list of habits from database
     */
    public ArrayList<Habit> getHabits(String username){
        // initialize list of habits =
        ArrayList<Habit> habits = new ArrayList<Habit>();

        // select * from habits table for entries of specific user
        String ALL_ENTRIES_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = \'%s\'", TABLE_HABITS, UNAME_COL, username);

        // open the database for reading
        SQLiteDatabase db = getReadableDatabase();

        // run query and get cursor object
        Cursor cursor = db.rawQuery(ALL_ENTRIES_SELECT_QUERY, null);
        try { // reading data from cursor
            if (cursor.moveToFirst()) {
                do {
                    // creating a habit with retrieved values
                    Habit h = new Habit();
                    h.identity = cursor.getString(cursor.getColumnIndex(IDENTITY_COL));
                    h.hname = cursor.getString(cursor.getColumnIndex(HABIT_COL));
                    h.why = cursor.getString(cursor.getColumnIndex(WHY_COL));
                    h.freq = cursor.getString(cursor.getColumnIndex(FREQ_COL));
                    h.reminder = cursor.getInt(cursor.getColumnIndex(REMIND_COL)) != 0;
                    h.hr = cursor.getInt(cursor.getColumnIndex(HR_COL));
                    h.min = cursor.getInt(cursor.getColumnIndex(MIN_COL));
                    h.cue = cursor.getString(cursor.getColumnIndex(CUE_COL));
                    h.craving = cursor.getString(cursor.getColumnIndex(CRAVING_COL));
                    h.response = cursor.getString(cursor.getColumnIndex(RESPONSE_COL));
                    h.reward = cursor.getString(cursor.getColumnIndex(REWARD_COL));
                    habits.add(h);
                } while(cursor.moveToNext());
            }
        }
        catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to get habits from database");
        }
        finally { // closing the cursor and the database
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // returning a list of habits related to given user
        return habits;
    }

    /**
     * Method to delete habit by name
     * @param username
     * @param hname
     */
    public void deleteHabitByName(String username, String hname){
        // open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // execute delete query
        String DELETE_QUERY =
                String.format("DELETE FROM %s WHERE %s = \'%s\' AND %s = \'%s\'",
                        TABLE_HABITS, UNAME_COL, username, HABIT_COL, hname);
        db.execSQL(DELETE_QUERY);
    }

    // TODO how to add notif for habit added after login cuz setnotif is called at login

    public void deleteNotifs(){
        // get habits that have notifications
    }


    private void setNotif(Habit h){

    }

    private void deleteNotif(Habit h){

    }


//    // TODO old stuff from here on
//    // we need this for the homepage, to extract user's preferred name from table
//    public String selectQuery(String fieldname){
//        String fieldvalue;
//        String query="SELECT "+fieldname+" FROM "+TABLE_USER_INFO;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cur=db.rawQuery(query,null); //Runs the provided SQL and returns a Cursor over the result set.
//
//        //  extract.moveToLast();
//        cur.moveToFirst();
//        String val="";
//        while (cur.moveToNext()) {  //contains all rows. traversing
//            // if (cur.getInt(1)) {
//            String title = cur.getString(0);
//            Log.d("Check cursor","====="+title);
//            if(cur.getPosition()==cur.getCount()-1){  //getting last row.
//                val=cur.getString(0);
//            }
//        }
//        cur.close();
//
//        db.close();
//        return val;
//    }
//
//    // called to add a new note to table with diary entries
//    public void addDiaryEntry(String username, DiaryEntry de) {
//        // open the database for writing
//        SQLiteDatabase db = getWritableDatabase();
//
//        // starting transaction
//        db.beginTransaction();
//        try { // adding row to table
//            // including user's entries as field values
//            ContentValues values = new ContentValues();
//            values.put(UNAME_COL, username);
//            values.put(DATE_COL, de.getDate());
//            values.put(NOTES_COL, de.getNotes());
//            values.put(EXERCISED_COL, de.getExercise());
//            values.put(OUTSIDE_COL, de.getOutside());
//            values.put(SOCIALIZED_COL, de.getSocialize());
//            values.put(MEDITATED_COL, de.getMeditate());
//            values.put(READ_COL, de.getRead());
//
//            // adding values to table
//            db.insertOrThrow(TABLE_DIARY, null, values);
//            db.setTransactionSuccessful();
//        }
//        catch (Exception e) { // printing error in logcat
//            Log.d(ERROR_TAG, "Error while trying to add diary entry to database");
//        }
//        finally { // closing the database
//            db.endTransaction();
//        }
//    }
//
//    //called to add wellness tracker data to tracker table
//    public void addTrackerData(String username, String date, int mood, int anxiety, int medication, int medTime) {
//        // open the database for writing
//        SQLiteDatabase db = getWritableDatabase();
//
//        // starting transaction
//        db.beginTransaction();
//        try { // adding row to table
//            // including user's entries as field values
//            ContentValues values = new ContentValues();
//            values.put(UNAME_COL, username);
//            values.put(DATE_COL, date);
//            values.put(MOOD_COL, mood);
//            values.put(ANXIETY_COL, anxiety);
//            values.put(MEDICATION_COL, medication);
//            values.put(MEDICATION_TIME_COL, medTime);
//
//            // adding values to table
//            db.insertOrThrow(TABLE_TRACKER, null, values);
//            db.setTransactionSuccessful();
//        }
//        catch (Exception e) { // printing error in logcat
//            Log.d(ERROR_TAG, "Error while trying to add tracker data to database");
//        }
//        finally { // closing the database
//            db.endTransaction();
//        }
//    }
//
//    // called to retrieve notes of a specific user from diary table
//    @SuppressLint("Range")
//    public List<DiaryEntry> retrieveAllDiaryEntries(String username) {
//        // initialize list of diary entries
//        List<DiaryEntry> entries = new ArrayList<DiaryEntry>();
//
//        // select * from diary table for entries of specific user
//        String ALL_ENTRIES_SELECT_QUERY =
//                String.format("SELECT * FROM %s WHERE %s = \'%s\' ORDER BY %s DESC", TABLE_DIARY, UNAME_COL, username, DATE_COL);
//
//        // open the database for reading
//        SQLiteDatabase db = getReadableDatabase();
//
//        // run query and get cursor object
//        Cursor cursor = db.rawQuery(ALL_ENTRIES_SELECT_QUERY, null);
//        try { // reading data from cursor
//            if (cursor.moveToFirst()) {
//                do {
//                    // creating a dairy entry with retrieved values
//                    DiaryEntry de = new DiaryEntry();
//                    de.setDate(cursor.getString(cursor.getColumnIndex(DATE_COL)));
//                    de.setNotes(cursor.getString(cursor.getColumnIndex(NOTES_COL)));
//                    de.setExercise(cursor.getInt(cursor.getColumnIndex(EXERCISED_COL)));
//                    de.setOutside(cursor.getInt(cursor.getColumnIndex(OUTSIDE_COL)));
//                    de.setSocialize(cursor.getInt(cursor.getColumnIndex(SOCIALIZED_COL)));
//                    de.setMeditate(cursor.getInt(cursor.getColumnIndex(MEDITATED_COL)));
//                    de.setRead(cursor.getInt(cursor.getColumnIndex(READ_COL)));
//                    entries.add(de);
//                } while(cursor.moveToNext());
//            }
//        }
//        catch (Exception e) { // printing error in logcat
//            Log.d(ERROR_TAG, "Error while trying to get diary entry from database");
//        }
//        finally { // closing the cursor and the database
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//
//        // returning a list of diary entries related to given user
//        return entries;
//    }

    /**
     * method to hash a password
     * @param password
     * @return hashed string
     */
    public String hashPassword(String password) {
        String hashPwd = "";
        try {
            byte [] hashedPwd = messageDigest(password);
            hashPwd = Base64.encodeToString(hashedPwd, 0);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashPwd;
    }

    /**
     * helper for hashing a string
     * @param s
     * @return hashed byte array
     * @throws NoSuchAlgorithmException
     */
    public byte[] messageDigest(String s) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // digest() method called to calculate message digest of an input and return array of byte
        return md.digest(s.getBytes(StandardCharsets.UTF_8));
    }

}