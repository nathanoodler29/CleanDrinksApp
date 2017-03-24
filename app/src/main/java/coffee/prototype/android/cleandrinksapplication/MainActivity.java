package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import coffee.prototype.android.cleandrinksapplication.data.SessionManager;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;


@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    //References the facebook login button for SSO.
    private LoginButton loginButton;

    //handles facebook fragment
    CallbackManager callbackManager;


    private UsersDBHelper usersDBHelper;

    private String  emailAddressField;
    private String passwordFiled;


    private EditText emailAddressInput;
    private EditText passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);




        emailAddressInput = (EditText) findViewById(R.id.email_address_field);

        passwordInput = (EditText) findViewById(R.id.password_field);

        usersDBHelper = new UsersDBHelper(this);

        //creates the facebook login button
        createFacebookLoginButton();
        //Allows the single sign on to occur for a user.
        facebookLoginHandler();
        validateEmailField();
        validatePasswordField();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.current_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if(item.getItemId()==R.id.action_logout){
            sessionManager.deleteSession();
            createToastWithText("Successfully logged out");
        }
        return super.onOptionsItemSelected(item);
    }

    public String getEmailAddressField() {
        return emailAddressField;
    }

    public void setEmailAddressField(String emailAddressField) {
        this.emailAddressField = emailAddressField;
    }

    public String getPasswordField() {
        return passwordFiled;
    }

    public void setPasswordFiled(String passwordFiled) {
        this.passwordFiled = passwordFiled;
    }


    /**
     * Initialises a Toast object and then displays text.
     *
     * @param toastText Uses the text passed, and is displayed by the toast.
     */
    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }


    public void createFacebookLoginButton() {

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);


    }

    /**
     * This method handles what event should happen if a user:
     * Correctly logs in, fails to login or cancels the login.
     */
    public void facebookLoginHandler() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                //States to the user they've correctly logged in.
                createToastWithText("Facebook login successful");


            }

            @Override
            public void onCancel() {
                //Finishes the current process.
//                finish();
                createToastWithText("Facebook login canceled ");

            }

            @Override
            public void onError(FacebookException error) {
                //States to the user they've incorrectly attempted to log in.
                createToastWithText("Facebook login error" + error.getMessage());

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * If a user clicks the sign up button then they are re-directed to the sign up activity.
     * @param view Relates to the current activity view.
     */
    public void openSignUpActivity(View view) {
        //Creates an intent related to the sign up activity.
        SessionManager sessionManager = new SessionManager(getApplicationContext());
//
        if(sessionManager.checkIfUserIsLoggedin().equals("User is  logged in")){
            createToastWithText("You're already signed in, please sign out.");
        }else{

            Intent changeToSignUpPage = new Intent(this, Sign_Up_Activity.class);
            //Switches the activity to sign up.
            startActivity(changeToSignUpPage);
        }


    }

    /**
     * This method controls whether a user has successfully logged in with username + password.
     * @param view Reflects the current activity view.
     */
    public void openWeightActivityAfterCorrectSignOn(View view) {


     boolean validateIfCreated =  validateIfUsersAccountCredentialsAreCorrect();

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        //If the user credentials are correct then current activity finishes
        if (validateIfCreated ) {
            //To prevent the user going back to the login screen.
            //Goes to the next activity for adding weight and height.
            createUserSession();
            Intent changeToWeightPage = new Intent(this, Weight_and_Height_Activity.class);
            startActivity(changeToWeightPage);


        } else{
            // Is displayed if a user's account can't be found.
            createToastWithText("Unable to find Account.");
        }

    }


    public String validateEmailField() {

        emailAddressInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //Text watcher, monitors what text is typed by the user
            @Override
            public void afterTextChanged(Editable s) {
                //Converts the input from a user
                String userInput = emailAddressInput.getText().toString();
                int checkNumberEmail = 0;

                if (userInput.isEmpty()) {
                    emailAddressInput.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    emailAddressInput.setError("Special characters can't be used");

//                Regex from Google regex checker
                } else if (userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    checkNumberEmail += 1;
                    String validUserEmail = userInput.trim();
                    createToastWithText("Valid Email");
                    setEmailAddressField(validUserEmail);



                } else if (!userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    emailAddressInput.setError("Please include a valid email");
                }
            }
        });
        return getEmailAddressField();

    }

    public String validatePasswordField() {

        passwordInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //Text watcher, monitors what text is typed by the user

            @Override
            public void afterTextChanged(Editable s) {
                //Converts the input from a user
                String userInput = passwordInput.getText().toString();

                if (userInput.isEmpty()) {
                    passwordInput.setError("Please don't leave blank ");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    passwordInput.setError("Special characters can't be used");

                } else if (userInput.length() <= 3) {
                    passwordInput.setError("Password must be longer than four characters");
                } else if (!userInput.matches(".*\\d+.*")) {
                    passwordInput.setError("Password Needs to contain 1 number");
                } else if (userInput.matches(".*\\d+.*")) {
                    createToastWithText("Valid Password.");
                    String validPassword = userInput.trim();
                    setPasswordFiled(validPassword);

                }


            }
        });

        return getPasswordField();
    }


    private boolean validateIfUsersAccountCredentialsAreCorrect(){
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        boolean useLoggedIn=false;
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //SQL query uses the email and password passed to check if it matches the database equivalents
        Cursor cursor = db.rawQuery("SELECT * FROM "+
                UsersEntry.TABLE_NAME+" WHERE " +UsersEntry.COLUMN_USER_EMAIL+"="+"'"+getEmailAddressField()+"'"+" AND "+"" +
                UsersEntry.COLUMN_USER_PASSWORD+"="+"'"+getPasswordField()+"'",null);
        //If the data returned from the query is 1 then the account exists.
//            cursor.moveToFirst();
        //Get the user ID

        if(cursor.getCount()==1){
            createToastWithText("Account valid");
            useLoggedIn=true;
////            sessionManager.createSessionForUser(1,getEmailAddressField());
//            createToastWithText(sessionManager.checkIfUserIsLoggedin());
        //This means the user hasn't got an account.
        }else if(cursor.getCount()==0){
            createToastWithText("No such account");
            useLoggedIn=false;
        }

        try{
            //Prints whole query output, used for debugging if results are incorrect or sql query isn't right.
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));

        }finally {
            //closes the cursor query.
            cursor.close();
        }
        //Returns whether the user is logged in i.e false means user doesn't have an account, while true then the user does.
//        getUserId();
        return useLoggedIn;
    }

    private int getUserId(){
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT "+UsersEntry._ID+" " +"FROM "+
                UsersEntry.TABLE_NAME+" WHERE " +UsersEntry.COLUMN_USER_EMAIL+"="+"'"+getEmailAddressField()+"'"+" AND "+"" +
                UsersEntry.COLUMN_USER_PASSWORD+"="+"'"+getPasswordField()+"'",null);

    cursor.moveToFirst();

       int num = Integer.parseInt(cursor.getString(cursor.getColumnIndex(UsersEntry._ID)));

        createToastWithText("User ID" +num);
        Log.v("Cursor ObjectID", DatabaseUtils.dumpCursorToString(cursor));


        db.close();
        return num;


    }

    private void createUserSession(){
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //SQL query uses the email and password passed to check if it matches the database equivalents
        Cursor cursor = db.rawQuery("SELECT * FROM "+
                UsersEntry.TABLE_NAME+" WHERE " +UsersEntry.COLUMN_USER_EMAIL+"="+"'"+getEmailAddressField()+"'"+" AND "+"" +
                UsersEntry.COLUMN_USER_PASSWORD+"="+"'"+getPasswordField()+"'",null);
        //If the data returned from the query is 1 then the account exists.
            cursor.moveToFirst();
        //Get the user ID

            createToastWithText("session manager from create sesh method"+            sessionManager.createSessionForUser(getUserId(),getEmailAddressField()));

        cursor.close();

    }


}





