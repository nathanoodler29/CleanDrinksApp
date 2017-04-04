package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * created by Noodle on 22/03/2017.
 */

public class SessionManager {

    Context context;

    private SharedPreferences preferences;

    public static String perferencesName = "userPref";

    public static int PRIVATE = 0;

    public static String email;

    public static String id;


    public Editor editor;


    public SessionManager(Context context) {
        //Creates the shared preferences file.
        this.context = context;
        preferences = context.getSharedPreferences(perferencesName, PRIVATE);

    }


    public String createSessionForUser(int userId, String userEmail) {

        editor = preferences.edit();

        editor.putInt(id, userId);

        editor.putString(email, userEmail);

        String size = String.valueOf(preferences.getAll().size());

        String user = userId + userEmail + "  " + size;

        int userID = preferences.getInt("id", 0);


        editor.commit();

        return String.valueOf(userID);

    }

    public String getAll() {

        return String.valueOf(preferences.getAll());
    }

    public int getUserId() {
        int userID = preferences.getInt("id", 0);

        return userID;
    }


    public void deleteSession() {
        preferences.edit().clear().apply();
    }

    public String checkIfUserIsLoggedin() {
        String message = "";

        if (preferences.getAll().size() >= 1) {
            message = "User is  logged in";


        } else if (preferences.getAll().size() < 0) {

            message = "User isn't logged in";
        }

        return message;
    }


    public String getUserIDEmail() {


        return preferences.getString("email", null);

    }


}
