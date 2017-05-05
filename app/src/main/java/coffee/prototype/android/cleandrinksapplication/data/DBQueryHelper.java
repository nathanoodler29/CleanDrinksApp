package coffee.prototype.android.cleandrinksapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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


}
