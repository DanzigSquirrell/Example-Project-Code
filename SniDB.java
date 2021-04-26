package com.example.finalassignment.spaceNasaImage;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Database open helper, used to create and update the database
 *
 * @author Dan Squirrell
 * @version 3
 */
public class SniDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "sniDB";
    public static final int DB_VERSION = 3;

    public static final String ID_COLUMN = "_id";
    public static final String TABLE_NAME = "Favourites";
    public static final String DATE = "Date";
    public static final String EXPLANATION = "Explanation";
    public static final String TITLE = "Title";
    public static final String URL = "Url";
    public static final String HD_URL = "HDUrl";

    /**
     * Required constructor with super call
     * @param a the activity sent by method call
     * */
    public SniDB(Activity a) {
        super(a, DB_NAME, null, DB_VERSION);
    }

    /**
     * creates database
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATE + " TEXT,"
                + EXPLANATION + " TEXT,"
                + TITLE + " TEXT,"
                + URL + " TEXT,"
                + HD_URL + " TEXT);");
    }

    /**
     * Deletes database on upgrade and calls for it to be recreated
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:" + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
