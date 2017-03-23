package coffee.prototype.android.cleandrinksapplication.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;


/**
 * Created by Noodle on 22/03/2017.
 */

public class SessionManager {

    Context context;

    SharedPreferences preferences;

    public static String perferencesName = "userPref";

    public static int PRIVATE =0;

    public static String email;

    private String userEmail;


    public Editor editor;

    public static String id;

    public SessionManager(Context context){
        //Creates the shared preferences file.
        this.context = context;
        preferences = context.getSharedPreferences(perferencesName,PRIVATE);
        editor = preferences.edit();

    }


    public String createSessionForUser(int userId, String userEmail){




        editor = preferences.edit();

        editor.putInt(id,userId);

        editor.putString(email,userEmail);

       String size = String.valueOf(preferences.getAll().size());

        String user= userId+userEmail+"size  "+size;

        editor.apply();

        return user;


    }

    public void deleteSession(){
        preferences.edit().clear().apply();
    }

    public String checkIfUserIsLoggedin(){
        String message = "";

        if (preferences.getAll().size()>=1){
            message = "User is  logged in";


        }else if(preferences.getAll().size()<0){

            message="User isn't logged in";
        }

        return message;
    }



}
