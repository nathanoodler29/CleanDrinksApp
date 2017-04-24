package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.Model.TimeHandler;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Noodle on 24/04/2017.
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
}



