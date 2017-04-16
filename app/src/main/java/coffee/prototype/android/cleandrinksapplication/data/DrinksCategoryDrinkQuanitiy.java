package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * Created by Noodle on 11/04/2017.
 */

public class DrinksCategoryDrinkQuanitiy {

    private DrinksCategoryDrinkQuanitiy() {
    }

    public static final class DrinksQuantityEntry  {
        public final static String TABLE_NAME = "drinks_category_quanitiy";
        public final static String quanitiy_ID = BaseColumns._ID;
        public final static String drink_id_fk = "drinks_id_fk";
        //This is so a query, can show exactly how much a user has been drinking  for a recipt ivew.
        public final static String user_id_fk = "user_id_fk";
        public final static String quantity_of_drink = "quantity";

        public final static String DATE = "date";



    }
}
