package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/*
 * created by Nathan on 21/04/2017.
 */

/**
 * This class is used to retrieve column names, to ensure they are spelled correctly.
 * As such a columns are referenced in the schema.
 */
public class AchievementContract {
    private AchievementContract() {
    }

    public static final class AchievementEntry implements BaseColumns {

        public final static String TABLE_NAME = "ACHIEVEMENT";

        //Base columns is used to reference id in reycler view and each record in the table.
        public final static String ACHIEVEMENT_ID = BaseColumns._ID;
        //name of the achievement
        public final static String COLUMN_ACHIEVEMENT_NAME = "achievement_name";
        //Achievement description
        public final static String COLUMN_ACHIEVEMENT_DESCRIPTION = "achievement_description";
        //Achievement image icon
        public final static String COLUMN_ACHIEVEMENT_IMAGE = "achievement_image";


    }
}
