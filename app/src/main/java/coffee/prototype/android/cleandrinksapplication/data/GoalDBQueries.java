package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.Model.TimeHandler;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * created by Noodle on 24/04/2017.
 */



public class GoalDBQueries {

    private ActivityHelper helper = new ActivityHelper();
    private TimeHandler timeHandler = new TimeHandler();

    public boolean checkIfGoalHasAlreadyBeenSet(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        boolean goalExistingToday = false;


        Cursor cursor = db.rawQuery("SELECT * FROM " + GoalContract.GoalEntry.TABLE_NAME + " WHERE " + GoalContract.GoalEntry.USER_FK_REF + "= " + helper.getUserId(getApplicationContext()) +


                " AND " + GoalContract.GoalEntry.COLUMN_DATE + "= " + "'" + timeHandler.getYearAndMonth()+"'"+   " ORDER BY " + GoalContract.GoalEntry.COLUMN_DATE + " ASC ", null);


        if (cursor.moveToFirst()) {

            goalExistingToday = true;

            cursor.close();
//


        }

        return goalExistingToday;

    }

    public int numOfGoalsCreatedForAUser(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        int numOfGoals = 0;

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + GoalContract.GoalEntry.TABLE_NAME + " WHERE " + GoalContract.GoalEntry.USER_FK_REF, null);


        if (cursor.moveToFirst()) {

            numOfGoals = cursor.getInt(0);
            cursor.close();



        }

        return numOfGoals;

    }
}



