package com.example.moodtracker;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DBClass extends SQLiteOpenHelper {
    // singleton instance of database to use across entire application
    private static DBClass database;

    // database name
    private static final String DATABASE_NAME = "UserInfo";

    // error tag for SQLite
    private static final String ERROR_TAG="FIX";

    // variables for table and column names in loginQuery
    private String TABLE_USER_INFO="Users";
    private static final String UNAME_COL = "username";
    private static final String PASSWORD_COL = "password";
    private static final String NAME_COL = "name";
    private static final String AGE_COL = "age";
    private static final String GENDER_COL = "gender";

    // variables for table and column names in diaryQuery
    private String TABLE_DIARY="Diary";
    // private static final String UNAME_COL = "username";
    private static final String DATE_COL = "date";
    private static final String NOTES_COL = "notes";
    private static final String EXERCISED_COL = "exercised";
    private static final String OUTSIDE_COL = "outside";
    private static final String SOCIALIZED_COL = "socialized";
    private static final String MEDITATED_COL = "meditated";
    private static final String READ_COL = "read";

    // variables for table and column names in diaryQuery
    private String TABLE_TRACKER="Tracker";
    // private static final String UNAME_COL = "username";
    // private static final String DATE_COL = "date";
    private static final String MOOD_COL = "mood";
    private static final String ANXIETY_COL = "anxiety";
    private static final String MEDICATION_COL = "medication";
    private static final String MEDICATION_TIME_COL = "medication_time";


    // method to use the database instance outside DBclass across application
    public static synchronized DBClass getDBInstance(Context context) {
        if (database == null) {
            database = new DBClass(context.getApplicationContext());
        }
        return database;
    }

    // private constructor to support use of one database across entire application
    private DBClass(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    // called when db is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        //user credentials table
        String loginQuery = "CREATE TABLE " + TABLE_USER_INFO  + "("
                + UNAME_COL + " TEXT PRIMARY KEY, "
                + PASSWORD_COL + " TEXT, "
                + NAME_COL + " TEXT, "
                + AGE_COL + " INTEGER, "
                + GENDER_COL + " TEXT)";

        String diaryQuery = "CREATE TABLE " + TABLE_DIARY + "("
                + UNAME_COL + " TEXT, "
                + DATE_COL + " TEXT, "
                + NOTES_COL + " TEXT, "
                + EXERCISED_COL + " INTEGER, " // boolean
                + OUTSIDE_COL + " INTEGER, " // boolean
                + SOCIALIZED_COL + " INTEGER, " // boolean
                + MEDITATED_COL + " INTEGER, " // boolean
                + READ_COL + " INTEGER)"; // boolean

        String trackerQuery = "CREATE TABLE " + TABLE_TRACKER + "("
                + UNAME_COL + " TEXT, "
                + DATE_COL + " TEXT, "
                + MOOD_COL + " INTEGER, " // value 1-5
                + ANXIETY_COL + " INTEGER, " // value 0-5
                + MEDICATION_COL + " INTEGER, " // boolean
                + MEDICATION_TIME_COL + " INTEGER)"; // 0 - No, 1 - Yes, -1 - N/A

        // running the queries
        db.execSQL(loginQuery);
        db.execSQL(diaryQuery);
        db.execSQL(trackerQuery);
    }

    // called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dropping all tables that exist in schema old version and recreate new empty ones
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKER);
        onCreate(db);
    }

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

    // called to check if username and password match database records
    // returns -1 if user doesn't exist, 1 if success, 0 if fail
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
    }//end authenticateUser() method

    // called to add new user to database
    public void addUser(String username, String password, String name, int age, String gender) {
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
            values.put(AGE_COL, age);
            values.put(GENDER_COL, gender);

            // adding values to table
            db.insertOrThrow(TABLE_USER_INFO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to add new user to database");
        } finally { // closing the database
            db.endTransaction();
        }
    }//end addUser() method

    // called to add a new note to table with diary entries
    public void addDiaryEntry(String username, DiaryEntry de) {
        // open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // starting transaction
        db.beginTransaction();
        try { // adding row to table
            // including user's entries as field values
            ContentValues values = new ContentValues();
            values.put(UNAME_COL, username);
            values.put(DATE_COL, de.getDate());
            values.put(NOTES_COL, de.getNotes());
            values.put(EXERCISED_COL, de.getExercise());
            values.put(OUTSIDE_COL, de.getOutside());
            values.put(SOCIALIZED_COL, de.getSocialize());
            values.put(MEDITATED_COL, de.getMeditate());
            values.put(READ_COL, de.getRead());

            // adding values to table
            db.insertOrThrow(TABLE_DIARY, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to add diary entry to database");
        }
        finally { // closing the database
            db.endTransaction();
        }
    }

    //called to add wellness tracker data to tracker table
    public void addTrackerData(String username, String date, int mood, int anxiety, int medication, int medTime) {
        // open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // starting transaction
        db.beginTransaction();
        try { // adding row to table
            // including user's entries as field values
            ContentValues values = new ContentValues();
            values.put(UNAME_COL, username);
            values.put(DATE_COL, date);
            values.put(MOOD_COL, mood);
            values.put(ANXIETY_COL, anxiety);
            values.put(MEDICATION_COL, medication);
            values.put(MEDICATION_TIME_COL, medTime);

            // adding values to table
            db.insertOrThrow(TABLE_TRACKER, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to add tracker data to database");
        }
        finally { // closing the database
            db.endTransaction();
        }
    }

    // called to retrieve notes of a specific user from diary table
    @SuppressLint("Range")
    public List<DiaryEntry> retrieveAllDiaryEntries(String username) {
        // initialize list of diary entries
        List<DiaryEntry> entries = new ArrayList<DiaryEntry>();

        // select * from diary table for entries of specific user
        String ALL_ENTRIES_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = \'%s\' ORDER BY %s DESC", TABLE_DIARY, UNAME_COL, username, DATE_COL);

        // open the database for reading
        SQLiteDatabase db = getReadableDatabase();

        // run query and get cursor object
        Cursor cursor = db.rawQuery(ALL_ENTRIES_SELECT_QUERY, null);
        try { // reading data from cursor
            if (cursor.moveToFirst()) {
                do {
                    // creating a dairy entry with retrieved values
                    DiaryEntry de = new DiaryEntry();
                    de.setDate(cursor.getString(cursor.getColumnIndex(DATE_COL)));
                    de.setNotes(cursor.getString(cursor.getColumnIndex(NOTES_COL)));
                    de.setExercise(cursor.getInt(cursor.getColumnIndex(EXERCISED_COL)));
                    de.setOutside(cursor.getInt(cursor.getColumnIndex(OUTSIDE_COL)));
                    de.setSocialize(cursor.getInt(cursor.getColumnIndex(SOCIALIZED_COL)));
                    de.setMeditate(cursor.getInt(cursor.getColumnIndex(MEDITATED_COL)));
                    de.setRead(cursor.getInt(cursor.getColumnIndex(READ_COL)));
                    entries.add(de);
                } while(cursor.moveToNext());
            }
        }
        catch (Exception e) { // printing error in logcat
            Log.d(ERROR_TAG, "Error while trying to get diary entry from database");
        }
        finally { // closing the cursor and the database
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // returning a list of diary entries related to given user
        return entries;
    }

    // method to hash a password
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

    // helper for hashing a string
    public byte[] messageDigest(String s) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // digest() method called to calculate message digest of an input and return array of byte
        return md.digest(s.getBytes(StandardCharsets.UTF_8));
    }

}