package com.example.week7_thursday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBClass extends SQLiteOpenHelper {
    private static final String cour_name = "Course name";
    private String TABLE_NAME="Info";
    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String AGE_COL = "age";
}
