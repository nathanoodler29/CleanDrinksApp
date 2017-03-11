package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;

/**
 * created by Noodle on 10/03/2017.
 */

public class UsersDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "userdetails.db";
    private static final int DATABASE_VERSION = 1;

    public UsersDBHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_USERS_TABLE ="CREATE TABLE " + UsersEntry.TABLE_NAME + "("
                + UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsersEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_USERS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
