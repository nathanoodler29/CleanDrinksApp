package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * created by Nathan on 11/04/2017.
 */

public class DrinksCategoryDrinkQuanitiy {
    /**
     * This class is used to retrieve column names, to ensure they are spelled correctly.
     * As such a columns are referenced in the schema.
     */
    private DrinksCategoryDrinkQuanitiy() {
    }

    public static final class DrinksQuantityEntry {
        public final static String TABLE_NAME = "drinks_category_quanitiy";
        //Base columns is used to reference id in reycler view and each record in the table.
        public final static String quanitiy_ID = BaseColumns._ID;
        //Relates to the foreign key reference from the drinks category drink id.
        public final static String drink_id_fk = "drinks_id_fk";
        //Refers to the users id from the user table, but is used as a  foreign key reference
        public final static String user_id_fk = "user_id_fk";
        //Refers to the number of drinks.
        public final static String quantity_of_drink = "quantity";
        //Refers to the name of the day
        public final static String DATE_NAME = "day_name";
        //Refers to the date, in yyyy/mm/dd hh:mm
        public final static String DATE = "date";


    }
}
