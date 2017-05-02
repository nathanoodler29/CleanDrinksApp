package coffee.prototype.android.cleandrinksapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * created by noodle on 04/04/2017.
 */

public class DBQueryHelper {

    public void insertIntoDBImage(Context context, String drinkName, String drinkType, double drinkVolume, double drinkCaffine, int imagePath) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();



        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_NAME, drinkName);

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINK_TYPE, drinkType);

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_VOLUME, drinkVolume);

        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_IMAGE, imagePath);


        contentValues.put(DrinksContract.DrinksCategoryEntry.DRINKS_AMOUNT, drinkCaffine);


        db.insert(DrinksContract.DrinksCategoryEntry.TABLE_NAME, null, contentValues);



        db.close();


    }



}
