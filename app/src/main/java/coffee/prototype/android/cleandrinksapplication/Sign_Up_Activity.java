package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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

import coffee.prototype.android.cleandrinksapplication.data.SessionManager;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;

import static coffee.prototype.android.cleandrinksapplication.R.id.password_address_sign_up_field;
import static coffee.prototype.android.cleandrinksapplication.R.id.passwordstrength;

public class Sign_Up_Activity extends AppCompatActivity {
    private EditText signUpEmailAddressInput;
    private EditText signUpPasswordInput;
    private int checkNumberEmail;
    private int checkNumberPassword;


    private String userEmail;
    private String userPassword;

    private UsersDBHelper usersDBHelper;

    private TextView passwordStrengthIndicator;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);
        signUpEmailAddressInput = (EditText) findViewById(R.id.email_address_sign_up_field);
        signUpPasswordInput = (EditText) findViewById(password_address_sign_up_field);
        passwordStrengthIndicator = (TextView) findViewById(passwordstrength);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //performs validation for the email field.
        validateEmailField();
        //performs validation for the password field.
        validatePasswordField();

        usersDBHelper = new UsersDBHelper(this);


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

        if (item.getItemId() == R.id.action_addgoal) {
            //add goal intent here.
            createToastWithText("Clicked add Goal ");

        }
        return super.onOptionsItemSelected(item);
    }


    //@TODO relate to the user object
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    /**
     * Performs validation on the email field, stopping use of illegal special chars and incorrect emails.
     * Uses regex to ensure the text pass matches an email format and stops empty strings being accepted.
     *
     * @return checkNumberEmail int, which 0 = failed validation 1 = passed validation.
     */
    public int validateEmailField() {
        //Creates a text watcher, to track a users input.
        signUpEmailAddressInput.addTextChangedListener(new TextWatcher() {
            //This is before the user is able to use the text
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //This now monitors what the user has typed, after on changed.
            @Override
            public void afterTextChanged(Editable s) {
                //Converts the input from a user
                String userInput = signUpEmailAddressInput.getText().toString();
                //This number represents failed validaiton, the field hasn't been checked yet.
                checkNumberEmail = 0;
                //if the email field has no text
                if (userInput.isEmpty()) {
                    //Error is created asking a user to fill out the field.
                    signUpEmailAddressInput.setError("Please don't leave blank");
                    //Protects against special chars, so SQL injection can't occur.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //Sets error stating symbols can't be used.
                    signUpEmailAddressInput.setError("Special characters can't be used");

                    //Regular expression used from Google's Regex Checker:
                    // Reference to email checker: https://chrome.google.com/webstore/detail/regexp-tester/fekbbmalpajhfifodaakkfeodkpigjbk?hl=en
                    // Regular expression for validating an email, is in the correct form.
                } else if (userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    //If validation is passed then checkNumberEmail is 1, suggesting a pass.
                    checkNumberEmail += 1;
                    //Removes any extra spaces from string.
                    String validUserEmail = userInput.trim();
                    //Notifies a user that email is valid.
                    createToastWithText("Valid Email");
                    //Sets the string to the valid email.
                    setUserEmail(validUserEmail);
                    //If the user's text doesn't match the regex for email, then error is thrown.
                } else if (!userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    signUpEmailAddressInput.setError("Please include a valid email");
                }
            }
        });
        //Returns the number of the email field validation , 0 = fail, 1 = passed validation.
        return checkNumberEmail;

    }

    /**
     * Performs validation on the password field, stopping use of illegal special chars
     * Requires a number in password for security.
     * Uses regex to ensure the text passed matches includes a number, and stops empty strings from being passed.
     *
     * @return checkNumberPassword int, which 0 = failed validation 1 = passed validation.
     */
    public int validatePasswordField() {

        signUpPasswordInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //Text watcher, monitors what text is typed by the user
            //ref of password strength code https://www.tutorialspoint.com/android/android_login_screen.htm
            @Override
            public void afterTextChanged(Editable s) {
                //Converts the input from a user
                String userInput = signUpPasswordInput.getText().toString();
                checkNumberPassword = 0;
                //if input field contains now text, throw error.
                if (userInput.isEmpty()) {
                    //creates an error message asking not to leave blank.
                    signUpPasswordInput.setError("Please don't leave blank ");
                    checkNumberPassword = 0;

                    //Checks that any special chars are used, if so then error is thrown.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //Error thrown about special chars.
                    signUpPasswordInput.setError("Special characters can't be used");
                    //Error is thrown if the password is 4 or less chars.
                } else if (userInput.length() <= 3) {
                    //Error message is thrown
                    signUpPasswordInput.setError("Password must be longer than four characters");
                    checkNumberPassword = 0;
                    passwordStrengthIndicator.setText("Password Strength: Weak");
                    passwordStrengthIndicator.setBackgroundColor(Color.RED);


                    //Regex used from http://javarevisited.blogspot.co.uk/2012/10/regular-expression-example-in-java-to-check-String-number.html
                    //checks that at least a number is present in the string
                } else if (!userInput.matches(".*\\d+.*")) {
                    //Error is thrown if the input of at least one number is added.
                    signUpPasswordInput.setError("Password Needs to contain 1 number");
                    passwordStrengthIndicator.setText("Password Strength: Medium");
                    passwordStrengthIndicator.setBackgroundColor(Color.YELLOW);
                    checkNumberPassword = 0;


                    //Regex used from http://javarevisited.blogspot.co.uk/2012/10/regular-expression-example-in-java-to-check-String-number.html
                } else if (userInput.matches(".*\\d+.*")) {
                    //Toast is displayed to show a ps password.
                    createToastWithText("Valid Password");
                    passwordStrengthIndicator.setText("Password Strength: Strong");
                    passwordStrengthIndicator.setBackgroundColor(Color.GREEN);
                    //adds 1 to the number password.
                    checkNumberPassword += 1;
                    //removes any spaces in the password.
                    String validPassword = userInput.trim();
                    //sets the valid password as a password.
                    setUserPassword(validPassword);

                }


            }
        });
        //Returns the number of the email field validation , 0 = fail, 1 = passed validation.
        return checkNumberPassword;
    }


    /**
     * Intialses a Toast object and then displays text.
     *
     * @param toastText Uses the text passed, and is displayed by the toast.
     */
    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        //Toast only is displayed for a small peroid of time
        int duration = Toast.LENGTH_LONG;
        //Creates the toast with the text passed.
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    /**
     * @param view relates to the current activity view.
     */
    public void openWeightActivityAfterCorrectSignUp(View view) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (validateEmailField() == 1 && validatePasswordField() == 1) {
            Log.d("After CREATED", "CREATED");
            boolean userExists = validateIfUserExists();
            if (userExists == false) {
                insertUser(getUserEmail(), getUserPassword());
                checkIfDataHasBeenAddedToDb();

                Intent changeToWeightPage = new Intent(this, Weight_and_Height_Activity.class);
                startActivity(changeToWeightPage);
                finish();

            } else if (userExists) {
                Log.d("User Exists", "User is already written in DB");

            }

        } else if (checkNumberEmail == 0 | checkNumberPassword == 0) {
            //If both methods return 0, this means that they haven't passed Validation for the fields.
            createToastWithText("Please ensure email or password fields aren't blank.");

        }

    }


    /**
     * Retrieves the email address, the user enters then checks to see if already exists.
     * If the address already exists, the activity is finished then the user is sent to the login screen.
     *
     * @return boolean userExists: If true, user is already in db otherwise not.
     */
    private boolean validateIfUserExists() {
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean userExists = false;
        //Performs the sql query on the email a user has passed, to check if present in the DB.
        Cursor cursor = db.rawQuery("SELECT * FROM " + UsersEntry.TABLE_NAME + " WHERE " + UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + getUserEmail() + "'", null);
        //If a column number exists related to the query, then the user is sent back to the login screen.
        cursor.moveToFirst();


        if (cursor.getCount() == 1) {
            createToastWithText("Account with this email already exists");
            userExists = true;
            //Creates toast for the user to understand they are being took back to the login screen.
            createToastWithText("Please login to your account");
            //Stops executing the current activity.
            finish();
        } else if (cursor.getCount() == 0) {
            //This means the email the user has entered doesn't exist.


            userExists = false;


        }

        try {
            //Used for debugging, shows all the output related to the query used.
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));

        } finally {
            //Closes the query object.
            cursor.close();
        }
        //Boolean result of whether the user exists.
        return userExists;
    }

    /**
     * Inserts a user into the database.
     *
     * @param email    The user's email address from the email edit text.
     * @param password The user's password from the email edit text.
     */
    private void insertUser(String email, String password) {
        //Sets the database to write mode.
        SQLiteDatabase db = usersDBHelper.getWritableDatabase();
        //Content value key pair object.
        ContentValues contentValues = new ContentValues();
        //Places the email input, like email : "user_email"
        contentValues.put(UsersEntry.COLUMN_USER_EMAIL, email);
        //Places the email input, like password : "user_password"
        contentValues.put(UsersEntry.COLUMN_USER_PASSWORD, password);
        //Inserts the email and password into the database.
        long newRowId = db.insert(UsersEntry.TABLE_NAME, null, contentValues);
        //Log cat used to show that a database insertion is occuring.

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        //Creates the user session
        String session = sessionManager.createSessionForUser(getUserId(), email);
        //Logs that the sign up activity has added a user
        Log.v("sign up activity", "new row id" + newRowId);
        //closes the db
        db.close();
    }

    /**
     * Used for debugging for whether the user has been added to the user table.
     */
    private void checkIfDataHasBeenAddedToDb() {

        SQLiteDatabase db = usersDBHelper.getReadableDatabase();
        //checks that values are populated in db.
        Cursor cursor = db.rawQuery("SELECT * FROM " + UsersEntry.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //@todo may cause error clsoed cusor
        cursor.close();
    }


    /**
     * Gets the users id related to the session
     *
     * @return The user id of the current logge din user
     */
    private int getUserId() {
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        num = 0;

        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Get the users id based on the email.
        Cursor cursor = db.rawQuery("SELECT " + UsersEntry._ID + " " + "FROM " +
                UsersEntry.TABLE_NAME + " WHERE " + UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + getUserEmail() + "'" + " AND " + "" +
                UsersEntry.COLUMN_USER_PASSWORD + "=" + "'" + getUserPassword() + "'", null);
        //if cursor is 1 or more then get the user id.
        if (cursor.getCount() >= 1) {
            while (cursor.moveToNext()) {
                //get the user id from the user id column in user table.
                setNum(Integer.parseInt(cursor.getString(cursor.getColumnIndex(UsersEntry._ID))));

//                    Log.v("Cursor ObjectID", DatabaseUtils.dumpCursorToString(cursor));

            }
        }


        db.close();

        return num;

    }
}


