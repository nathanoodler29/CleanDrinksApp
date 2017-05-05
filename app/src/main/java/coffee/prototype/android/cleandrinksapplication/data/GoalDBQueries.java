package coffee.prototype.android.cleandrinksapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.Model.TimeHandler;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * created by Noodle on 24/04/2017.
 */


public class GoalDBQueries {

    private ActivityHelper helper = new ActivityHelper();
    private TimeHandler timeHandler = new TimeHandler();
    private String goalID;
    private DBQueryHelper queryHelper = new DBQueryHelper();
    /**
     * This method checks to see if a goal already exists for the current day.
     * As a user is only able to have one goal set a day.
     *
     * @param context Refers to the activities current context
     * @return Boolean GoalExistingToday, for whether the goal does or doesn't exist.
     */
    public boolean checkIfGoalHasAlreadyBeenSet(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Sets database to read.
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        boolean goalExistingToday = false;

        //Cursor checks to see if a user id is related to a current goal set, and then orders by most recent and checks if it matches todays date, by using timeHandler.getYearAndMonth
        // if it does exist today then set as true, otherwise not.

         /*
          * Cursor checks to see if a user id is related to a current goal set, and then orders by most recent.
          * Checks if it matches todays date. by using timeHandler.getYearAndMonth() to see if the current date matches with the most recent goal date.
          *  if it does exist today then set as true, otherwise not.
          */
        Cursor cursor = db.rawQuery("SELECT * FROM " + GoalContract.GoalEntry.TABLE_NAME + " WHERE " + GoalContract.GoalEntry.USER_FK_REF + "= " + helper.getUserId(getApplicationContext()) +


                " AND " + GoalContract.GoalEntry.COLUMN_DATE + "= " + "'" + timeHandler.getYearAndMonth() + "'" + " ORDER BY " + GoalContract.GoalEntry.COLUMN_DATE + " ASC ", null);

        //If there is an entry in the database move to the first row.
        if (cursor.moveToFirst()) {

            goalExistingToday = true;
            //closes the cursor that peformed the query.
            cursor.close();


        }
        //The value for the query is then returned.
        return goalExistingToday;

    }

    /**
     * Counts the number of goals set by a user.
     *
     * @param context Refers to the Activities context.
     * @return The number of goals a single user has set.
     */
    public int numOfGoalsCreatedForAUser(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        int numOfGoals = 0;
        //Counts the number of goals set based on the current user id.
        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + GoalContract.GoalEntry.TABLE_NAME + " WHERE " +
                GoalContract.GoalEntry.USER_FK_REF + " = " + "'" + helper.getUserId(context) + "'", null);

        //if a row exists then move to the first value.
        if (cursor.moveToFirst()) {

            numOfGoals = cursor.getInt(0);
            cursor.close();


        }
        //Return the value for the number of goals set.
        return numOfGoals;

    }

    public void addGoalToGoalTable(Context context, String userID, double waterGoal, String startTime, String endTime,String date) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(GoalContract.GoalEntry.USER_FK_REF, userID);

        contentValues.put(GoalContract.GoalEntry.COLUMN_Water_Target, waterGoal);
        contentValues.put(GoalContract.GoalEntry.COLUMN_START_TIME, startTime);
        contentValues.put(GoalContract.GoalEntry.COLUMN_END_TIME, endTime);


        contentValues.put(GoalContract.GoalEntry.COLUMN_DATE, date);

        long newRowId = db.insert(GoalContract.GoalEntry.TABLE_NAME, null, contentValues);

        setGoalID(String.valueOf(newRowId));

//        createToastWithText("row id of goal id" + newRowId);
        //Log cat used to show that a database insertion is occuring.
        Log.v("goal table  activity", "new row id" + newRowId);

        db.close();

    }

    public String getGoalID() {
        return goalID;
    }

    public void setGoalID(String goalID) {
        this.goalID = goalID;
    }

    public ArrayList<Goal> populateGoalAdapter(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GoalContract.GoalEntry.TABLE_NAME + " WHERE " + GoalContract.GoalEntry.USER_FK_REF + "= " + queryHelper.getUserId(getApplicationContext()), null);
//        createToastWithText(DatabaseUtils.dumpCursorToString(cursor));
        ArrayList<Goal> goalsList = new ArrayList<Goal>();
        goalsList.clear();


        if (cursor.moveToFirst()) {


            do {

                String waterGoal = cursor.getString(2);
                String startTime = cursor.getString(3);
                String endTime = cursor.getString(4);


                Goal goal = new Goal(waterGoal,startTime, endTime);

                goal.setWaterGoalStr(waterGoal);
                goal.setStartTimeGoal(startTime);
                goal.setEndTimeGoal(endTime);

                goalsList.add(goal);

            } while (cursor.moveToNext());


            cursor.close();

        }

//        createToastWithText("goals list+" + goalsList);
        return goalsList;
    }

}



