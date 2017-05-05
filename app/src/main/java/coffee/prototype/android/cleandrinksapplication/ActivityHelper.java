package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.Model.AlcoholicDrink;
import coffee.prototype.android.cleandrinksapplication.Model.Coffee;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.Model.TimeHandler;
import coffee.prototype.android.cleandrinksapplication.Model.User;
import coffee.prototype.android.cleandrinksapplication.data.DrinksCategoryDrinkQuanitiy;
import coffee.prototype.android.cleandrinksapplication.data.DrinksContract;
import coffee.prototype.android.cleandrinksapplication.data.SessionManager;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;
import coffee.prototype.android.cleandrinksapplication.data.WeightContract;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * created by Nathan on 28/03/2017.
 * tea icons from http://www.flaticon.com/free-icon/cup-with-tea-bag_68890#term=tea&page=2&position=35
 */

public class ActivityHelper {


    //changed type of activity helper to just a generic java class, as otherwise it requries a getCreateMethod, which is useless for a class that's just used for code re-use.
//    private String validatedString;


    private String goalID;
    private ArrayList<Drink> mdrinks = new ArrayList<>();
    private ArrayList<Drink> drinksRecipt = new ArrayList<Drink>();

    public double getTotalForProgres() {
        return totalForProgres;
    }

    public void setTotalForProgres(double totalForProgres) {
        this.totalForProgres = totalForProgres;
    }

    private double totalForProgres;

    private String drinksID;

    public String getLastAddedID() {
        return LastAddedID;
    }

    public void setLastAddedID(String lastAddedID) {
        LastAddedID = lastAddedID;
    }

    private String LastAddedID;


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

    public int getWeightFromUserSession(Context context) {
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
        cursor.close();
        db.close();
//        createToastWithText("Returning" + num);
        return num;


    }


    public String getDrinksID() {
        return drinksID;
    }

    public void setDrinksID(String drinksID) {
        this.drinksID = drinksID;
    }



    public ArrayList<Drink> populateDrinksArrayFromDataBaseGeneric(Context context,String typeOfDrink) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String drinkType = typeOfDrink;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+drinkType+"'", null);


        String wholeCoffeeObject = "";


        while (cursor.moveToNext()) {

            Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            coffee.setDrinkName(cursor.getString(1));
            coffee.setDrinkType(cursor.getString(2));
            coffee.setImagePath(cursor.getInt(4));

            mdrinks.add(coffee);


        }
        cursor.close();
        db.close();
        return mdrinks;
    }

    public double returnTotalAmountOfWaterConsumedContext (Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String drinkType = "Water";
        Cursor cursor = db.rawQuery("SELECT "+ DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME+" FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+drinkType+"'", null);

        double total = 0;
        String wholeCoffeeObject = "";



        while (cursor.moveToNext()) {

                cursor.getInt(0);
             total = cursor.getInt(0) + cursor.getInt(0);
            setTotalForProgres(total);




        }

        cursor.close();
        db.close();
        double percentage = ((getTotalForProgres()*100)/3000);
//        createToastWithText("total after while"+getTotalForProgres());
//        createToastWithText("apparent percentage"+percentage);
        int percentageInt = (int) percentage;
        return percentageInt;
    }

    /**
     * @todo Add this to progress activity
     * @param context
     * @return
     */
    public double returnTotalAmountOfWaterConsumedContextFromDb (Context context) {

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String water= "Water";


        Cursor cursor = db.rawQuery("SELECT * FROM "+
                DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME+","+ DrinksContract.DrinksCategoryEntry.TABLE_NAME+" WHERE " +
                ""+DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk+ " = "+"'"+getUserId(getApplicationContext())+"'"
                + " AND "+ DrinksContract.DrinksCategoryEntry.DRINK_TYPE +"= "+"'"+water+"'",null);
//
//
////        //        String name = "water total";
////        Cursor cursor = db.rawQuery("SELECT sum(DISTINCT "+ DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME+") AS "+"'"+name+"'"+
////                " FROM "+
////                DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME+","+ DrinksContract.DrinksCategoryEntry.TABLE_NAME+" WHERE " +
////                ""+DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk+ " = "+"'"+getUserId(getApplicationContext())+"'"
////                + " AND "+ DrinksContract.DrinksCategoryEntry.DRINK_TYPE +"= "+"'"+water+"'",null);
////
////
//
//        //S
//
////        String name = "water total";
////        Cursor cursor = db.rawQuery("SELECT * FROM "+
////                DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME+","+ DrinksContract.DrinksCategoryEntry.TABLE_NAME+" WHERE " +
////                ""+DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk+ " = "+"'"+getUserId(getApplicationContext())+"'"
////                + " AND "+ DrinksContract.DrinksCategoryEntry.DRINK_TYPE +"= "+"'"+water+"'",null);
//
//        Cursor cursor = db.rawQuery("SELECT * FROM "+
//               DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME +" WHERE " +
//                ""+ "drinksCat."+DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk+" = "+"'"+getUserId(getApplicationContext())+"'",null);
//
        if (cursor.moveToFirst()) {


            while (cursor.moveToNext()) {

                String value =
                        "Type" + cursor.getString(7) + "Volume" + cursor.getString(8);

//                createToastWithText("values" + value);

//            createToastWithText("progress of total in if"+getTotalForProgres());

            }
//        }
        }
//        createToastWithText("progress of total"+getTotalForProgres());
//
        double percentage = ((getTotalForProgres()*100)/3000);
//        createToastWithText("total after while"+getTotalForProgres());
//        createToastWithText("apparent percentage"+percentage);
        int percentageInt = (int) percentage;
        cursor.close();
        db.close();
        return 10;
    }



        public ArrayList<Drink> populateDrinksArrayFromDataBase(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String coffeeQuery = "Coffee";
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_TYPE+ " ="+"'"+coffeeQuery+"'", null);


        String wholeCoffeeObject = "";


        while (cursor.moveToNext()) {

            Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            coffee.setDrinkName(cursor.getString(1));
            coffee.setDrinkType(cursor.getString(2));
            coffee.setImagePath(cursor.getInt(4));


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


        while (cursor.moveToNext()) {

            Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            coffee.setDrinkName(cursor.getString(1));
            coffee.setDrinkType(cursor.getString(2));

            coffee.setImagePath(cursor.getInt(4));

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


        while (cursor.moveToNext()) {

            AlcoholicDrink alcoholicDrink = new AlcoholicDrink(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            alcoholicDrink.setDrinkName(cursor.getString(1));
            alcoholicDrink.setDrinkType(cursor.getString(2));

            alcoholicDrink.setImagePath(cursor.getInt(4));


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

            while (cursor.moveToNext()) {

                Coffee coffee = new Coffee(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
                coffee.setDrinkName(cursor.getString(1));
                coffee.setDrinkType(cursor.getString(2));

                coffee.setImagePath(cursor.getInt(4));

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


        while (cursor.moveToNext()) {

            AlcoholicDrink alcoholicDrink = new AlcoholicDrink(cursor.getString(1), cursor.getDouble(3), cursor.getString(2), cursor.getDouble(5), cursor.getInt(4));
            alcoholicDrink.setDrinkName(cursor.getString(1));
            alcoholicDrink.setDrinkType(cursor.getString(2));

            alcoholicDrink.setImagePath(cursor.getInt(4));

            mdrinks.add(alcoholicDrink);


        }
        cursor.close();
        db.close();

        return mdrinks;
    }


    public String getDrinksIDFromDrinkName(Context context, String nameOfDrink) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME + " " + "WHERE " + DrinksContract.DrinksCategoryEntry.DRINK_NAME + "=" + "'" + nameOfDrink + "'" + " ", null);




        if (cursor.moveToFirst()) {

            setDrinksID(cursor.getString(0));

        }

        cursor.close();
        db.close();

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


        db.close();


    }


    public long addDrinkQuanitiyValues(Context context, String drinksID, String userID) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        TimeHandler timeHandler = new TimeHandler();


        contentValues.put(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.drink_id_fk, drinksID);

        contentValues.put(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk, userID);
        //need to add another here for the date.
        contentValues.put(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE_NAME, timeHandler.dayName());



        contentValues.put(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE, timeHandler.getTotalDateWithTime());


        long lastRowAdded = db.insert(DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME, null, contentValues);

        db.close();

        return lastRowAdded;


    }


    public void updateUsingCursor(Context context, String drinkID) {

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean userExists = false;


        int one = 1;
        int zero = 0;

        Cursor checkQuantitycusor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);

        if (checkQuantitycusor.getCount() > 0) {

            Cursor updateCursor = db.rawQuery("UPDATE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " SET "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " " + "= "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " + 1" + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " =" + "'" + zero + "'", null);

            updateCursor.close();

        } else {

            checkQuantitycusor.close();
//
        }

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

            lastAddedID = "id" + cursor.getString(0);

            String lastAddedIDActual = cursor.getString(0);

            setLastAddedID(lastAddedID);

            cursor.close();


        }
//        createToastWithText("last added id before return"+lastAddedID);
        db.close();
        return getLastAddedID();


    }


    //Here!
    public ArrayList<Drink> populateDrinksReciptAdatper(Context context) {

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM "+
           DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME +" WHERE " +
                ""+DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk+" = "+"'"+getUserId(getApplicationContext())+"'"

                +" ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ",null);



        drinksRecipt.clear();
                if (cursor.moveToFirst()) {
                    do{
                        Coffee coffee = new Coffee(test(getApplicationContext(),cursor.getString(1)), cursor.getString(4),cursor.getString(5));
                        coffee.setDrinkName(test(getApplicationContext(),cursor.getString(1)));

                        coffee.setDate(cursor.getString(4));
                        coffee.setDateName(cursor.getString(5));
//                coffee.setDrinkName();

                        drinksRecipt.add(coffee);
                    }
            while (cursor.moveToNext());


            }






        cursor.close();
        db.close();


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

//                createToastWithText("new cursor str"+newCursorStr);

        }

        return newCursor.getString(1);
    }



    public void upateIDToHave0Value(Context context,String drinksID){

        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + drinksID + "'"+
                " ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ", null);

        if (cursor.moveToFirst()){
            createToastWithText("DELETED");



           String sql =  "UPDATE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " SET "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " " + "= "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " - 1" + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + drinksID + "'"+
            " ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ";

            db.execSQL(sql);


            cursor.close();
            db.close();
        }


    }



}






