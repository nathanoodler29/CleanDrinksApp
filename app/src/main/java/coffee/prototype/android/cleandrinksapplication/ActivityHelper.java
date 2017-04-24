package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.Model.Achievement;
import coffee.prototype.android.cleandrinksapplication.Model.AlcoholicDrink;
import coffee.prototype.android.cleandrinksapplication.Model.Coffee;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.Model.Tea;
import coffee.prototype.android.cleandrinksapplication.Model.TimeHandler;
import coffee.prototype.android.cleandrinksapplication.Model.User;
import coffee.prototype.android.cleandrinksapplication.data.AchievementContract;
import coffee.prototype.android.cleandrinksapplication.data.DrinksCategoryDrinkQuanitiy;
import coffee.prototype.android.cleandrinksapplication.data.DrinksContract;
import coffee.prototype.android.cleandrinksapplication.data.GoalContract;
import coffee.prototype.android.cleandrinksapplication.data.GoalProgressContract;
import coffee.prototype.android.cleandrinksapplication.data.SessionManager;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;
import coffee.prototype.android.cleandrinksapplication.data.WeightContract;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * created by Noodle on 28/03/2017.
 * tea icons from http://www.flaticon.com/free-icon/cup-with-tea-bag_68890#term=tea&page=2&position=35
 */

public class ActivityHelper {


    //changed type of activity helper to just a generic java class, as otherwise it requries a getCreateMethod, which is useless for a class that's just used for code re-use.
//    private String validatedString;


    private String goalID;
    private ArrayList<Goal> goals = new ArrayList<>();
    private ArrayList<Drink> mdrinks = new ArrayList<>();
    private ArrayList<Drink> drinksRecipt = new ArrayList<Drink>();
    private ArrayList<Achievement> achivements = new ArrayList<Achievement>();


    private String numOfAchivementsInDB;





    private String drinksID;

    public String getLastAddedID() {
        return LastAddedID;
    }

    public void setLastAddedID(String lastAddedID) {
        LastAddedID = lastAddedID;
    }

    private String LastAddedID;


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
            cursor.close();
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
    public void addGoalToGoalTable(Context context, String userID, double waterGoal, String startTime, String endTime) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(GoalContract.GoalEntry.USER_FK_REF, userID);

        contentValues.put(GoalContract.GoalEntry.COLUMN_Water_Target, waterGoal);
        contentValues.put(GoalContract.GoalEntry.COLUMN_START_TIME, startTime);
        contentValues.put(GoalContract.GoalEntry.COLUMN_END_TIME, endTime);

        long newRowId = db.insert(GoalContract.GoalEntry.TABLE_NAME, null, contentValues);

        setGoalID(String.valueOf(newRowId));

        createToastWithText("row id of goal id" + newRowId);
        //Log cat used to show that a database insertion is occuring.
        Log.v("goal table  activity", "new row id" + newRowId);

        db.close();

    }

    public void addGoalToGoalProgressTable(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(GoalProgressContract.GoalProgressEntry.GOAL_FK_REF, getGoalID());

        //I'm assuming that the goal completed value will populate itself.

        long newRowId = db.insert(GoalProgressContract.GoalProgressEntry.TABLE_NAME, null, contentValues);
        //Log cat used to show that a database insertion is occuring.
        Log.v("adding progress to gaol", "new row id" + newRowId);


        db.close();


    }

    public String getDrinksID() {
        return drinksID;
    }

    public void setDrinksID(String drinksID) {
        this.drinksID = drinksID;
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
        Cursor cursor = db.rawQuery("SELECT * FROM " + GoalContract.GoalEntry.TABLE_NAME + " WHERE " + GoalContract.GoalEntry.USER_FK_REF + "= " + getUserId(getApplicationContext()), null);
        createToastWithText(DatabaseUtils.dumpCursorToString(cursor));
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

        createToastWithText("goals list+" + goalsList);
        return goalsList;
    }


    public void insertValuesIntoDB(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValuestwo = new ContentValues();
        ContentValues contentValuesthree = new ContentValues();
        ContentValues contentValuesfour = new ContentValues();

        int espressoCup = context.getResources().getIdentifier("espresso", "drawable", context.getPackageName());

        Coffee singleEspresso = new Coffee("Single Espresso", 30, "Coffee", 92, espressoCup);
        singleEspresso.setDrinkName("Single Espresso");
        singleEspresso.setDrinkVolume(30);
        singleEspresso.setDrinkType("Coffee");
        singleEspresso.setCaffineContent(92);
        singleEspresso.setImagePath(espressoCup);
//        //http://stackoverflow.com/questions/3476430/how-to-get-a-resource-id-with-a-known-resource-name
//        int doubleEspressoCup = context.getResources().getIdentifier("espresso", "drawable", context.getPackageName());
//        doubleEspresso.setImagePath(espressoCup);

        int cappucinoImage = context.getResources().getIdentifier("cappuccino", "drawable", context.getPackageName());

        Coffee cappucino = new Coffee("Small Double shot Cappucino", 236, "Coffee", 184, cappucinoImage);
        cappucino.setDrinkName("Small Double shot Cappucino");
        cappucino.setDrinkVolume(236);
        cappucino.setDrinkType("Coffee");
        cappucino.setCaffineContent(184);
        cappucino.setImagePath(cappucinoImage);

        int flatWhiteImage = context.getResources().getIdentifier("gibraltar", "drawable", context.getPackageName());

        Coffee flatWhite = new Coffee("Flat white", 236, "Coffee", 184, flatWhiteImage);
        flatWhite.setDrinkName("Flat white");
        flatWhite.setDrinkVolume(236);
        flatWhite.setDrinkType("Coffee");
        flatWhite.setCaffineContent(184);
        flatWhite.setImagePath(flatWhiteImage);

        int latteImage = context.getResources().getIdentifier("latte", "drawable", context.getPackageName());
////https://www.caffeineinformer.com/caffeine-content/costa-coffee
        Coffee latte = new Coffee("Single shot latte", 350, "Coffee", 92, latteImage);
        latte.setDrinkName("Single shot latte");
        latte.setDrinkVolume(350);
        latte.setDrinkType("Coffee");
        latte.setCaffineContent(92);
        latte.setImagePath(latteImage);

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, singleEspresso.getDrinkName());

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, singleEspresso.getDrinkType());

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, singleEspresso.getDrinkVolume());

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, singleEspresso.getImagePath());

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, singleEspresso.getCaffineContent());

//


        contentValuestwo.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, cappucino.getDrinkName());

        contentValuestwo.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, cappucino.getDrinkType());

        contentValuestwo.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, cappucino.getDrinkVolume());

        contentValuestwo.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, cappucino.getImagePath());

        contentValuestwo.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, cappucino.getCaffineContent());


        contentValuesthree.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, flatWhite.getDrinkName());

        contentValuesthree.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, flatWhite.getDrinkType());

        contentValuesthree.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, flatWhite.getDrinkVolume());

        contentValuesthree.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, flatWhite.getImagePath());

        contentValuesthree.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, flatWhite.getCaffineContent());


        contentValuesfour.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, latte.getDrinkName());

        contentValuesfour.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, latte.getDrinkType());

        contentValuesfour.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, latte.getDrinkVolume());

        contentValuesfour.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, latte.getImagePath());

        contentValuesfour.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, latte.getCaffineContent());


        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, contentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, contentValuestwo);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, contentValuesthree);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, contentValuesfour);
        db.close();
    }


    public void insertBlackCoffeeIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues filterCoffeeContentValue = new ContentValues();
        ContentValues frenchPressContentValue = new ContentValues();
        ContentValues mokaPotCoffeeContentValue = new ContentValues();
        ContentValues instantCoffeeContentValue = new ContentValues();

        int filterCoffeeImage = context.getResources().getIdentifier("filter_coffee", "drawable", context.getPackageName());


        Coffee filterCoffee = new Coffee("Filter Coffee", 236, "Black Coffee", 145, filterCoffeeImage);
        filterCoffee.setDrinkName("Filter Coffee");
        filterCoffee.setDrinkVolume(236);
        filterCoffee.setDrinkType("Black Coffee");
        filterCoffee.setCaffineContent(145);
        filterCoffee.setImagePath(filterCoffeeImage);

        int frenchPressImage = context.getResources().getIdentifier("frenchpress", "drawable", context.getPackageName());


        Coffee frenchPress = new Coffee("French press Coffee", 236, "Black Coffee", 163, frenchPressImage);
        frenchPress.setDrinkName("French press Coffee");
        frenchPress.setDrinkVolume(236);
        frenchPress.setDrinkType("Black Coffee");
        frenchPress.setCaffineContent(145);
        frenchPress.setImagePath(frenchPressImage);

        int mokaPotImage = context.getResources().getIdentifier("mokapot", "drawable", context.getPackageName());



        Coffee mokaPotCoffee = new Coffee("Moka Pot Coffee", 236, "Black Coffee", 5, mokaPotImage);
        mokaPotCoffee.setDrinkName("Moka Pot Coffee");
        mokaPotCoffee.setDrinkVolume(236);
        mokaPotCoffee.setDrinkType("Black Coffee");
        mokaPotCoffee.setCaffineContent(5);
        mokaPotCoffee.setImagePath(mokaPotImage);

        int instantCoffeeImage = context.getResources().getIdentifier("instant_coffee", "drawable", context.getPackageName());


        Coffee instantCoffee = new Coffee("Instant  Coffee", 236, "Black Coffee", 57, instantCoffeeImage);
        instantCoffee.setDrinkName("Instant  Coffee");
        instantCoffee.setDrinkVolume(236);
        instantCoffee.setDrinkType("Black Coffee");
        instantCoffee.setCaffineContent(57);
        instantCoffee.setImagePath(instantCoffeeImage);

        filterCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, filterCoffee.getDrinkName());

        filterCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, filterCoffee.getDrinkType());

        filterCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, filterCoffee.getDrinkVolume());

        filterCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, filterCoffee.getImagePath());

        filterCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, filterCoffee.getCaffineContent());

//


        frenchPressContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, frenchPress.getDrinkName());

        frenchPressContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, frenchPress.getDrinkType());

        frenchPressContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, frenchPress.getDrinkVolume());

        frenchPressContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, frenchPress.getImagePath());

        frenchPressContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, frenchPress.getCaffineContent());


        mokaPotCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, mokaPotCoffee.getDrinkName());

        mokaPotCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, mokaPotCoffee.getDrinkType());

        mokaPotCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, mokaPotCoffee.getDrinkVolume());

        mokaPotCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, mokaPotCoffee.getImagePath());

        mokaPotCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, mokaPotCoffee.getCaffineContent());


        instantCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, instantCoffee.getDrinkName());

        instantCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, instantCoffee.getDrinkType());

        instantCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, instantCoffee.getDrinkVolume());

        instantCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, instantCoffee.getImagePath());

        instantCoffeeContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, instantCoffee.getCaffineContent());


        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, filterCoffeeContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, frenchPressContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, mokaPotCoffeeContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, instantCoffeeContentValue);


        db.close();



    }

    public void insertTeaIntoDatabase(Context context){

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues englishBreakFastContentValue = new ContentValues();
        ContentValues blackTeaContentValue = new ContentValues();
        ContentValues greenTeaContentValue = new ContentValues();
        ContentValues decafTeaContentValue = new ContentValues();

        int teaImage = context.getResources().getIdentifier("english_breakfast", "drawable", context.getPackageName());

        Tea englishBreakFast = new Tea("3 Min steep English breakfast tea", 170, "Tea", 22, teaImage);
        englishBreakFast.setDrinkName("3 Min steep English breakfast tea");
        englishBreakFast.setDrinkVolume(170);
        englishBreakFast.setDrinkType("Tea");
        englishBreakFast.setCaffineContent(22);
        englishBreakFast.setImagePath(teaImage);

        int blackTeaImage = context.getResources().getIdentifier("black_tea_cup", "drawable", context.getPackageName());

        Tea blackTea = new Tea("3 Min Steep Black Tea", 236, "Tea", 42, blackTeaImage);
        blackTea.setDrinkName("3 Min Steep Black Tea");
        blackTea.setDrinkVolume(236);
        blackTea.setDrinkType("Tea");
        blackTea.setCaffineContent(42);
        blackTea.setImagePath(blackTeaImage);

        int greenTeaImage = context.getResources().getIdentifier("green_tea", "drawable", context.getPackageName());


        Tea greenTea = new Tea("Green Tea", 236, "Tea", 25, greenTeaImage);
        greenTea.setDrinkName("Green Tea");
        greenTea.setDrinkVolume(236);
        greenTea.setDrinkType("Tea");
        greenTea.setCaffineContent(25);
        greenTea.setImagePath(greenTeaImage);

        int decafTeaImage = context.getResources().getIdentifier("decaf_tea", "drawable", context.getPackageName());

        Tea decafTea = new Tea("Decaf Tea", 236, "Tea", 5, blackTeaImage);
        decafTea.setDrinkName("Decaf Tea");
        decafTea.setDrinkVolume(236);
        decafTea.setDrinkType("Tea");
        decafTea.setCaffineContent(5);
        decafTea.setImagePath(decafTeaImage);

        englishBreakFastContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, englishBreakFast.getDrinkName());

        englishBreakFastContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, englishBreakFast.getDrinkType());

        englishBreakFastContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, englishBreakFast.getDrinkVolume());

        englishBreakFastContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, englishBreakFast.getImagePath());

        englishBreakFastContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, englishBreakFast.getCaffineContent());



        blackTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, blackTea.getDrinkName());

        blackTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, blackTea.getDrinkType());

        blackTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, blackTea.getDrinkVolume());

        blackTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, blackTea.getImagePath());

        blackTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, blackTea.getCaffineContent());


        greenTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, greenTea.getDrinkName());

        greenTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, greenTea.getDrinkType());

        greenTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, greenTea.getDrinkVolume());

        greenTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, greenTea.getImagePath());

        greenTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, greenTea.getCaffineContent());



        decafTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, decafTea.getDrinkName());

        decafTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, decafTea.getDrinkType());

        decafTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, decafTea.getDrinkVolume());

        decafTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, decafTea.getImagePath());

        decafTeaContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, decafTea.getCaffineContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, englishBreakFastContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, blackTeaContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, greenTeaContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, decafTeaContentValue);

        db.close();



    }

    public void insertRedWineIntoDatabase(Context context){

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues redwineContentValue = new ContentValues();
        ContentValues sangioveseRedWineContentValue = new ContentValues();
        ContentValues merlotRedWineContentValue = new ContentValues();



        int redWineImage = context.getResources().getIdentifier("red_wine_item", "drawable", context.getPackageName());

        AlcoholicDrink redWine = new AlcoholicDrink("125ml Glass of Red wine", 125, "Red wine", 2.3, redWineImage);
        redWine.setDrinkName("125ml Glass of Red wine");
        redWine.setDrinkVolume(125);
        redWine.setDrinkType("Red Wine");
        redWine.setUnitContent(2.3);
        redWine.setImagePath(redWineImage);

        AlcoholicDrink sangioveseRedWine = new AlcoholicDrink(" 13% 125ml Sangiovese Red wine", 125, "Red wine", 1.6, redWineImage);
        sangioveseRedWine.setDrinkName("125ml Sangiovese Red wine");
        sangioveseRedWine.setDrinkVolume(125);
        sangioveseRedWine.setDrinkType("Red Wine");
        sangioveseRedWine.setUnitContent(1.6);
        sangioveseRedWine.setImagePath(redWineImage);


        AlcoholicDrink merlotRedWine = new AlcoholicDrink("13% 125ml Merlot Red wine", 125, "Red wine", 1.6, redWineImage);
        merlotRedWine.setDrinkName("125ml Merlot Red wine");
        merlotRedWine.setDrinkVolume(125);
        merlotRedWine.setDrinkType("Red Wine");
        merlotRedWine.setUnitContent(1.6);
        merlotRedWine.setImagePath(redWineImage);

        redwineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, redWine.getDrinkName());

        redwineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, redWine.getDrinkType());

        redwineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, redWine.getDrinkVolume());

        redwineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, redWine.getImagePath());

        redwineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, redWine.getUnitContent());




        sangioveseRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, sangioveseRedWine.getDrinkName());

        sangioveseRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, sangioveseRedWine.getDrinkType());

        sangioveseRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, sangioveseRedWine.getDrinkVolume());

        sangioveseRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, sangioveseRedWine.getImagePath());

        sangioveseRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, sangioveseRedWine.getUnitContent());




        merlotRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, merlotRedWine.getDrinkName());

        merlotRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, merlotRedWine.getDrinkType());

        merlotRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, merlotRedWine.getDrinkVolume());

        merlotRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, merlotRedWine.getImagePath());

        merlotRedWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, merlotRedWine.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, redwineContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, sangioveseRedWineContentValue);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null,  merlotRedWineContentValue);


        db.close();



    }


    public void insertWhiteWineIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues whiteWineContentValues = new ContentValues();
        ContentValues sauvignoBlancContentValues = new ContentValues();
        ContentValues biancoMoncaroWhiteWineContentValue = new ContentValues();

        int whiteWineImage = context.getResources().getIdentifier("white_wine_item", "drawable", context.getPackageName());

        AlcoholicDrink whiteWine = new AlcoholicDrink("125ml Glass of White wine", 125, "White Wine", 2.3, whiteWineImage);
        whiteWine.setDrinkName("125ml Glass of White wine");
        whiteWine.setDrinkVolume(125);
        whiteWine.setDrinkType("White Wine");
        whiteWine.setUnitContent(2.3);
        whiteWine.setImagePath(whiteWineImage);


        AlcoholicDrink sauvignoBlanc = new AlcoholicDrink("13% 125ml sauvigno Blanc", 125, "White Wine", 1.6, whiteWineImage);
        sauvignoBlanc.setDrinkName("13% 125ml sauvigno Blanc");
        sauvignoBlanc.setDrinkVolume(125);
        sauvignoBlanc.setDrinkType("White Wine");
        sauvignoBlanc.setUnitContent(1.6);
        sauvignoBlanc.setImagePath(whiteWineImage);

        AlcoholicDrink biancoMoncaroWhiteWine = new AlcoholicDrink("11% 125ml Bianco Moncaro", 125, "White Wine", 1.4, whiteWineImage);
        biancoMoncaroWhiteWine.setDrinkName("11% 125ml Bianco Moncaro");
        biancoMoncaroWhiteWine.setDrinkVolume(125);
        biancoMoncaroWhiteWine.setDrinkType("White Wine");
        biancoMoncaroWhiteWine.setUnitContent(1.6);
        biancoMoncaroWhiteWine.setImagePath(whiteWineImage);

        whiteWineContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, whiteWine.getDrinkName());

        whiteWineContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, whiteWine.getDrinkType());

        whiteWineContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, whiteWine.getDrinkVolume());

        whiteWineContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, whiteWine.getImagePath());

        whiteWineContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, whiteWine.getUnitContent());


        sauvignoBlancContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, sauvignoBlanc.getDrinkName());

        sauvignoBlancContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, sauvignoBlanc.getDrinkType());

        sauvignoBlancContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, sauvignoBlanc.getDrinkVolume());

        sauvignoBlancContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, sauvignoBlanc.getImagePath());

        sauvignoBlancContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, sauvignoBlanc.getUnitContent());



        biancoMoncaroWhiteWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, biancoMoncaroWhiteWine.getDrinkName());

        biancoMoncaroWhiteWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, biancoMoncaroWhiteWine.getDrinkType());

        biancoMoncaroWhiteWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, biancoMoncaroWhiteWine.getDrinkVolume());

        biancoMoncaroWhiteWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, biancoMoncaroWhiteWine.getImagePath());

        biancoMoncaroWhiteWineContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, biancoMoncaroWhiteWine.getUnitContent());

        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, whiteWineContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, sauvignoBlancContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null,  biancoMoncaroWhiteWineContentValue);



        db.close();
    }

    public void insertLagerIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues stellaContentValues = new ContentValues();
        ContentValues budContentValues = new ContentValues();


        int beerImage = context.getResources().getIdentifier("beerbottle", "drawable", context.getPackageName());

        AlcoholicDrink stellaPint = new AlcoholicDrink("5% Pint Stella Atoris ", 568, "Lager", 2.8, beerImage);
        stellaPint.setDrinkName("5% Pint Stella Atoris");
        stellaPint.setDrinkVolume(568);
        stellaPint.setDrinkType("Lager");
        stellaPint.setUnitContent(2.8);
        stellaPint.setImagePath(beerImage);


        AlcoholicDrink bottleBud = new AlcoholicDrink("5% BudWeiser Bottle ", 355, "Lager", 1.55, beerImage);
        bottleBud.setDrinkName("5% BudWeiser Bottle");
        bottleBud.setDrinkVolume(355);
        bottleBud.setDrinkType("Lager");
        bottleBud.setUnitContent(1.55);
        bottleBud.setImagePath(beerImage);


        stellaContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, stellaPint.getDrinkName());

        stellaContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, stellaPint.getDrinkType());

        stellaContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, stellaPint.getDrinkVolume());

        stellaContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, stellaPint.getImagePath());

        stellaContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, stellaPint.getUnitContent());


        budContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, bottleBud.getDrinkName());

        budContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, bottleBud.getDrinkType());

        budContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, bottleBud.getDrinkVolume());

        budContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, bottleBud.getImagePath());

        budContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, bottleBud.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, stellaContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, budContentValues);


        db.close();


    }


    public void insertCraftBeerIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues brewDogDeadPonyContentValues = new ContentValues();
        ContentValues blueMoonContentValues = new ContentValues();


        int beerImage = context.getResources().getIdentifier("craft_beer_bottle", "drawable", context.getPackageName());

        AlcoholicDrink brewDogDeadPony= new AlcoholicDrink("Bottle Brewdog Dead Pony Club", 330, "CraftBeer", 1.25, beerImage);
        brewDogDeadPony.setDrinkName("Bottle Brewdog Dead Pony Club");
        brewDogDeadPony.setDrinkVolume(330);
        brewDogDeadPony.setDrinkType("CraftBeer");
        brewDogDeadPony.setUnitContent(1.25);
        brewDogDeadPony.setImagePath(beerImage);


        AlcoholicDrink blueMoon = new AlcoholicDrink("5.4% Blue Moon Bottle", 330, "Lager", 1.8, beerImage);
        blueMoon.setDrinkName("5.4% Blue Moon Bottle");
        blueMoon.setDrinkVolume(355);
        blueMoon.setDrinkType("CraftBeer");
        blueMoon.setUnitContent(1.55);
        blueMoon.setImagePath(beerImage);


        brewDogDeadPonyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME,  brewDogDeadPony.getDrinkName());

        brewDogDeadPonyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, brewDogDeadPony.getDrinkType());

        brewDogDeadPonyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, brewDogDeadPony.getDrinkVolume());

        brewDogDeadPonyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, brewDogDeadPony.getImagePath());

        brewDogDeadPonyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, brewDogDeadPony.getUnitContent());


        blueMoonContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, blueMoon.getDrinkName());

        blueMoonContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, blueMoon.getDrinkType());

        blueMoonContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, blueMoon.getDrinkVolume());

        blueMoonContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, blueMoon.getImagePath());

        blueMoonContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, blueMoon.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, brewDogDeadPonyContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, blueMoonContentValues);


        db.close();


    }

    public void insertRealAleIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues newcastleBrownContentValues = new ContentValues();
        ContentValues londonPrideontentValues = new ContentValues();


        int beerImage = context.getResources().getIdentifier("real_ale_listing", "drawable", context.getPackageName());

        AlcoholicDrink newcastleBrown= new AlcoholicDrink("4.7% Pint Newcastle Brown ale", 550, "RealAle", 2.6, beerImage);
        newcastleBrown.setDrinkName("4.7% Pint Newcastle Brown ale");
        newcastleBrown.setDrinkVolume(550);
        newcastleBrown.setDrinkType("RealAle");
        newcastleBrown.setUnitContent(2.6);
        newcastleBrown.setImagePath(beerImage);


        AlcoholicDrink londonPride = new AlcoholicDrink("500ml 4.7% Fullers London Pride", 500, "RealAle", 2.35, beerImage);
        londonPride.setDrinkName("4.7% 500ml Fullers London Pride");
        londonPride.setDrinkVolume(500);
        londonPride.setDrinkType("RealAle");
        londonPride.setUnitContent(2.35);
        londonPride.setImagePath(beerImage);


        newcastleBrownContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME,  newcastleBrown.getDrinkName());

        newcastleBrownContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, newcastleBrown.getDrinkType());

        newcastleBrownContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, newcastleBrown.getDrinkVolume());

        newcastleBrownContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, newcastleBrown.getImagePath());

        newcastleBrownContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, newcastleBrown.getUnitContent());


        londonPrideontentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, londonPride.getDrinkName());

        londonPrideontentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, londonPride.getDrinkType());

        londonPrideontentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, londonPride.getDrinkVolume());

        londonPrideontentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, londonPride.getImagePath());

        londonPrideontentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, londonPride.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, newcastleBrownContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, londonPrideontentValues);


        db.close();


    }
    public void insertStoutIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues guinessContentValues = new ContentValues();
        ContentValues brewdogBlackHeartContentValues = new ContentValues();


        int beerImage = context.getResources().getIdentifier("stout_listing", "drawable", context.getPackageName());

        AlcoholicDrink guinnessOriginal= new AlcoholicDrink("500ml Guinness Original", 500, "Stout", 2.1, beerImage);
        guinnessOriginal.setDrinkName("500ml Guinness Original");
        guinnessOriginal.setDrinkVolume(500);
        guinnessOriginal.setDrinkType("Stout");
        guinnessOriginal.setUnitContent(2.1);
        guinnessOriginal.setImagePath(beerImage);


        AlcoholicDrink brewdogBlackHeart = new AlcoholicDrink("330ml Brewdog Blackheart Stout", 330, "Stout", 1.55, beerImage);
        brewdogBlackHeart.setDrinkName("330ml Brewdog Blackheart Stout");
        brewdogBlackHeart.setDrinkVolume(330);
        brewdogBlackHeart.setDrinkType("Stout");
        brewdogBlackHeart.setUnitContent(1.55);
        brewdogBlackHeart.setImagePath(beerImage);


        guinessContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME,  guinnessOriginal.getDrinkName());

        guinessContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, guinnessOriginal.getDrinkType());

        guinessContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, guinnessOriginal.getDrinkVolume());

        guinessContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, guinnessOriginal.getImagePath());

        guinessContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, guinnessOriginal.getUnitContent());


        brewdogBlackHeartContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, brewdogBlackHeart.getDrinkName());

        brewdogBlackHeartContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, brewdogBlackHeart.getDrinkType());

        brewdogBlackHeartContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, brewdogBlackHeart.getDrinkVolume());

        brewdogBlackHeartContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, brewdogBlackHeart.getImagePath());

        brewdogBlackHeartContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, brewdogBlackHeart.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, guinessContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, brewdogBlackHeartContentValues);


        db.close();


    }

    public void insertWhiskeyIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues jackDanielsHoneyContentValues = new ContentValues();
        ContentValues jamesonswhiskeyContentValue = new ContentValues();


        int whiskeyImage = context.getResources().getIdentifier("whiskey_listing", "drawable", context.getPackageName());

        AlcoholicDrink jackDanielsHoney= new AlcoholicDrink("One Shot Jack Daniels Tennessee Honey", 25, "Whiskey", 1, whiskeyImage);
        jackDanielsHoney.setDrinkName("One Shot Jack Daniels Tennessee Honey");
        jackDanielsHoney.setDrinkVolume(25);
        jackDanielsHoney.setDrinkType("Whiskey");
        jackDanielsHoney.setUnitContent(1);
        jackDanielsHoney.setImagePath(whiskeyImage);


        AlcoholicDrink jamesonsWhiskey = new AlcoholicDrink("One shot Jameson Whiskey", 25, "Whiskey", 1, whiskeyImage);
        jamesonsWhiskey.setDrinkName("One shot Jameson Whiskey");
        jamesonsWhiskey.setDrinkVolume(25);
        jamesonsWhiskey.setDrinkType("Whiskey");
        jamesonsWhiskey.setUnitContent(1);
        jamesonsWhiskey.setImagePath(whiskeyImage);


        jackDanielsHoneyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME,  jackDanielsHoney.getDrinkName());

        jackDanielsHoneyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, jackDanielsHoney.getDrinkType());

        jackDanielsHoneyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, jackDanielsHoney.getDrinkVolume());

        jackDanielsHoneyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, jackDanielsHoney.getImagePath());

        jackDanielsHoneyContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, jackDanielsHoney.getUnitContent());


        jamesonswhiskeyContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, jamesonsWhiskey.getDrinkName());

        jamesonswhiskeyContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, jamesonsWhiskey.getDrinkType());

        jamesonswhiskeyContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, jamesonsWhiskey.getDrinkVolume());

        jamesonswhiskeyContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, jamesonsWhiskey.getImagePath());

        jamesonswhiskeyContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, jamesonsWhiskey.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, jackDanielsHoneyContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, jamesonswhiskeyContentValue);


        db.close();


    }

    public void insertVodkaIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues smirnoffContentValues = new ContentValues();
        ContentValues russianStanardContentValue = new ContentValues();


        int vodkaImage = context.getResources().getIdentifier("vodka", "drawable", context.getPackageName());

        AlcoholicDrink smirnoffRedLabel= new AlcoholicDrink("One Shot Smirnoff Red Label Vodka", 25, "Vodka", 1, vodkaImage);
        smirnoffRedLabel.setDrinkName("One Shot Smirnoff Red Label Vodka");
        smirnoffRedLabel.setDrinkVolume(25);
        smirnoffRedLabel.setDrinkType("Vodka");
        smirnoffRedLabel.setUnitContent(1);
        smirnoffRedLabel.setImagePath(vodkaImage);


        AlcoholicDrink russianStandard = new AlcoholicDrink("One shot Russian Standard Vodka", 25, "Vodka", 1, vodkaImage);
        russianStandard.setDrinkName("One shot Russian Standard Vodka");
        russianStandard.setDrinkVolume(25);
        russianStandard.setDrinkType("Vodka");
        russianStandard.setUnitContent(1);
        russianStandard.setImagePath(vodkaImage);


        smirnoffContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME,  smirnoffRedLabel.getDrinkName());

        smirnoffContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, smirnoffRedLabel.getDrinkType());

        smirnoffContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, smirnoffRedLabel.getDrinkVolume());

        smirnoffContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, smirnoffRedLabel.getImagePath());

        smirnoffContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, smirnoffRedLabel.getUnitContent());


        russianStanardContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, russianStandard.getDrinkName());

        russianStanardContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, russianStandard.getDrinkType());

        russianStanardContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, russianStandard.getDrinkVolume());

        russianStanardContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, russianStandard.getImagePath());

        russianStanardContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, russianStandard.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, smirnoffContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, russianStanardContentValue);


        db.close();


    }

    public void insertGinIntoDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues bombayContentValues = new ContentValues();
        ContentValues hendricksContentValue = new ContentValues();


        int ginImage = context.getResources().getIdentifier("gin", "drawable", context.getPackageName());

        AlcoholicDrink bombayGin= new AlcoholicDrink("One shot Bombay Sapphire", 25, "Gin", 1, ginImage);
        bombayGin.setDrinkName("One shot Bombay Sapphire");
        bombayGin.setDrinkVolume(25);
        bombayGin.setDrinkType("Gin");
        bombayGin.setUnitContent(1);
        bombayGin.setImagePath(ginImage);


        AlcoholicDrink hendricksGin = new AlcoholicDrink("One shot Hendricks Gin", 25, "Gin", 1, ginImage);
        hendricksGin.setDrinkName("One shot Hendricks Gin");
        hendricksGin.setDrinkVolume(25);
        hendricksGin.setDrinkType("Gin");
        hendricksGin.setUnitContent(1);
        hendricksGin.setImagePath(ginImage);


        bombayContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME,  bombayGin.getDrinkName());

        bombayContentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, bombayGin.getDrinkType());

        bombayContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, bombayGin.getDrinkVolume());

        bombayContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, bombayGin.getImagePath());

        bombayContentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, bombayGin.getUnitContent());


        hendricksContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, hendricksGin.getDrinkName());

        hendricksContentValue.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, hendricksGin.getDrinkType());

        hendricksContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, hendricksGin.getDrinkVolume());

        hendricksContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, hendricksGin.getImagePath());

        hendricksContentValue.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, hendricksGin.getUnitContent());



        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, bombayContentValues);
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, hendricksContentValue);


        db.close();


    }
    public ArrayList<Drink> populateDrinksArrayFromDataBaseGeneric(Context context,String typeOfDrink) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String drinkType = typeOfDrink;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+drinkType+"'", null);


        String wholeCoffeeObject = "";

        createToastWithText("curosr value" + cursor.getCount());

        while (cursor.moveToNext()) {

            Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            coffee.setDrinkName(cursor.getString(1));
            coffee.setImagePath(cursor.getInt(4));
            createToastWithText(wholeCoffeeObject);

            mdrinks.add(coffee);


        }
        cursor.close();
        db.close();
        return mdrinks;
    }

    public ArrayList<Drink> populateDrinksArrayFromDataBase(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String coffeeQuery = "Coffee";
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+coffeeQuery+"'", null);


        String wholeCoffeeObject = "";

        createToastWithText("curosr value" + cursor.getCount());

        while (cursor.moveToNext()) {

            Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            coffee.setDrinkName(cursor.getString(1));
            coffee.setImagePath(cursor.getInt(4));
            createToastWithText(wholeCoffeeObject);

            mdrinks.add(coffee);


        }
        cursor.close();
        db.close();
        return mdrinks;
    }

    public ArrayList<Drink> populateDrinksArrayFromDataBaseTea(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//                Cursor checkQuantitycusor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);
        String tea = "Tea";
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+tea+"'", null);


        String wholeCoffeeObject = "";

        createToastWithText("curosr value" + cursor.getCount());

        while (cursor.moveToNext()) {

            Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            coffee.setDrinkName(cursor.getString(1));
            coffee.setImagePath(cursor.getInt(4));
            createToastWithText(wholeCoffeeObject);

            mdrinks.add(coffee);


        }
        cursor.close();
        db.close();

        return mdrinks;
    }

    public ArrayList<Drink> populateDrinksArrayFromDataBaseRedWine(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//                Cursor checkQuantitycusor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);
        String redWine = "Red Wine";
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+redWine+"'", null);


        String wholeCoffeeObject = "";

        createToastWithText("curosr value" + cursor.getCount());

        while (cursor.moveToNext()) {

            AlcoholicDrink alcoholicDrink = new AlcoholicDrink(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            alcoholicDrink.setDrinkName(cursor.getString(1));
            alcoholicDrink.setImagePath(cursor.getInt(4));
            createToastWithText(wholeCoffeeObject);

            mdrinks.add(alcoholicDrink);


        }
        cursor.close();
        db.close();

        return mdrinks;
    }

    public ArrayList<Drink> populateDrinksArrayFromDataBaseBlackCoffee(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//                Cursor checkQuantitycusor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);
        String blackCoffee = "Black Coffee";
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+blackCoffee+"'", null);


        String wholeCoffeeObject = "";

        createToastWithText("curosr value" + cursor.getCount());
            while (cursor.moveToNext()) {

                Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
                coffee.setDrinkName(cursor.getString(1));
                coffee.setImagePath(cursor.getInt(4));
                createToastWithText(wholeCoffeeObject);

                mdrinks.add(coffee);


            }
        cursor.close();
        db.close();

        return mdrinks;
    }



    public ArrayList<Drink> populateDrinksArrayFromDataBaseWhiteWine(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//                Cursor checkQuantitycusor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);
        String whiteWine = "White Wine";
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+whiteWine+"'", null);


        String wholeCoffeeObject = "";

        createToastWithText("curosr value" + cursor.getCount());

        while (cursor.moveToNext()) {

            AlcoholicDrink alcoholicDrink = new AlcoholicDrink(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            alcoholicDrink.setDrinkName(cursor.getString(1));
            alcoholicDrink.setImagePath(cursor.getInt(4));
            createToastWithText(wholeCoffeeObject);

            mdrinks.add(alcoholicDrink);


        }
        cursor.close();
        db.close();

        return mdrinks;
    }


    public String printAllFromDb(Context context, String nameOfDrink) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " " + "WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_NAME + "=" + "'" + nameOfDrink + "'" + " ", null);


        String wholeCoffeeObject = "";


        if (cursor.moveToFirst()) {
            createToastWithText("Found a drink");
            createToastWithText("drink id" + cursor.getString(0) + "drink name" + cursor.getString(1));
            setDrinksID(cursor.getString(0));

        }

        cursor.close();
        db.close();
        createToastWithText("drinks id from print all from db" + getDrinksID());
        return getDrinksID();

    }


    public void insertIntoDB(Context context, String drinkName, String drinkType, double drinkVolume, double drinkCaffine) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int defaultImage = context.getResources().getIdentifier("caffine_cup", "drawable", context.getPackageName());


        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, drinkName);

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, drinkType);

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, drinkVolume);

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, defaultImage);


        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, drinkCaffine);


        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, contentValues);


        createToastWithText("added new record to db");
//                createToastWithText("curosr dump"+ DatabaseUtils.dumpCursorToString(cursor))
// ;
        db.close();


    }


    public long addDrinkQuanitiyValues(Context context, String drinksID, String userID) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        TimeHandler timeHandler = new TimeHandler();


        contentValues.put(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.drink_id_fk, drinksID);

        contentValues.put(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk, userID);


        contentValues.put(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE, timeHandler.getTotalDateWithTime());

        createToastWithText("added to drinks quanitiy db");
        long lastRowAdded = db.insert(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME, null, contentValues);

        db.close();

        return lastRowAdded;


    }


    public int checkDrinksQuantiiyValuesExist(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME, null);

        String drinksQuant = "";


        createToastWithText("number of items in db value" + cursor.getCount());

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {

                drinksQuant = "pk " + cursor.getString(0) + "drink key" + cursor.getString(1) + "user key" + cursor.getString(2) + "drink quantity:" +
                        cursor.getString(3) + "drink date: " + cursor.getString(4);
                createToastWithText("drink" + drinksQuant);
            }

        }

        cursor.close();


        return cursor.getCount();

    }


    public void updateUsingCursor(Context context, String drinkID) {

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean userExists = false;


        //Performs the sql query on the email a user has passed, to check if present in the DB.
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.drink_id_fk + "=" + "'" + drinkID + "'", null);
        //If a column number exists related to the query, then the user is sent back to the login screen.
//        cursor.moveToFirst();

//        if (cursor.getCount() == 1) {
        int one = 1;
        int zero = 0;

        Cursor checkQuantitycusor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);

        createToastWithText("count of the query" + checkQuantitycusor.getCount());
        if (checkQuantitycusor.getCount() > 0) {

            Cursor updateCursor = db.rawQuery("UPDATE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " SET "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " " + "= "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " + 1" + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);


            createToastWithText("Update quanitiy" + DatabaseUtils.dumpCursorToString(updateCursor));
            createToastWithText("roW NUMBER" + updateCursor.getCount());
            updateCursor.close();

        } else {
            createToastWithText("Already ypdated");

            checkQuantitycusor.close();
//
        }
//            createToastWithText("alreayd around");
//        }else if (checkQuantitycusor.getCount()==0){
//            checkQuantitycusor.moveToFirst();
//            Cursor updateCursor = db.rawQuery("UPDATE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " SET "
//                    +     DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink+" "+"= "
//                    +DrinksCategoryDrinkQuanitiy.DrinksQusantityEntry.quantity_of_drink+" + 1", null);
//
//
//            createToastWithText("Update quanitiy" + DatabaseUtils.dumpCursorToString(updateCursor));
//            createToastWithText("roW NUMBER"+updateCursor.getCount());
//
//            checkQuantitycusor.close();
//            updateCursor.close();
//
//        }


//        }

        checkQuantitycusor.close();
        db.close();

    }


    public String getLastAddedDrinksQuanitiy(Context context, String drinkID) {

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Performs the sql query on the email a user has passed, to check if present in the DB.

        Cursor cursor = db.rawQuery("SELECT * from " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " " + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.drink_id_fk + " =" + "'" + drinkID + "'" + " " + " ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ", null);

        int one = 1;
        String lastAddedID = " ";



        if (cursor.moveToFirst()) {

            lastAddedID = "id" + cursor.getString(0)+"id"+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4);

            String lastAddedIDActual = cursor.getString(0);

            setLastAddedID(lastAddedID);
//            db.rawQuery("UPDATE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " SET "
//                    +     DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink+" "+"= "
//                    +DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink+" + 1"+ " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " ="+"'"+lastAddedID+"'", null);
            cursor.close();
//            updateCursor.close();


        }
        createToastWithText("last added id before return"+lastAddedID);
        db.close();
        return getLastAddedID();


    }

    public ArrayList<Drink> populateDrinksReciptAdatper(Context context) {

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM "+
           DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME +" WHERE " +
                ""+DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk+" = "+"'"+getUserId(getApplicationContext())+"'"

                +" ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ",null);
//




        createToastWithText("number of items in db value" + cursor.getCount());

        drinksRecipt.clear();
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {

        createToastWithText("found user sesh");



//                createToastWithText("user id related drinks"+drinksQuant);

//
                Coffee coffee = new Coffee(test(getApplicationContext(),cursor.getString(1)), cursor.getString(4));
                coffee.setDrinkName(test(getApplicationContext(),cursor.getString(1)));
                coffee.setDate(cursor.getString(4));

                drinksRecipt.add(coffee);




            }


        }

        cursor.close();
        db.close();

        createToastWithText("size of recipt"+drinksRecipt.size());

        return drinksRecipt;

    }

    public String test(Context context,String drinksID){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor newCursor = db.rawQuery("SELECT * FROM "+
                DrinksContract.DrinksCategoryEntry.TABLE_NAME +" WHERE " +
                ""+DrinksContract.DrinksCategoryEntry.DRINKS_ID +" = "+"'"+drinksID+"'",null);
        if (newCursor.moveToFirst()){
                String newCursorStr = newCursor.getString(0)+newCursor.getString(1)+newCursor.getString(2)+newCursor.getString(3)+newCursor.getString(4);

                createToastWithText("new cursor str"+newCursorStr);

        }
//        newCursor.close();

        return newCursor.getString(1);
    }

    public void getIDcHEC(Context context, String id) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        createToastWithText("getidchec method");
        String num = "1";
        Cursor cursor = db.rawQuery("SELECT * from " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + id + "'", null);

        if (cursor.moveToFirst()) {
            String drinkDetialsFromID = "pk " + cursor.getString(0) + "drink key" + cursor.getString(1) + "user key" + cursor.getString(2) + "drink quantity:" +
                    cursor.getString(3) + "drink date: " + cursor.getString(4);


            createToastWithText("drink from the getIDchec methid"+drinkDetialsFromID);
        }
        cursor.close();
        db.close();

    }

    public void upateIDToHave0Value(Context context,String drinksID){

        createToastWithText("drimks id from inside update method");
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + drinksID + "'"+
                " ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ", null);

        if (cursor.moveToFirst()){
            createToastWithText("DELETED");

            String sql = "DELETE FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME +
                    " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + drinksID + "'"+"";



            db.execSQL(sql);


            cursor.close();
            db.close();
//            db.execSQL(sql);
        }


    }


    public void populateAchievementsDatabase(Context context){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValuesTwo = new ContentValues();
        ContentValues contentValuesThree= new ContentValues();

        ContentValues contentValuesFour= new ContentValues();

        int achivementImage = context.getResources().getIdentifier("locked_achievement", "drawable", context.getPackageName());

        Achievement achievement = new Achievement("Rookie","Created your first water goal.",achivementImage);
        achievement.setName("Rookie");
        achievement.setDescription("Created your first water goal.");

        achievement.setAchievementAvater(achivementImage);


        Achievement achievementTwo = new Achievement("Water goals","Completed 3 water goals!",achivementImage);
        achievementTwo.setName("Water goals");
        achievementTwo.setDescription("Completed 3 water goals!");

        int achivementTwoImage = context.getResources().getIdentifier("three_streak_grey", "drawable", context.getPackageName());

        achievementTwo.setAchievementAvater(achivementTwoImage);

        int achievementThreeImage = context.getResources().getIdentifier("coffee_killer", "drawable", context.getPackageName());

        Achievement achievementThree= new Achievement("Coffee Killer","Drank enough water to combat caffeine.",achievementThreeImage);

        achievementThree.setName("Coffee Killer");
        achievementThree.setDescription("Drank enough water to combat caffeine.");


        int achievementFourImage = context.getResources().getIdentifier("alcohol_killer", "drawable", context.getPackageName());

        Achievement achievementFour= new Achievement("Alcohol Killer","Drank water to combat Alcohol.",achievementThreeImage);

        achievementFour.setName("Alcohol Killer");
        achievementFour.setDescription("Drank water to combat Alcohol.");

        achievementFour.setAchievementAvater(achievementFourImage);


        contentValues.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_NAME, achievement.getName());
        contentValues.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_DESCRIPTION, achievement.getDescription());
        contentValues.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_IMAGE, achievement.getAchievementAvater());


        contentValuesTwo.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_NAME, achievementTwo.getName());
        contentValuesTwo.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_DESCRIPTION, achievementTwo.getDescription());
        contentValuesTwo.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_IMAGE, achievementTwo.getAchievementAvater());

        contentValuesThree.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_NAME, achievementThree.getName());
        contentValuesThree.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_DESCRIPTION, achievementThree.getDescription());
        contentValuesThree.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_IMAGE, achievementThree.getAchievementAvater());


        contentValuesFour.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_NAME, achievementFour.getName());
        contentValuesFour.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_DESCRIPTION, achievementFour.getDescription());
        contentValuesFour.put(AchievementContract.AchievementEntry.COLUMN_ACHIEVEMENT_IMAGE, achievementFour.getAchievementAvater());



        db.insert(AchievementContract.AchievementEntry.TABLE_NAME, null, contentValues);
        db.insert(AchievementContract.AchievementEntry.TABLE_NAME, null, contentValuesTwo);
        db.insert(AchievementContract.AchievementEntry.TABLE_NAME, null, contentValuesThree);
        db.insert(AchievementContract.AchievementEntry.TABLE_NAME, null, contentValuesFour);

        db.close();

    }

    public ArrayList<Achievement> populateAchivementsFromDataBase(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AchievementContract.AchievementEntry.TABLE_NAME, null);


        String wholeCoffeeObject = "";

        createToastWithText("cursor value" + cursor.getCount());

        setNumOfAchivementsInDB(String.valueOf(cursor.getCount()));

        while (cursor.moveToNext()) {

            Achievement achievement = new Achievement(cursor.getString(0), cursor.getString(1),cursor.getInt(2));
            achievement.setName(cursor.getString(1));
            achievement.setDescription(cursor.getString(2));
            achievement.setAchievementAvater(cursor.getInt(3));
            createToastWithText(wholeCoffeeObject);

            achivements.add(achievement);


        }
        cursor.close();
        db.close();
        return achivements;
    }


    public String getNumOfAchivementsInDB() {
        return numOfAchivementsInDB;
    }

    public void setNumOfAchivementsInDB(String numOfAchivementsInDB) {
        this.numOfAchivementsInDB = numOfAchivementsInDB;
    }





}






