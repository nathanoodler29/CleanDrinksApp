package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * Created by Noodle on 21/04/2017.
 */

public class AchievementContract {
    private AchievementContract() {
    }

    public static final class AchievementEntry implements BaseColumns {

        public final static String TABLE_NAME = "ACHIEVEMENT";

        //must have ID otherwise, wont; be able to update or delete an existing measurment.

        public final static String ACHIEVEMENT_ID = BaseColumns._ID;
        //i think here we pass in the pk of the users table, so we can trace this through that.
        public final static String COLUMN_ACHIEVEMENT_NAME = "achievement_name";
        public final static String COLUMN_ACHIEVEMENT_DESCRIPTION = "achievement_description";
        public final static String COLUMN_ACHIEVEMENT_IMAGE = "achievement_image";



    }
}
