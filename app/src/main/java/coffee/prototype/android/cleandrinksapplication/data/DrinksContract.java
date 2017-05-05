package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * created by Nathan on 11/04/2017.
 */

public class DrinksContract {
    /**
     * This class is used to retrieve column names, to ensure they are spelled correctly.
     * As such a columns are referenced in the schema.
     */
    private DrinksContract() {
    }

    public static final class DrinksCategoryEntry implements BaseColumns {
        public final static String TABLE_NAME = "drinks_category";
        //Base columns is used to reference id in recycler view and each record in the table.
        public final static String DRINKS_ID = BaseColumns._ID;
        //The name of the drink.
        public final static String DRINK_NAME = "drinks_name";
        //The type of drink i.e Coffee
        public final static String DRINK_TYPE = "drinks_type";
        //The volume of the drink i.e 150ml
        public final static String DRINKS_VOLUME = "drinks_volume";
        // the amount of drink i.e caffine or unit value.
        public final static String DRINKS_AMOUNT = "drinks_amount";
        //Refers to the drinks image icon
        public final static String DRINKS_IMAGE = "drinks_image";
    }
}
