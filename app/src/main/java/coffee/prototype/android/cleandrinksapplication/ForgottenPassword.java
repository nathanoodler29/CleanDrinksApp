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

//    private UsersDBHelper usersDBHelper;

    private String emailAddressField;
    private String passwordField;


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


    public void rememberPassword(View view) {

        Intent loginActivity = new Intent(this, MainActivity.class);
        //Switches the activity to sign up.
        finish();
        startActivity(loginActivity);
    }


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
                if (userInput.isEmpty()) {
                    existingEmailField.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    existingEmailField.setError("Special characters can't be used");

//                Regex from Google regex checker
                } else if (userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    String validUserEmail = userInput.trim();
                    createToastWithText("Valid Email");
                    setEmailAddressField(validUserEmail);


                } else if (!userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    existingEmailField.setError("Please include a valid email");
                }
            }
        });
        return getEmailAddressField();

    }

    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }


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

                if (userInput.isEmpty()) {
                    updatingPasswordField.setError("Please don't leave blank ");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    updatingPasswordField.setError("Special characters can't be used");

                } else if (userInput.length() <= 3) {
                    updatingPasswordField.setError("Password must be longer than four characters");
                } else if (!userInput.matches(".*\\d+.*")) {
                    updatingPasswordField.setError("Password Needs to contain 1 number");
                } else if (userInput.matches(".*\\d+.*")) {
                    createToastWithText("Valid Password.");
                    String validPassword = userInput.trim();
                    setPasswordField(validPassword);
                    String passwrod = getPasswordField();
                    createToastWithText("Get password" + passwrod);

                }


            }
        });

        return getPasswordField();
    }


    private boolean updatePassword() {

        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean userExists = false;


        //Performs the sql query on the email a user has passed, to check if present in the DB.
        Cursor cursor = db.rawQuery("SELECT * FROM " + UsersContract.UsersEntry.TABLE_NAME + " WHERE " + UsersContract.UsersEntry.COLUMN_USER_EMAIL + "=" + "'" + getEmailAddressField() + "'", null);
        //If a column number exists related to the query, then the user is sent back to the login screen.
        cursor.moveToFirst();


        if (cursor.getCount() == 1) {
            userExists = true;
            createToastWithText("Account valid");
            Cursor updateCursor = db.rawQuery("UPDATE " + UsersContract.UsersEntry.TABLE_NAME + " SET "
                    + UsersContract.UsersEntry.COLUMN_USER_PASSWORD + "=" + "'" + getPasswordField() + "'" +
                    " WHERE " + UsersContract.UsersEntry.COLUMN_USER_EMAIL + " =" + "'" + getEmailAddressField() + "'", null);

            createToastWithText("Update password" + DatabaseUtils.dumpCursorToString(updateCursor));
            updateCursor.close();
        } else if (cursor.getCount() == 0) {
            createToastWithText("Email doesn't exist, either re-type email or tap remember password");
            userExists = false;


        }

        cursor.close();

        db.close();

        //Returns whether the user is logged in i.e false means user doesn't have an account, while true then the user does.

        return userExists;
    }


    public void updatePasswordAndChangePage(View view) {

        if (updatePassword()) {
            Intent loginActivity = new Intent(this, MainActivity.class);
            //Switches the activity to sign up.
            startActivity(loginActivity);

            checkData();

        } else if (!updatePassword()) {

            createToastWithText("Email Doesn't exist");
        }


    }


    private void checkData() {
//        SessionManager sessionManager = new SessionManager(getApplicationContext());

        UsersDBHelper dbHelper = new UsersDBHelper(this);
        //Makes the database readable.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //SQL query uses the email and password passed to check if it matches the database equivalents
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                UsersContract.UsersEntry.TABLE_NAME, null);
        //If the data returned from the query is 1 then the account exists.
        cursor.moveToFirst();
        //Get the user ID

        Log.v("data after", DatabaseUtils.dumpCursorToString(cursor));

        createToastWithText("check data of user name password" + DatabaseUtils.dumpCursorToString(cursor));
        cursor.close();

    }
}




