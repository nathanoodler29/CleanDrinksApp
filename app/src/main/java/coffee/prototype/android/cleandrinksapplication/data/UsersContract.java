package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * created by Noodle on 10/03/2017.
 */

public class UsersContract {

    private UsersContract() {}


    public static final class UsersEntry implements BaseColumns{
        //Users table name
        public final static String TABLE_NAME = "users";
        //Helps id to be used in recycler view and create an autoincrement id.
        public final static String _ID = BaseColumns._ID;
        //Users email
        public final  static  String COLUMN_USER_EMAIL = "email";
        //user password.
        public final  static  String COLUMN_USER_PASSWORD = "password";

    }

}
