package com.example.moodtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBClass extends SQLiteOpenHelper {
    //variables for table and column names in loginQuery
    private String TABLE_ONE_NAME="UserInfo";
    private static final String UNAME_COL = "username";
    private static final String PASSWORD_COL = "password";
    private static final String NAME_COL = "name";
    private static final String AGE_COL = "age";
    private static final String GENDER_COL = "gender";

    //variables for table and column names in diaryQuery
    private String TABLE_TWO_NAME="diary";
    private static final String DATE_COL = "date";
    private static final String NOTES_COL = "notes";
    private static final String EXERCISED_COL = "exercised";
    private static final String OUTSIDE_COL = "outside";
    private static final String SOCIALIZED_COL = "socialized";
    private static final String MEDITATED_COL = "meditated";
    private static final String READ_COL = "read";

    //variables for table and column names in diaryQuery
    private String TABLE_THREE_NAME="Tracker";
    /*private static final String UNAME_COL = "username";
    private static final String EXERCISED_COL = "exercised";
    private static final String OUTSIDE_COL = "outside";
    private static final String SOCIALIZED_COL = "socialized";
    private static final String MEDITATED_COL = "meditated";
    private static final String READ_COL = "read"; */


    // default constructor, factory points to cursor factory->allow cursor, version of db
    public DBClass(Context context, String DATABASE_NAME){
        super(context, DATABASE_NAME,null,1);
    }

    // called when db is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        //user credentials table
        String loginQuery = "CREATE TABLE" + TABLE_ONE_NAME  + "("
                + UNAME_COL + " TEXT PRIMARY KEY, "
                + PASSWORD_COL + " TEXT, "
                + NAME_COL + " TEXT, "
                + AGE_COL + " INTEGER, "
                + GENDER_COL + " TEXT)";

        String diaryQuery = "CREATE TABLE" + TABLE_TWO_NAME + "("
                + UNAME_COL + " TEXT PRIMARY KEY, "
                + DATE_COL + " TEXT, "
                + NOTES_COL + " TEXT, "
                + EXERCISED_COL + " INTEGER, " // boolean
                + OUTSIDE_COL + " INTEGER, " // boolean
                + SOCIALIZED_COL + " INTEGER, " // boolean
                + MEDITATED_COL + " INTEGER, " // boolean
                + READ_COL + " INTEGER)"; // boolean

        // TODO modify tracker columns - sreya
        String trackerQuery = "CREATE TABLE" + TABLE_THREE_NAME + "("
                + UNAME_COL + " TEXT PRIMARY KEY, "
                + EXERCISED_COL + " INTEGER, " // boolean
                + OUTSIDE_COL + " INTEGER, " // boolean
                + SOCIALIZED_COL + " INTEGER, " // boolean
                + MEDITATED_COL + " INTEGER, " // boolean
                + READ_COL + " INTEGER)"; // boolean

        // running the queries
//        db.execSQL(loginQuery);
//        db.execSQL(diaryQuery);
//        db.execSQL(trackerQuery);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //Called when the database needs to be upgraded. The implementation should use this method to drop tables, add tables, or do anything else it needs to upgrade to the new schema version.
        // TODO add relevant code
        // this method is called to check if the table exists already.
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    // TODO NOTE: everything after this point has not been adapted for this project
//
//    public void deletetable(String table_name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String qry="DELETE FROM "+table_name;
//        db.execSQL(qry);
//        db.close();
//    }
//    public void addInfo(Integer i,String name, String age) {
//
//        ContentValues values = new ContentValues(); //used to store set of values.
//        SQLiteDatabase db = this.getWritableDatabase(); //Opensource db
//
//        values.put(NAME_COL, name);
//        values.put(AGE_COL, Integer.parseInt(age));
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }
      public String selectQuery(String fieldname){
          String fieldvalue;
          String query="SELECT "+fieldname+" FROM "+TABLE_NAME;
          SQLiteDatabase db = this.getReadableDatabase();
          Cursor cur=db.rawQuery(query,null); //Runs the provided SQL and returns a Cursor over the result set.


          //  extract.moveToLast();
          cur.moveToFirst();
          String val="";
          while (cur.moveToNext()) {  //contains all rows. traversing
              // if (cur.getInt(1)) {
              String title = cur.getString(0);
              Log.d("Check cursor","====="+title);
              if(cur.getPosition()==cur.getCount()-1){  //getting last row.
                  val=cur.getString(0);
              }
          }
          cur.close();

          db.close();
          return val;

      }
//
//    public String selectConditionQuery(String fieldname,String condition){
//        String fieldvalue;
//        String query="SELECT "+fieldname+" FROM "+TABLE_NAME+" WHERE "+condition;
//        Log.d("==SelectCondition===","====="+query);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cur=db.rawQuery(query,null); //Runs the provided SQL and returns a Cursor over the result set.
//
//
//        //  extract.moveToLast();
//        cur.moveToFirst();
//        Log.d("==SelectCondition===","====="+cur.getString(0));
//
//        String val=cur.getString(0)+" ";  //first record
//        while (cur.moveToNext()) {  //contains all rows. traversing
//            // if (cur.getInt(1)) {
//            val+= cur.getString(0)+" ";
//            Log.d("==SelectCondition===","====="+val);
//        }
//
//        cur.close();
//
//        db.close();
//        return val;
//
//    }
//
      public void updateTable(String field1,String fieldvalue1,String field2,String fieldvalue2){
          SQLiteDatabase db = this.getReadableDatabase();
          db.execSQL("UPDATE "+TABLE_NAME+" SET"+" "+field1 +"="+"'"+fieldvalue1+"',"+field2+"="+"'"+fieldvalue2+"'"); //special character.
          db.close();
      }
}