package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;

/**
 * created by Noodle on 10/03/2017.
 */

public class UsersDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "userdetails.db";
    private static final int DATABASE_VERSION = 2;

    public UsersDBHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_USERS_TABLE ="CREATE TABLE " + UsersEntry.TABLE_NAME + "("
                + UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsersEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL);";

        Log.v("Creating users table","users table");
        db.execSQL(SQL_CREATE_USERS_TABLE);
        String SQL_CREATE_WEIGHT_TABLE ="CREATE TABLE " + WeightContract.WeightEntry.TABLE_NAME + "("
                + WeightContract.WeightEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WeightContract.WeightEntry.COLUMN_WEIGHT + " INTEGER NOT NULL, "
                + WeightContract.WeightEntry.COLUMN_HEIGHT + " REAL NOT NULL);";
        Log.v("Creating weight table","weight table");

        db.execSQL(SQL_CREATE_WEIGHT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_USER_TABLE;
        String SQL_DROP_WEIGHT_TABLE;

        SQL_DROP_USER_TABLE= "DROP TABLE IF EXISTS "+UsersEntry.TABLE_NAME;
        Log.v("User table exists","Dropping table");

        db.execSQL(SQL_DROP_USER_TABLE);
        SQL_DROP_WEIGHT_TABLE= "DROP TABLE IF EXISTS "+ WeightContract.WeightEntry.TABLE_NAME;
        Log.v("Weight table exists","Dropping table");

        db.execSQL(SQL_DROP_WEIGHT_TABLE);

        onCreate(db);
    }
}
