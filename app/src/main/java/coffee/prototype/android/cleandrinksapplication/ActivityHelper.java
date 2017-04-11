package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.Model.User;
import coffee.prototype.android.cleandrinksapplication.data.GoalContract;
import coffee.prototype.android.cleandrinksapplication.data.GoalProgressContract;
import coffee.prototype.android.cleandrinksapplication.data.SessionManager;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;
import coffee.prototype.android.cleandrinksapplication.data.WeightContract;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * created by Noodle on 28/03/2017.
 */

public class ActivityHelper {


    //changed type of activity helper to just a generic java class, as otherwise it requries a getCreateMethod, which is useless for a class that's just used for code re-use.
//    private String validatedString;



    private String goalID;
    private ArrayList<Goal>goals = new ArrayList<>();



//    public String getValidatedString() {
//        return validatedString;
//    }
//
//    public void setValidatedString(String validatedString) {
//        this.validatedString = validatedString;
//    }
//
//    public void validateField(final EditText element) {
//
//
//        element.addTextChangedListener(new TextWatcher() {
//
//
//            String validated = "";
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            //Text watcher, monitors what text is typed by the user
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                //Converts the input from a user
//                String userInput = element.getText().toString();
//
//                if (userInput.isEmpty()) {
//                    element.setError("No empty input");
//                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
//                        | userInput.contains("\0")
//                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
//                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
//                        | userInput.contains("\\") | userInput.contains("%")) {
//
//                    element.setError("Special characters can't be used");
//
////                Regex from Google regex checker
//
//                }
//            }
//        });
//        createToastWithText("RETURNING STRING" + getValidatedString());
//    }

    /**
     * Initialises a Toast object and then displays text.
     *
     * @param toastText Uses the text passed, and is displayed by the toast.
     */
    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    //should be in database class!

    public int getWeightFromSesh(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SessionManager sessionManager = new SessionManager(context);
        User user = new User();
        //Makes the database readable.
        int num = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                WeightContract.WeightEntry.TABLE_NAME + " WHERE " + WeightContract.WeightEntry.USER_FK_REF + "= " + "'" + getUserId(getApplicationContext()) + "'", null);
        //Bug: In program, before this logic from this source http://stackoverflow.com/questions/4396604/how-to-solve-cursorindexoutofboundsexception
        //my code would return -1 index, becuase of the check, the code now doesn't throw the exception because it's handed.
        // move to first, when no intial insertion of item caused the program to fail, this is why moveatoNEXT IS USED, if a user has been added.
        if (cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                num = Integer.parseInt(cursor.getString(cursor.getColumnIndex(WeightContract.WeightEntry.COLUMN_WEIGHT)));

                createToastWithText("Weight from user ID method from db   " + num);

            }
        }


        db.close();
        return num;


    }

    public int getUserId(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.
        int num = 0;
        SessionManager SessionManager = new SessionManager(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String userID = SessionManager.getAll();
        String removeSpecChars = userID.replaceAll("\\{", " ").replaceAll("null", "").replaceAll("=", "").replaceAll("\\}", "").trim();
        Cursor cursor = db.rawQuery("SELECT " + UsersContract.UsersEntry._ID + " " + "FROM " +
                UsersContract.UsersEntry.TABLE_NAME + " WHERE " + UsersContract.UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + removeSpecChars + "'", null);
        //Bug: In program, before this logic from this source http://stackoverflow.com/questions/4396604/how-to-solve-cursorindexoutofboundsexception
        //my code would return -1 index, becuase of the check, the code now doesn't throw the exception because it's handed.
        // move to first, when no intial insertion of item caused the program to fail, this is why moveatoNEXT IS USED, if a user has been added.
        if (cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                num = Integer.parseInt(cursor.getString(cursor.getColumnIndex(UsersContract.UsersEntry._ID)));

//                createToastWithText("User ID FROM GET USERid" + num);
                Log.v("Cursor ObjectID", DatabaseUtils.dumpCursorToString(cursor));

            }
        }


        db.close();
        createToastWithText("Returning" + num);
        return num;


    }
    
    //// TODO: 04/04/2017  Need to add progress table.
    public void  addGoalToGoalTable(Context context,String userID, double waterGoal, String startTime, String endTime){
        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(GoalContract.GoalEntry.USER_FK_REF,userID);

        contentValues.put(GoalContract.GoalEntry.COLUMN_Water_Target,waterGoal);
        contentValues.put(GoalContract.GoalEntry.COLUMN_START_TIME,startTime);
        contentValues.put(GoalContract.GoalEntry.COLUMN_END_TIME,endTime);

        long newRowId = db.insert(GoalContract.GoalEntry.TABLE_NAME, null, contentValues);

        setGoalID(String.valueOf(newRowId));

        createToastWithText("row id of goal id"+newRowId);
        //Log cat used to show that a database insertion is occuring.
        Log.v("goal table  activity", "new row id" + newRowId);

        db.close();

    }

    public void addGoalToGoalProgressTable(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(GoalProgressContract.GoalProgressEntry.GOAL_FK_REF,getGoalID());

        //I'm assuming that the goal completed value will populate itself.

        long newRowId = db.insert(GoalProgressContract.GoalProgressEntry.TABLE_NAME, null, contentValues);
        //Log cat used to show that a database insertion is occuring.
        Log.v("adding progress to gaol", "new row id" + newRowId);




        db.close();

    }

//    public ArrayList<Goal> checkIfDataHasBeenAddedToDb(Context context) {
//        UsersDBHelper dbHelper = new UsersDBHelper(context);
//
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + GoalContract.GoalEntry.TABLE_NAME, null);
////        createToastWithText(DatabaseUtils.dumpCursorToString(cursor));
//        goals.clear();
//
//
//        if (cursor.moveToFirst()) {
//            Goal goal = new Goal();
//
//
//
//            createToastWithText("Inside do" + cursor.getDouble(cursor.getColumnIndex("water_target")));
////            Goal goal = new Goal(goal.getWaterGoal(cursor.getDouble(cursor.getColumnIndex("water_target"))));
//            goals.add(goal);
//        }
//        while (cursor.moveToNext()) ;
//        createToastWithText("before close"+goals);
//
//        cursor.close();
//
//
////
//        createToastWithText("after clsoe"+goals);
//
//        return goals;
//    }

//        createToastWithText("Goal list"+ goalsList);


    public List<Goal> checkIfDataHasBeenAddedToDb(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GoalContract.GoalEntry.TABLE_NAME, null);
        createToastWithText(DatabaseUtils.dumpCursorToString(cursor));
        List<Goal> goalsList = new ArrayList<>();
        goalsList.clear();


        if (cursor.moveToFirst()) {
            Goal goal = new Goal();

            goal.setWaterGoal(cursor.getDouble(cursor.getColumnIndex("water_target")));

            createToastWithText("Inside do" + cursor.getDouble(cursor.getColumnIndex("water_target")));
            do {


                double waterGoal = goal.getWaterGoal();

                createToastWithText("water goal in do" + waterGoal);


//                createToastWithText("to string on cusor"+cursor.toString());
                goalsList.add(new Goal(goal.getWaterGoal()));


                GoalsAdapter adapter = new GoalsAdapter(getApplicationContext(), goalsList);
                adapter.notifyDataSetChanged();

            } while (cursor.moveToNext());
            String waterTarget = String.valueOf(cursor.getColumnIndex(GoalContract.GoalEntry.COLUMN_Water_Target));


            cursor.close();

        }

        createToastWithText("goals list+"+goalsList);
        return goalsList;
    }



    public String getGoalID() {
        return goalID;
    }

    public void setGoalID(String goalID) {
        this.goalID = goalID;
    }


}
