package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/*
 * created by noodle on 05/04/2017.
 */

/**
 * This class relates to the column names in the table.
 * To reduce the chances of typing the wrong table name in an sql query.
 */
public class GoalProgressContract {
    private GoalProgressContract() {
    }

    public static final class GoalProgressEntry implements BaseColumns {

        public final static String TABLE_NAME = "goal_progress";
        //Relates to the autoincrement ID to re-inforce referntial integgrity.
        public final static String Progress_Goal_ID = BaseColumns._ID;
        //must have ID otherwise, wont; be able to update or delete an existing measurement.
        public final static String GOAL_FK_REF = "goal_fk";
        //Used to state whether a user has completed a goal.
        public final static String GOAL_COMPLETED = "goal_completed";
        //i think here we pass in the pk of the users table, so we can trace this through that.


    }
}
