package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * created by Noodle on 04/04/2017.
 */

public class GoalContract {
    private GoalContract() {
    }

    /**
     * This class is used to retrieve column names, to ensure they are spelled correctly.
     * As such a columns are referenced in the schema.
     */
    public static final class GoalEntry implements BaseColumns {
        public final static String TABLE_NAME = "Goal";
        //References a user to maintain a user session.
        public final static String USER_FK_REF = "user_fk_goal";
        //Base columns is used to reference id in reycler view and each record in the table.
        public final static String GOAL_ID = BaseColumns._ID;
        public final static String COLUMN_Water_Target = "water_target";
        public final static String COLUMN_START_TIME = "start_time";
        public final static String COLUMN_END_TIME = "end_time";
        //Refers to yyy/mm/dd hh/mm
        public final static String COLUMN_DATE = "goal_date";


    }
}

