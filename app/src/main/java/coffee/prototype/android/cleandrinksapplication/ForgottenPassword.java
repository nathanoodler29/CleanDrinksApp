package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import coffee.prototype.android.cleandrinksapplication.data.UsersContract;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;

public class ForgottenPassword extends AppCompatActivity {

    private EditText existingEmailField;

    private EditText updatingPasswordField;


    private String emailAddressField;
    private String passwordField;

    //@todo: change this to use a user for setting values.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotten_password);

        existingEmailField = (EditText) findViewById(R.id.existingEmailAddress);
        updatingPasswordField = (EditText) findViewById(R.id.updatepassword);
        validateEmailField();
        validatePasswordField();


    }


    public String getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(String passwordField) {
        this.passwordField = passwordField;
    }


    public String getEmailAddressField() {
        return emailAddressField;
    }

    public void setEmailAddressField(String emailAddressField) {
        this.emailAddressField = emailAddressField;
    }

    /**
     * Starts the login activity.
     * The activity is related to when a user remembers their password, they can just use the login activity.
     *
     * @param view
     */
    public void rememberPassword(View view) {

        Intent loginActivity = new Intent(this, MainActivity.class);
        //Switches the activity to login.
        finish();
        startActivity(loginActivity);
    }

    /**
     * Validates that a user adds the create email address.
     *
     * @return The email address.
     */
    public String validateEmailField() {

        existingEmailField.addTextChangedListener(new TextWatcher() {

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
                String userInput = existingEmailField.getText().toString();
                //Checks whether the user has left the field blank
                if (userInput.isEmpty()) {
                    //error message related to blank field.
                    existingEmailField.setError("Please don't leave blank");
                    //Checks if the user has added regular expression
                    //@todo reference regex checker
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //special character error thrown.
                    existingEmailField.setError("Special characters can't be used");

                    // @Todo reference Regex from Google regex checker
                } else if (userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    //removes spacing from the email
                    String validUserEmail = userInput.trim();
                    //notifies the user that the email is valid.
                    createToastWithText("Valid Email");
                    //sets the email address
                    setEmailAddressField(validUserEmail);

                    //if the email address doesn't match validation then error is thrown.
                } else if (!userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    existingEmailField.setError("Please include a valid email");
                }
            }
        });
        //gets the email address that has been set.
        return getEmailAddressField();

    }

    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    /**
     * validates that a password matches the right format.
     *
     * @return The password value.
     */
    public String validatePasswordField() {

        updatingPasswordField.addTextChangedListener(new TextWatcher() {

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
                String userInput = updatingPasswordField.getText().toString();

                //if the edit text doesn't contain text
                if (userInput.isEmpty()) {
                    //set an error related to no text being displayed.
                    updatingPasswordField.setError("Please don't leave blank ");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    updatingPasswordField.setError("Special characters can't be used");
                    //Checks that the password is more than or equal to 4 characters.
                } else if (userInput.length() <= 3) {
                    //Error is displayed if the password isn't long enough
                    updatingPasswordField.setError("Password must be longer than four characters");
                    //Checks to see if a number hasn't been added in the password.
                } else if (!userInput.matches(".*\\d+.*")) {
                    //Sets the error related to no numbers being in the password.
                    updatingPasswordField.setError("Password Needs to contain 1 number");
                    //Checks to see if a number has been added in the password.
                } else if (userInput.matches(".*\\d+.*")) {
                    //Toast is displayed
                    createToastWithText("Valid Password.");
                    //Removes spacing from the string
                    String validPassword = userInput.trim();
                    //sets the password field
                    setPasswordField(validPassword);
//                    String passwrod = getPasswordField();
                }


            }
        });
        //Returns the password field.
        return getPasswordField();
    }

    /**
     * This method allows the user to update their password if they forget it.
     * A check is performed for whether the user email exists, before allowing an update on password.
     *
     * @return Whether the user exists.
     */
    private boolean updatePassword() {

        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean userExists = false;
        //Performs the sql query on the email a user has passed, to check if present in the DB.
        Cursor cursor = db.rawQuery("SELECT * FROM " + UsersContract.UsersEntry.TABLE_NAME + " WHERE " + UsersContract.UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + getEmailAddressField() + "'", null);
        //If a column number exists related to the query, then the user is sent back to the login screen.
        cursor.moveToFirst();
        //This checks for whether a row is returned, suggesting that the user exists.
        if (cursor.getCount() == 1) {
            //Sets user exists to true.
            userExists = true;
            //Toast message, responds to say that the user has an account
            createToastWithText("Account valid");
            //Update query users the user email address, and then gets the current password field then peforms an update for the password, by replacing the old one.
            Cursor updateCursor = db.rawQuery("UPDATE " + UsersContract.UsersEntry.TABLE_NAME + " SET "
                    + UsersContract.UsersEntry.COLUMN_USER_PASSWORD + "=" + "'" + getPasswordField() + "'" +
                    " WHERE " + UsersContract.UsersEntry.COLUMN_USER_EMAIL + " =" + "'" + getEmailAddressField() + "'", null);
            //closes the cursor.
            updateCursor.close();
            //While if cursor is 0 then it suggets the user doesn't exist.
        } else if (cursor.getCount() == 0) {
            //Notifies user that email doesn't exist
            createToastWithText("Email doesn't exist, either re-type email or tap remember password");
            //sets whether the user exists to false.
            userExists = false;


        }
        //closes first read cursor.
        cursor.close();
        //closes db
        db.close();
        //Returns whether the user is logged in i.e false means user doesn't have an account, while true then the user does.
        return userExists;
    }

    /**
     * if the user exists and password updated then send back to main activity.
     *
     * @param view References current view.
     */
    public void updatePasswordAndChangePage(View view) {

        if (updatePassword()) {
            //if the user exists and password updated then send back to main activity.
            Intent loginActivity = new Intent(this, MainActivity.class);
            //Switches the activity to login in activity
            startActivity(loginActivity);
            //Checks whether the user the user account exists.
            checkData();

        } else if (!updatePassword()) {
            //If false then doesn't exist.
            createToastWithText("Email Doesn't exist");
        }


    }

    /**
     * Checks whether the user the user account exists.
     */
    private void checkData() {
        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //SQL query uses the email and password passed to check if it matches the database equivalents
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                UsersContract.UsersEntry.TABLE_NAME, null);
        //If the data returned from the query is 1 then the account exists.
        cursor.moveToFirst();
        Log.v("data after", DatabaseUtils.dumpCursorToString(cursor));

        cursor.close();

    }
}




