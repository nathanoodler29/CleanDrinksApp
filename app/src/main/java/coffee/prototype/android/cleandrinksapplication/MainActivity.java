package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import coffee.prototype.android.cleandrinksapplication.data.SessionManager;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;
import coffee.prototype.android.cleandrinksapplication.data.WeightContract;


//Deprecation is related to facebook single sign on not the code itself.
@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    //References the facebook login button for SSO.
    private LoginButton loginButton;


    //handles facebook fragment
    CallbackManager callbackManager;
    //references the database reference
    private UsersDBHelper usersDBHelper;

    private String emailAddressField;
    private String passwordFiled;


    private EditText passwordInput;
    private EditText emailAddressInput;
    //Relates to number of attempts while logging in.
    private TextView numOfAttempts;
    private int attempts = 3;

    ActivityHelper activityHelper = new ActivityHelper();


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


        numOfAttempts = (TextView) findViewById(R.id.attempts);

        //creates the facebook login button
        createFacebookLoginButton();
        //Allows the single sign on to occur for a user.
        facebookLoginHandler();

        validateEmailField();
        validatePasswordField();


    }

    /**
     * Creates a menu
     *
     * @param menu Needs a menu resource to populate the menu
     * @return True to display the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.current_menu, menu);
        return true;
    }

    /**
     * Sets the events related to each menu
     *
     * @param item relates to the option in the menu
     * @return The menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (item.getItemId() == R.id.action_logout) {
            //deletes the current users session
            sessionManager.deleteSession();
            //toast message displayed.
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

    /**
     * Handles Facebook fragment
     *
     * @param requestCode relating to the request from the facebook prompt
     * @param resultCode  whether the user has logged in correct or not
     * @param data        releating to the facebook data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * If a user clicks the sign up button then they are re-directed to the sign up activity.
     *
     * @param view Relates to the current activity view.
     */
    public void openSignUpActivity(View view) {
        //makes sign up button vibrate
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        //creates a user session
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.checkIfUserIsLoggedin().equals("User is  logged in")) {
            createToastWithText("You're already signed in, please sign out.");
        } else {
            Intent changeToSignUpPage = new Intent(this, Sign_Up_Activity.class);
            //Switches the activity to sign up.
            startActivity(changeToSignUpPage);
        }


    }

    /**
     * This method controls whether a user has successfully logged in with username + password.
     * If a user has supplied the correct weight then goes to drinks cat page, otherwise goes to weight page.
     *
     * @param view Reflects the current activity view.
     */
    public void openWeightActivityAfterCorrectSignOn(View view) {
        //makes  button vibrate
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        //Checks to see if the user credentials are correct with user name and password
        boolean validateIfCreated = validateIfUsersAccountCredentialsAreCorrect();
        //checks if the user has added a weight.
        boolean weight = checkIfUserHasSuppliedWeightAndHeight();
        //Creates an instance of the session
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        //If the user credentials are correct and weight is present
        if (validateIfCreated && weight) {
            //create user session
            createUserSession();
            checkIfUserHasSuppliedWeightAndHeight();
            //change to drinks cat
            Intent changeToDrinksCat = new Intent(this, DrinkCategory.class);
            //go to the activity
            startActivity(changeToDrinksCat);
            finish();
            //If the weight hasn't been added then send user the weight and height activity
        } else if (validateIfCreated && !weight) {
            //Go to the weight and height activity.
            Intent changeToAddWeight = new Intent(this, Weight_and_Height_Activity.class);
            //start the activity
            startActivity(changeToAddWeight);
            //if the account doesn't exist
        } else if (!validateIfCreated) {
            //decrease total num of attempts
            attempts--;
            //Sets the number in the attempts edit text
            numOfAttempts.setText("Attempts left: " + Integer.toString(attempts));
            //if attempts is 0 then the forgotten password actiivty is then displayed
            if (attempts == 0) {
                //changes the forgotten password activity.
                Intent changeToForgottenPage = new Intent(this, ForgottenPassword.class);
                //Switches the activity to forgotten password activity
                startActivity(changeToForgottenPage);
                //resets attempts to 3
                resetAttempts();

            }

        }


    }

    /**
     * Resets the attempts number to 3.
     */
    public void resetAttempts() {
        attempts = 3;

    }

    /**
     * Validates if the users credentials are correct
     *
     * @return
     */
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
                //checks if the input is empty.
                if (userInput.isEmpty()) {
                    //error is thrown
                    emailAddressInput.setError("Please don't leave blank");
                    //checks if any special chars are used.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //error thrown
                    emailAddressInput.setError("Special characters can't be used");

//               @Todo reference  Regex from Google regex checker

                }
                //checks if a valid email format is correct
                if (userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    checkNumberEmail += 1;
                    String validUserEmail = userInput.trim();
                    //toast displays valid email.
                    createToastWithText("Valid Email");
                    setEmailAddressField(validUserEmail);

                    //if not valid then error is thrown.
                } else if (!userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    emailAddressInput.setError("Please include a valid email");
                }
            }
        });
        //return the email.
        return getEmailAddressField();

    }

    /**
     * Checks whether the password added by a user is valid.
     *
     * @return
     */
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
                //checks if edit text is empty.
                if (userInput.isEmpty()) {
                    //error thrown
                    passwordInput.setError("Please don't leave blank ");
                    //checks if special characters are used.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //error is thrown
                    passwordInput.setError("Special characters can't be used");
                    //checks if length is more than or equal to 4
                } else if (userInput.length() <= 3) {
                    //Otherwise error is thrown
                    passwordInput.setError("Password must be longer than four characters");
                    //Checks if a number is added
                } else if (!userInput.matches(".*\\d+.*")) {
                    //must contain a number
                    passwordInput.setError("Password Needs to contain 1 number");
                    //Checks if a number is part of the password.
                } else if (userInput.matches(".*\\d+.*")) {
                    createToastWithText("Valid Password.");
                    //removes any spaces.
                    String validPassword = userInput.trim();
                    setPasswordFiled(validPassword);

                }


            }
        });
        //Gets the password.
        return getPasswordField();
    }

    /**
     * Checks whether the users email and password are correct and if they are still logged in
     *
     * @return Whether the user is logged in
     */
    private boolean validateIfUsersAccountCredentialsAreCorrect() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        boolean useLoggedIn = false;
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //SQL query uses the email and password passed to check if it matches the database equivalents
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                UsersEntry.TABLE_NAME + " WHERE " + UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + getEmailAddressField() + "'" + " AND " + "" +
                UsersEntry.COLUMN_USER_PASSWORD + "=" + "'" + getPasswordField() + "'", null);

        //This means that an entry is present.
        if (cursor.getCount() == 1) {
            createToastWithText("Account valid");
            useLoggedIn = true;

        } else if (cursor.getCount() == 0) {
            createToastWithText("No such account");
            useLoggedIn = false;


        }

        try {
            //Prints whole query output, used for debugging if results are incorrect or sql query isn't right.
            Log.v("Cursor if user exists", DatabaseUtils.dumpCursorToString(cursor));

        } finally {
            //closes the cursor query.
            cursor.close();
        }
        //Returns whether the user is logged in i.e false means user doesn't have an account, while true then the user does.
        return useLoggedIn;
    }

    private int getUserId() {
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        int num = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + UsersEntry._ID + " " + "FROM " +
                UsersEntry.TABLE_NAME + " WHERE " + UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + getEmailAddressField() + "'" + " AND " + "" +
                UsersEntry.COLUMN_USER_PASSWORD + "=" + "'" + getPasswordField() + "'", null);
        //Bug: In program, before this logic from this source http://stackoverflow.com/questions/4396604/how-to-solve-cursorindexoutofboundsexception
        //my code would return -1 index, because of the check, the code now doesn't throw the exception because it's handed.
        // move to first, when no intial insertion of item caused the program to fail, this is why moveatoNEXT IS USED, if a user has been added.
        if (cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                num = Integer.parseInt(cursor.getString(cursor.getColumnIndex(UsersEntry._ID)));

            }

            //closes the cursor @todo recently added cusror close
            cursor.close();
        }


        db.close();
        return num;


    }

    /**
     * Creates a  users session
     */
    private void createUserSession() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //SQL query uses the email and password passed to check if it matches the database equivalents
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                UsersEntry.TABLE_NAME + " WHERE " + UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + getEmailAddressField() + "'" + " AND " + "" +
                UsersEntry.COLUMN_USER_PASSWORD + "=" + "'" + getPasswordField() + "'", null);
        //If the data returned from the query is 1 then the account exists.
        cursor.moveToFirst();
        //Create a session by storing the email and password of the user
        sessionManager.createSessionForUser(getUserId(), getEmailAddressField());
        //closes the database cursor.
        cursor.close();

    }

    /**
     * Checks if the user has already added weight or not.
     *
     * @return Whether the user has added weight or not.
     */
    private boolean checkIfUserHasSuppliedWeightAndHeight() {
        boolean userFilledInWeight = false;
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //checks to see if the user id exists in weight table to show that weight has been added.
        Cursor Weightcursor = db.rawQuery("SELECT * FROM " +
                WeightContract.WeightEntry.TABLE_NAME + " WHERE "
                + WeightContract.WeightEntry.USER_FK_REF + " = " + "'" + getUserId() + "'", null);

        Weightcursor.moveToFirst();

//        Log.v("check weight", DatabaseUtils.dumpCursorToString(Weightcursor));
        if (Weightcursor.getCount() == 1) {
            userFilledInWeight = true;
        } else if (Weightcursor.getCount() == 0) {
            userFilledInWeight = false;
        }

        //closes the cursor.
        Weightcursor.close();
        //Returns if the user has added weight.
        return userFilledInWeight;
    }


}





