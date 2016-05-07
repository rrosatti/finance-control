package com.example.rodri.financecontrol.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rodri on 2/1/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXPENSES = "expenses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXPENSE = "expense";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MONTH_ID = "month_id";

    private static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation
    private static final String DATABASE_CREATE = "create table " + TABLE_EXPENSES + "("
                                                    + COLUMN_ID + " integer primary key autoincrement, "
                                                    + COLUMN_EXPENSE + " text not null, "
                                                    + COLUMN_VALUE + " real not null, "
                                                    + COLUMN_DATE + " text not null, "
                                                    + COLUMN_MONTH_ID + " integer);";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version" + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }


}
