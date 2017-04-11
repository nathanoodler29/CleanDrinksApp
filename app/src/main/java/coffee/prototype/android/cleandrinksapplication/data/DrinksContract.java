package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * Created by Noodle on 11/04/2017.
 */

public class DrinksContract {

    private DrinksContract() {
    }

    public static final class DrinksCategoryEntry implements BaseColumns {
        public final static String TABLE_NAME = "drinks_category";
        public final static String DRINKS_ID = BaseColumns._ID;
        public final static String DRINK_NAME = "drinks_name";
        public final static String DRINK_TYPE = "drinks_type";
        public final static String DRINKS_VOLUME = "drinks_volume";
        public final static String DRINKS_AMOUNT = "drinks_amount";

    }
}
