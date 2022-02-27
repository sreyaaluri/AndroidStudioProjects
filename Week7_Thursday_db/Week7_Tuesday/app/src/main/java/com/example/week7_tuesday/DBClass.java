package com.example.week7_tuesday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBClass extends SQLiteOpenHelper {

    private static final String ID_COL = "id";
    private String TABLE_NAME="Info";
    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String AGE_COL = "age";


    public DBClass(Context context,String DATABASE_NAME){
        super(context,DATABASE_NAME,null,1);
    }
    //default constructor, factory points to cursor factory->allow cursor, version of db
    public void onCreate(SQLiteDatabase db) { //called when db is created for the first time.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + AGE_COL + " INTEGER)";

        db.execSQL(query);
        db.close();
    }
    public void deletetable(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry="DELETE FROM "+table_name;
        db.execSQL(qry);
        db.close();
    }
    public void addInfo(Integer i,String name, String age) {

        ContentValues values = new ContentValues(); //used to store set of values.
        SQLiteDatabase db = this.getWritableDatabase(); //Opensource db

        values.put(NAME_COL, name);
        values.put(AGE_COL, Integer.parseInt(age));
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
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

    public String selectConditionQuery(String fieldname,String condition){
        String fieldvalue;
        String query="SELECT "+fieldname+" FROM "+TABLE_NAME+" WHERE "+condition;
        Log.d("==SelectCondition===","====="+query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur=db.rawQuery(query,null); //Runs the provided SQL and returns a Cursor over the result set.


        //  extract.moveToLast();
        cur.moveToFirst();
        Log.d("==SelectCondition===","====="+cur.getString(0));

        String val=cur.getString(0)+" ";  //first record
        while (cur.moveToNext()) {  //contains all rows. traversing
            // if (cur.getInt(1)) {
            val+= cur.getString(0)+" ";
            Log.d("==SelectCondition===","====="+val);
            }

        cur.close();

        db.close();
        return val;

    }

    public void updateTable(String field1,String fieldvalue1,String field2,String fieldvalue2){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET"+" "+field1 +"="+"'"+fieldvalue1+"',"+field2+"="+"'"+fieldvalue2+"'"); //special character.
        db.close();
    }
    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //Called when the database needs to be upgraded. The implementation should use this method to drop tables, add tables, or do anything else it needs to upgrade to the new schema version.
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }}