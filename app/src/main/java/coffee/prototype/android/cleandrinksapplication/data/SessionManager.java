package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * created by Noodle on 22/03/2017.
 */

public class SessionManager {
    //Referes to context of applicaiton
    private Context context;
    //References shared preferences object
    private SharedPreferences preferences;
    //name of preferences file.
    public static String perferencesName = "userPref";

    public static int PRIVATE = 0;
    //refers to user email.
    public static String email;
    //Refers to user id
    public static String id;
    //Allows content provider to be modified.
    public Editor editor;

    /**
     * Used the following tutorial as a reference guide https://www.tutorialspoint.com/android/android_session_management.htm
     *
     * @param context Refers to the context of the activity.
     */
    public SessionManager(Context context) {
        //Creates the shared preferences file.
        this.context = context;
        //Creates the shared prefernces
        preferences = context.getSharedPreferences(perferencesName, PRIVATE);

    }

    /**
     * Creates a new session
     *
     * @param userId    Stores the user id
     * @param userEmail Stores the user email.
     * @return The email, id and size of the preferences.
     */
    public String createSessionForUser(int userId, String userEmail) {
        //Makes preference editable
        editor = preferences.edit();
        //stores the user id via key value pairs.
        editor.putInt(id, userId);
        //stores the user email key value pairs.
        editor.putString(email, userEmail);
        //Returns the amount of enteries in preferences
        String size = String.valueOf(preferences.getAll().size());
        //Used to check the user id, email and the total size of the preferences.
        String user = userId + userEmail + "  " + size;
        //used to get the value of the id.
        int userID = preferences.getInt("id", 0);
        //Saves the changes of storing the id and email.
        editor.commit();
        //returns the values from above.
        return String.valueOf(userID);

    }

    /**
     * Returns all values from preferences
     *
     * @return The value of the preferences.
     */
    public String getAll() {
        //Returns the value of all preferneces.
        return String.valueOf(preferences.getAll());
    }

    /**
     * Deletes all sessions
     */
    public void deleteSession() {
        preferences.edit().clear().apply();
    }

    /**
     * Checks if a user is logged in
     *
     * @return Whether a user is logged in or not.
     */
    public String checkIfUserIsLoggedin() {
        String message = "";
        //If the size of preferences is more than or equal to one, then session has been created.
        if (preferences.getAll().size() >= 1) {
            message = "User is  logged in";

            //If it's less than 0 then user isn't logged in.
        } else if (preferences.getAll().size() < 0) {

            message = "User isn't logged in";
        }

        return message;
    }

    /**
     * Gets the user's email address.
     *
     * @return The users email.
     */
    public String getUserIDEmail() {


        return preferences.getString("email", null);

    }


}
