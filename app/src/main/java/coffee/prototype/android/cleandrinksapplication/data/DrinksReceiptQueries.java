package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.Model.Coffee;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * created by Noodle on 05/05/2017.
 */

public class DrinksReceiptQueries {
    private ActivityHelper helper = new ActivityHelper();
    private ArrayList<Drink> drinksRecipt = new ArrayList<Drink>();
    private DBQueryHelper queryHelper = new DBQueryHelper();


    public String test(Context context, String drinksID){
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor newCursor = db.rawQuery("SELECT * FROM "+
                DrinksContract.DrinksCategoryEntry.TABLE_NAME +" WHERE " +
                ""+DrinksContract.DrinksCategoryEntry.DRINKS_ID +" = "+"'"+drinksID+"'",null);
        if (newCursor.moveToFirst()){
            String newCursorStr = newCursor.getString(0)+newCursor.getString(1)+newCursor.getString(2)+newCursor.getString(3)+newCursor.getString(4);


        }

        return newCursor.getString(1);
    }

    public void checkIfDrinksIDExists(Context context, String id) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String num = "1";
        Cursor cursor = db.rawQuery("SELECT * from " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + id + "'", null);

        if (cursor.moveToFirst()) {
            String drinkDetialsFromID = "pk " + cursor.getString(0) + "drink key" + cursor.getString(1) + "user key" + cursor.getString(2) + "drink quantity:" +
                    cursor.getString(3) + "drink date: " + cursor.getString(4);


        }
        cursor.close();
        db.close();

    }

    public void upateIDToHave0Value(Context context,String drinksID){

        UsersDBHelper dbHelper = new UsersDBHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + drinksID + "'"+
                " ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ", null);

        if (cursor.moveToFirst()){
            helper.createToastWithText("Deleted Drink");



            String sql =  "UPDATE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME + " SET "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " " + "= "
                    + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quantity_of_drink + " - 1" + " WHERE " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.quanitiy_ID + " =" + "'" + drinksID + "'"+
                    " ORDER BY " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.DATE + " ASC ";

            db.execSQL(sql);


            cursor.close();
            db.close();
        }


    }



    public void deleteDrinkFromTableBasedOnDrinkID(Context context, String drinksID){

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        //Makes the database readable.

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("DELETE FROM " + DrinksContract.DrinksCategoryEntry.TABLE_NAME+ " WHERE " +DrinksContract.DrinksCategoryEntry.DRINKS_ID+" " + " =" + "'" + drinksID + "'", null);

        if (cursor.moveToFirst()) {
          helper.createToastWithText("Deleted Drink");

        }


        cursor.close();
        db.close();


    }

    public ArrayList<Drink> populateDrinksReciptAdatper(Context context) {

        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM "+
                DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME +" WHERE " +
                ""+DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.user_id_fk+" = "+"'"+queryHelper.getUserId(getApplicationContext())+"'"

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

//        createToastWithText("size of recipt"+drinksRecipt.size());

        return drinksRecipt;

    }

    public int checkDrinksQuantiiyValuesExist(Context context) {
        UsersDBHelper dbHelper = new UsersDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DrinksCategoryDrinkQuanitiy.DrinksQuantityEntry.TABLE_NAME, null);

        String drinksQuant = "";

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {

                drinksQuant = "pk " + cursor.getString(0) + "drink key" + cursor.getString(1) + "user key" + cursor.getString(2) + "drink quantity:" +
                        cursor.getString(3) + "drink date: " + cursor.getString(4);
            }

        }

        cursor.close();


        return cursor.getCount();

    }


}
