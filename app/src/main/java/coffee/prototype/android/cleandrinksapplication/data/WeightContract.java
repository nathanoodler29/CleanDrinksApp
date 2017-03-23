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

        //must have ID otherwise, wont; be able to update or delete an existing measurment.
        public final static String USER_FK_REF = "user_fk";

        //i think here we pass in the pk of the users table, so we can trace this through that.
        public final static String COLUMN_WEIGHT = "weight";
        public final static String COLUMN_HEIGHT = "height";




    }
}

