package com.example.uplabdhisingh.bakingtime.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by uplabdhisingh on 04/03/18.
 */

public class WidgetDbHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "bakingtime.db";
    public static final int DATABASE_VERSION = 1;

    public WidgetDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_TABLE =
                "CREATE TABLE " + WidgetContract.WidgetDataEntry.TABLE_NAME +
                        "(" +
                        WidgetContract.WidgetDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WidgetContract.WidgetDataEntry.COLUMN_INGREDIENTS + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + WidgetContract.WidgetDataEntry.TABLE_NAME);
        onCreate(db);
    }

}
