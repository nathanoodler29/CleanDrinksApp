package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * created by Noodle on 04/04/2017.
 */

public class GoalContract {
    private GoalContract() {
    }

    public static final class GoalEntry implements BaseColumns {

        public final static String TABLE_NAME = "Goal";

        //must have ID otherwise, wont; be able to update or delete an existing measurment.
        public final static String USER_FK_REF = "user_fk_goal";

        public final static String GOAL_ID = BaseColumns._ID;
        //i think here we pass in the pk of the users table, so we can trace this through that.
        public final static String COLUMN_Water_Target = "water_target";
        public final static String COLUMN_START_TIME = "start_time";
        public final static String COLUMN_END_TIME = "end_time";

        public final static String COLUMN_DATE = "goal_date";



    }
}

