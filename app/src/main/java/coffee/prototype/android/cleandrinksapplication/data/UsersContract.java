package coffee.prototype.android.cleandrinksapplication.data;

import android.provider.BaseColumns;

/**
 * Created by Noodle on 10/03/2017.
 */

public class UsersContract {

    private UsersContract() {}


    public static final class UsersEntry implements BaseColumns{
        public final static String TABLE_NAME = "users";

        public final static String _ID = BaseColumns._ID;
        public final  static  String COLUMN_USER_EMAIL = "email";
        public final  static  String COLUMN_USER_PASSWORD = "password";

    }

}
