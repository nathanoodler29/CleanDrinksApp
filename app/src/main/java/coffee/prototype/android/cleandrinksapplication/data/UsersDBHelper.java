package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;

/**
 * created by Noodle on 10/03/2017.
 */

public class UsersDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "userdetails.db";
    private static final int DATABASE_VERSION = 5;

    public UsersDBHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //user table.
        String SQL_CREATE_USERS_TABLE ="CREATE TABLE " + UsersEntry.TABLE_NAME + "("
                + UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsersEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL);";

        Log.v("Creating users table","users table");
        db.execSQL(SQL_CREATE_USERS_TABLE);

        //weight table
        String SQL_CREATE_WEIGHT_TABLE ="CREATE TABLE " + WeightContract.WeightEntry.TABLE_NAME + " ("
                +  WeightContract.WeightEntry.USER_FK_REF + " INTEGER PRIMARY KEY  NOT NULL, "

                +  WeightContract.WeightEntry.COLUMN_WEIGHT + " INTEGER NOT NULL, "
                + WeightContract.WeightEntry.COLUMN_HEIGHT + " REAL NOT NULL,"+
                " FOREIGN KEY("+ WeightContract.WeightEntry.USER_FK_REF+")"+ " REFERENCES "+UsersEntry.TABLE_NAME+"("+UsersEntry._ID+"));";
        Log.v("Creating weight table","weight table");


        db.execSQL(SQL_CREATE_WEIGHT_TABLE);

        String SQL_CREATE_GOAL_TABLE ="CREATE TABLE " + GoalContract.GoalEntry.TABLE_NAME + " ("
                +  GoalContract.GoalEntry.USER_FK_REF + " INTEGER  NOT NULL, "

                +  GoalContract.GoalEntry.GOAL_ID + " INTEGER PRIMARY KEY  NOT NULL, "
                + GoalContract.GoalEntry.COLUMN_Water_Target + " REAL NOT NULL,"+ " "
                //real represnts georigon candler

                + GoalContract.GoalEntry.COLUMN_START_TIME + " REAL NOT NULL,"+ " "
                + GoalContract.GoalEntry.COLUMN_END_TIME + " REAL NOT NULL,"+


                " FOREIGN KEY("+ GoalContract.GoalEntry.USER_FK_REF+")"+ " REFERENCES "+UsersEntry.TABLE_NAME+"("+UsersEntry._ID+"));";
        Log.v("Creating GOAL table","GOAL table");
        //goal table

        db.execSQL(SQL_CREATE_GOAL_TABLE);

        //may be worthy having a join query for the user id, goal and progress tables, to make it easier to query. Catious with nulls

//FOREIGN KEY(artist) REFERENCES artists(id)

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_USER_TABLE;
        String SQL_DROP_WEIGHT_TABLE;
        String SQL_DROP_GOAL_TABLE;

        SQL_DROP_USER_TABLE= "DROP TABLE IF EXISTS "+UsersEntry.TABLE_NAME;
        Log.v("User table exists","Dropping table");

        db.execSQL(SQL_DROP_USER_TABLE);
        SQL_DROP_WEIGHT_TABLE= "DROP TABLE IF EXISTS "+ WeightContract.WeightEntry.TABLE_NAME;
        Log.v("Weight table exists","Dropping table");

        db.execSQL(SQL_DROP_WEIGHT_TABLE);

        SQL_DROP_GOAL_TABLE= "DROP TABLE IF EXISTS "+ GoalContract.GoalEntry.TABLE_NAME;

        Log.v("Goal table exists","Dropping table");

        db.execSQL(SQL_DROP_GOAL_TABLE);


        onCreate(db);
    }
}
