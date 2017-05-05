package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * created by Noodle on 12/03/2017.
 */

public class WeightContract {

    private WeightContract() {
    }

    public static final class WeightEntry implements BaseColumns {

        public final static String TABLE_NAME = "weight";

        //Links the user table to this table.
        public final static String USER_FK_REF = "user_fk";
        //weight column
        public final static String COLUMN_WEIGHT = "weight";
        //Height column
        public final static String COLUMN_HEIGHT = "height";




    }
}

