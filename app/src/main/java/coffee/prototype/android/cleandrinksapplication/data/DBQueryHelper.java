package coffee.prototype.android.cleandrinksapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * created by Nathan on 04/04/2017.
 */

public class DBQueryHelper {


    /**
     * This is a generic method to add a drink to the Drinks category table.
     *
     * @param context      Refers to the context of the activity
     * @param drinkName    The name of the drink that's being added.
     * @param drinkType    Type of the drink that's being added
     * @param drinkVolume  The volume of the drink being added.
     * @param drinkCaffine Amount of caffine or can refer to acholic units
     * @param imagePath    The image icon of the drink, that will be shown in the drinks listing pages.
     */
    public void insertIntoDBImage(Context context, String drinkName, String drinkType, double drinkVolume, double drinkCaffine, int imagePath) {
        //Creates new instance of the database
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Sets to write, for adding new values to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Content values is used for database insertion to the corresponding table columns.
        ContentValues contentValues = new ContentValues();
        //Adds drink name to the drink name column.
        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, drinkName);
        //Adds drink Type to the drink name column.
        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, drinkType);
        //Adds drink volume  to the drink volume column.
        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, drinkVolume);
        //Adds drink name to the drink image resource column.
        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, imagePath);
        //Adds drink name to the drink unit / caffine unit  column.
        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, drinkCaffine);
        //Inserts the value into the database.
        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, contentValues);
        //closes the instance of the database open to avoid database leaks.
        db.close();


    }


    public void insertWaterIntoDatabase(Context context){

        int waterImage = context.getResources().getIdentifier("water_bottle", "drawable", context.getPackageName());

        insertIntoDBImage(context,"250ml Water","Water", 250, 0, waterImage);
        insertIntoDBImage(context,"500ml Water","Water", 500, 0, waterImage);
        insertIntoDBImage(context,"1000ml Water","Water", 1000, 0, waterImage);


    }


    public void insertLagerIntoDatabase(Context context){
        int beerImage = context.getResources().getIdentifier("beerbottle", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Pint Stella Atoris units:2.8","Lager", 568, 2.8, beerImage);
        insertIntoDBImage(context,"BudWeiser Bottle units:2.8","Lager", 355, 1.55, beerImage);
    }

    public void insertCraftBeerIntoDatabase(Context context){

        int beerImage = context.getResources().getIdentifier("craft_beer_bottle", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Brewdog Dead Pony units:1.25","CraftBeer", 330, 1.25, beerImage);
        insertIntoDBImage(context,"Blue Moon Bottle units:1.8","CraftBeer", 330, 1.8, beerImage);



    }


    public void insertRealAleBeerIntoDatabase(Context context){

        int realAleImage = context.getResources().getIdentifier("real_ale_listing", "drawable", context.getPackageName());
        insertIntoDBImage(context,"Newcastle Brown ale units:2.6","RealAle", 550, 2.6, realAleImage);
        insertIntoDBImage(context,"Fullers London Pride units:2.35","RealAle", 500, 2.35, realAleImage);



    }

    public void insertStoutIntoDatabase(Context context){
        int stoutImage = context.getResources().getIdentifier("stout_listing", "drawable", context.getPackageName());
        insertIntoDBImage(context,"Guinness Original units:2.1","Stout", 550, 2.1, stoutImage);
        insertIntoDBImage(context,"Brewdog Blackheart Stout units:1.55","Stout", 330, 1.55, stoutImage);
    }

    public void insertEspressoBasedDrinksIntoDB(Context context){


        int cappucinoImage = context.getResources().getIdentifier("cappuccino", "drawable", context.getPackageName());

        insertIntoDBImage(context,"x2 shot Cappuccino caffeine:184mg","Coffee", 236, 184, cappucinoImage);

        int flatWhiteImage = context.getResources().getIdentifier("gibraltar", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Flat white caffeine:184mg","Coffee", 236, 184, flatWhiteImage);

        int latteImage = context.getResources().getIdentifier("latte", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Single shot latte caffeine:92mg","Coffee", 350, 92, latteImage);


    }

    public void insertGinBasedDrinksIntoDB(Context context){


        int ginImage = context.getResources().getIdentifier("vodka", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Shot Bombay Sapphire units:1","Gin", 25, 1, ginImage);

        insertIntoDBImage(context,"shot Hendricks Gin units:1","Gin", 25, 1, ginImage);


    }

    public void insertTeaBasedDrinksIntoDB(Context context){


        int teaImage = context.getResources().getIdentifier("english_breakfast", "drawable", context.getPackageName());

        insertIntoDBImage(context,"3Min steep breakfast caffeine:22mg","Tea", 170, 22, teaImage);

        int blackTeaImage = context.getResources().getIdentifier("black_tea_cup", "drawable", context.getPackageName());

        insertIntoDBImage(context,"3Min Steep Black Tea caffeine:42mg","Tea", 236, 42, blackTeaImage);

        int greenTeaImage = context.getResources().getIdentifier("green_tea", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Green Tea caffeine:25mg","Tea", 236, 25, greenTeaImage);

        int decafTeaImage = context.getResources().getIdentifier("decaf_tea", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Decaf Tea caffeine:5mg","Tea", 236, 5, decafTeaImage);


    }

    public void insertVodkaBasedDrinksIntoDB(Context context){


        int vodkaImage = context.getResources().getIdentifier("gin", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Shot Smirnoff Red Label units:1","Vodka", 25, 1, vodkaImage);

        insertIntoDBImage(context,"Shot Russian Standard Vodka units:1","Vodka", 25, 1, vodkaImage);


    }

    public void insertWhiskeyBasedDrinksIntoDB(Context context){


        int whiskeyImage = context.getResources().getIdentifier("whiskey_listing", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Shot Jack Daniels Honey units:1","Whiskey", 25, 1, whiskeyImage);

        insertIntoDBImage(context,"Shot Jameson Whiskey units:1","Whiskey", 25, 1, whiskeyImage);


    }

    public void insertRedWineIntoDatabase(Context context){


        int redWineImage = context.getResources().getIdentifier("red_wine_item", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Sangiovese Red wine units:1.6","Red Wine", 125, 1.6, redWineImage);

        insertIntoDBImage(context,"Merlot Red wine units:1.6","Red Wine", 125, 1.6, redWineImage);


    }

    public void insertWhiteWineIntoDatabase(Context context){


        int whiteWineImage = context.getResources().getIdentifier("white_wine_item", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Sauvigno Blanc units:1.6","White Wine", 125, 1.6, whiteWineImage);

        insertIntoDBImage(context,"Bianco Moncaro units:1.4","White Wine", 125, 1.4, whiteWineImage);


    }

    public void insertBlackCoffeeBasedDrinks(Context context){
        int filterCoffeeImage = context.getResources().getIdentifier("filter_coffee", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Filter Coffee caffeine:236mg","Black Coffee", 236, 145, filterCoffeeImage);

        int frenchPressImage = context.getResources().getIdentifier("frenchpress", "drawable", context.getPackageName());


        insertIntoDBImage(context,"French press caffeine:163mg","Black Coffee", 236, 163, frenchPressImage);

        int mokaPotImage = context.getResources().getIdentifier("mokapot", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Moka Pot caffeine:90mg","Black Coffee", 236, 90, mokaPotImage);

        int instantCoffeeImage = context.getResources().getIdentifier("instant_coffee", "drawable", context.getPackageName());

        insertIntoDBImage(context,"Instant Coffee caffeine:57m","Black Coffee", 236, 57, instantCoffeeImage);




    }




    public int returnImageRelatedToDrinkTypeForAddDrinkDb(String drinkType,Context context){
        int defaultImage = 1;

        if(drinkType.equals("Red Wine")){
            defaultImage = context.getResources().getIdentifier("red_wine_item", "drawable", context.getPackageName());

        }else if(drinkType.equals("Coffee")){
            defaultImage = context.getResources().getIdentifier("caffine_cup", "drawable", context.getPackageName());

        }else if(drinkType.equals("Gin")){
            defaultImage = context.getResources().getIdentifier("vodka", "drawable", context.getPackageName());

        }else if(drinkType.equals("Lager")){
            defaultImage = context.getResources().getIdentifier("beerbottle", "drawable", context.getPackageName());

        }else if(drinkType.equals("RealAle")){
            defaultImage = context.getResources().getIdentifier("real_ale_listing", "drawable", context.getPackageName());

        }else if(drinkType.equals("Stout")){
            defaultImage = context.getResources().getIdentifier("stout_listing", "drawable", context.getPackageName());

        }else if(drinkType.equals("Vodka")){
            defaultImage = context.getResources().getIdentifier("gin", "drawable", context.getPackageName());

        }else if(drinkType.equals("Whiskey")){
            defaultImage = context.getResources().getIdentifier("whiskey_listing", "drawable", context.getPackageName());

        }else if(drinkType.equals("White Wine")){
            defaultImage = context.getResources().getIdentifier("white_wine_item", "drawable", context.getPackageName());

        }else if(drinkType.equals("CraftBeer")){
            defaultImage = context.getResources().getIdentifier("craft_beer_bottle", "drawable", context.getPackageName());

        }else if(drinkType.equals("Tea")){
            defaultImage = context.getResources().getIdentifier("tea_new", "drawable", context.getPackageName());

        } else if(drinkType.equals("Water")){
            defaultImage = context.getResources().getIdentifier("water_bottle", "drawable", context.getPackageName());

        }
        else if(drinkType.equals("Americano")){
            defaultImage = context.getResources().getIdentifier("americano", "drawable", context.getPackageName());

        }
        else if(drinkType.equals("filter coffee")){

            //@todo add cotnent descriptions for images
            defaultImage = context.getResources().getIdentifier("filter_coffee", "drawable", context.getPackageName());

        }
        else if(drinkType.equals("Instant coffee")){
            defaultImage = context.getResources().getIdentifier("instant_coffee", "drawable", context.getPackageName());

        }else{
            //need to add dfeault drink in here
            defaultImage = context.getResources().getIdentifier("wine_bottle", "drawable", context.getPackageName());

        }
        return defaultImage;
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
}
