package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import coffee.prototype.android.cleandrinksapplication.Model.User;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;

/**
 * created by Noodle on 10/03/2017.
 */


public class UsersDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userdetails.db";
    private static final int DATABASE_VERSION = 12;

    public UsersDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * This creates all the tables related in the database.
     * WHich includes user, weight, goal tables.
     * With every new table added, the versioning needs to be updated.
     *
     * @param db This is a reference to the Database, that is used to then create the tables.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
            This creates a user table, with user ID, email and password, this relates to logging in
            Signing up and the forgotten password activity.
         */
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UsersEntry.TABLE_NAME + "("
                + UsersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsersEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL);";
        //Log cat, can check whether the table has been created.
        Log.v("Creating users table", "users table");
        //Exectures nq the creation of the user table.
        db.execSQL(SQL_CREATE_USERS_TABLE);

        /*
            The weight table stores the Weight and height of a  user.
            This table is linked to a user, with a 1..1 relationship.
            This allows a user to be looked up to check if they have supplied a weight.
         */

        String SQL_CREATE_WEIGHT_TABLE = "CREATE TABLE " + WeightContract.WeightEntry.TABLE_NAME + " ("
                + WeightContract.WeightEntry.USER_FK_REF + " INTEGER PRIMARY KEY  NOT NULL, "

                + WeightContract.WeightEntry.COLUMN_WEIGHT + " INTEGER NOT NULL, "
                + WeightContract.WeightEntry.COLUMN_HEIGHT + " REAL NOT NULL," +
                " FOREIGN KEY(" + WeightContract.WeightEntry.USER_FK_REF + ")" + " REFERENCES " + UsersEntry.TABLE_NAME + "(" + UsersEntry._ID + "));";
        Log.v("Creating weight table", "weight table");
        //Creates the weight table.
        db.execSQL(SQL_CREATE_WEIGHT_TABLE);

        //Goal table
        /*
            Creates a goal table which stores the users water goal, start and end time of the goal.
            The user id is the fk refernece to the table, to peform check if user as set or completed a goal.
         */
        String SQL_CREATE_GOAL_TABLE = "CREATE TABLE " + GoalContract.GoalEntry.TABLE_NAME + " ("
                + GoalContract.GoalEntry.USER_FK_REF + " INTEGER  NOT NULL, "

                + GoalContract.GoalEntry.GOAL_ID + " INTEGER PRIMARY KEY  NOT NULL, "
                + GoalContract.GoalEntry.COLUMN_Water_Target + " REAL NOT NULL," + " "
                //real represnts georigon calander
                + GoalContract.GoalEntry.COLUMN_START_TIME + " REAL NOT NULL," + " "
                //real represnts georigon calander
                + GoalContract.GoalEntry.COLUMN_END_TIME + " REAL NOT NULL," +


                " FOREIGN KEY(" + GoalContract.GoalEntry.USER_FK_REF + ")" + " REFERENCES " + UsersEntry.TABLE_NAME + "(" + UsersEntry._ID + "));";
        Log.v("Creating GOAL table", "GOAL table");
        //creates goal talbe.
        db.execSQL(SQL_CREATE_GOAL_TABLE);

        //Progress Goal table
        /*
            This table relates to the goal table, and sets default value of a goal as 0 for completion.
            This then means, when a user completes a goal, the value will change to 1
         */
        String SQL_CREATE_GOAL_PROGRESS_TABLE = "CREATE TABLE " + GoalProgressContract.GoalProgressEntry.TABLE_NAME + "("

                + GoalProgressContract.GoalProgressEntry.Progress_Goal_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + GoalProgressContract.GoalProgressEntry.GOAL_FK_REF + " INTEGER  NOT NULL, "

                //Sets progress completed to 0 suggesting it's false, until goal is complete where the value would be 1.
                + GoalProgressContract.GoalProgressEntry.GOAL_COMPLETED + " INTEGER DEFAULT 0, " +

                " FOREIGN KEY(" + GoalProgressContract.GoalProgressEntry.GOAL_FK_REF + ")" + " REFERENCES " + GoalContract.GoalEntry.TABLE_NAME + "(" + GoalContract.GoalEntry._ID + "));";
        Log.v("Creating Goal progress", "GOAL PROGRESS table");

        //creates goal progress table.
        db.execSQL(SQL_CREATE_GOAL_PROGRESS_TABLE);


        //Drinks Category table

        String SQL_CREATE_DRINKS_CATEGORY_TABLE = "CREATE TABLE " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + "("
                + DrinksContract.DrinksCategoryEntry.DRINKS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DrinksContract.DrinksCategoryEntry.DRINK_NAME + " TEXT NOT NULL, "
                + DrinksContract.DrinksCategoryEntry.DRINK_TYPE + " TEXT NOT NULL, "
                //don't think it makes sense for it to be a double.
                + DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME + " INTEGER NOT NULL, "

                + DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT + " REAL NOT NULL);";


        //Log cat, can check whether the table has been created.
        Log.v("Creating drinks table", "drinks cat table");
        //Exectures nq the creation of the user table.
        db.execSQL(SQL_CREATE_DRINKS_CATEGORY_TABLE);

        //drinks quanntiy table

        String SQL_CREATE_DRINKS_CATEGORY_QUANITIY_TABLE = "CREATE TABLE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " ("
                + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.drink_id_fk + " INTEGER NOT NULL, "
                + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk + " TEXT NOT NULL, "

                + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " INTEGER DEFAULT 0, "
                //STORING date in this format dd/mm/yyyy hence the ser of text.
                + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " TEXT NOT NULL, "


                + " FOREIGN KEY(" + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.drink_id_fk + ")" + " REFERENCES " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + "(" + DrinksContract.DrinksCategoryEntry.DRINKS_ID + ")  "

                + " FOREIGN KEY(" + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk + ")" + " REFERENCES " + UsersEntry.TABLE_NAME +  "(" + UsersEntry._ID + "));";

        Log.v("Creating drink quanity", "linking table to drink cat table");


        db.execSQL(SQL_CREATE_DRINKS_CATEGORY_QUANITIY_TABLE);


    }

    /**
     * This handles when a table already exists when the schema then updates.
     * All tables are then deleted if it already exists on an update?
     * s
     *
     * @param db         Reference to the database
     * @param oldVersion Relates to the old database
     * @param newVersion Relaates to the current version of it.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_USER_TABLE;
        String SQL_DROP_WEIGHT_TABLE;
        String SQL_DROP_GOAL_TABLE;
        String SQL_DROP_GOAL_PROGRESS_TABLE;
        String SQL_DROP_DRINK_CATEGORY_TABLE;
        String SQL_DROP_DRINK_QUANTITY_TABLE;


        SQL_DROP_USER_TABLE = "DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME;
        Log.v("User table exists", "Dropping table");

        db.execSQL(SQL_DROP_USER_TABLE);
        SQL_DROP_WEIGHT_TABLE = "DROP TABLE IF EXISTS " + WeightContract.WeightEntry.TABLE_NAME;
        Log.v("Weight table exists", "Dropping table");

        db.execSQL(SQL_DROP_WEIGHT_TABLE);

        SQL_DROP_GOAL_TABLE = "DROP TABLE IF EXISTS " + GoalContract.GoalEntry.TABLE_NAME;

        Log.v("Goal table exists", "Dropping table");

        db.execSQL(SQL_DROP_GOAL_TABLE);


        SQL_DROP_GOAL_PROGRESS_TABLE = "DROP TABLE IF EXISTS " + GoalProgressContract.GoalProgressEntry.TABLE_NAME;
        Log.v("Goal progress exists", "Dropping table");


        db.execSQL(SQL_DROP_GOAL_PROGRESS_TABLE);

        SQL_DROP_DRINK_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + DrinksContract.DrinksCategoryEntry.TABLE_NAME;
        Log.v("Drink cat table exists", "Dropping drink table");


        db.execSQL(SQL_DROP_DRINK_CATEGORY_TABLE);


        SQL_DROP_DRINK_QUANTITY_TABLE = "DROP TABLE IF EXISTS " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME;
        Log.v("Drink quantity exists", "Dropping drink quantity linking table");

        db.execSQL(SQL_DROP_DRINK_QUANTITY_TABLE);


        //Creates the entire database.

        onCreate(db);
    }
}
