package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import coffee.prototype.android.cleandrinksapplication.data.UsersContract.UsersEntry;

import org.w3c.dom.Text;

import coffee.prototype.android.cleandrinksapplication.data.UsersContract;
import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;

import static coffee.prototype.android.cleandrinksapplication.R.id.debugging;
import static coffee.prototype.android.cleandrinksapplication.R.id.password_address_sign_up_field;

public class Sign_Up_Activity extends AppCompatActivity {
    private EditText signUpEmailAddressInput;
    private EditText signUpPasswordInput;
    private int checkNumberEmail;
    private int checkNumberPassword;



    private String userEmail;
    private String userPassword;

    private UsersDBHelper usersDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_);
        signUpEmailAddressInput = (EditText) findViewById(R.id.email_address_sign_up_field);
        signUpPasswordInput = (EditText) findViewById(password_address_sign_up_field);
        validateEmailField();
        validatePasswordField();

        usersDBHelper = new UsersDBHelper(this);
//        validateDataBaseHasBeenCreated();

    }

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
//    }
//
//
////    /**
//     * This method checks that a user isn't passing in illegal special characters.
//     * or empty strings or invalid emails.
//     */
    public int validateEmailField() {

        signUpEmailAddressInput.addTextChangedListener(new TextWatcher() {

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
                String userInput = signUpEmailAddressInput.getText().toString();
                checkNumberEmail = 0;

                if (userInput.isEmpty()) {
                    signUpEmailAddressInput.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    signUpEmailAddressInput.setError("Special characters can't be used");

//                Regex from Google regex checker

                } else if (userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    checkNumberEmail += 1;
                    String validUserEmail = userInput.trim();
                    createToastWithText("Valid Email");
                    setUserEmail(validUserEmail);



                } else if (!userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    signUpEmailAddressInput.setError("Please include a valid email");
                }
            }
        });
        return checkNumberEmail;

    }

    public int validatePasswordField() {

        signUpPasswordInput.addTextChangedListener(new TextWatcher() {

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
                String userInput = signUpPasswordInput.getText().toString();
                checkNumberPassword = 0;

                if (userInput.isEmpty()) {
                    signUpPasswordInput.setError("Please don't leave blank ");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    signUpPasswordInput.setError("Special characters can't be used");

                } else if (userInput.length() <= 3) {
                    signUpPasswordInput.setError("Password must be longer than four characters");
                } else if (!userInput.matches(".*\\d+.*")) {
                    signUpPasswordInput.setError("Password Needs to contain 1 number");
                } else if (userInput.matches(".*\\d+.*")) {
                    createToastWithText("Valid Passowrd");
                    checkNumberPassword += 1;
                    String validPassword = userInput.trim();
                    setUserPassword(validPassword);

                }


            }
        });


        return 1;
    }





    /**
     * Intalises a Toast object and then displays text.
     *
     * @param toastText Uses the text passed, and is displayed by the toast.
     */
    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    public void openWeightActivityAfterCorrectSignUp(View view) {
        if (validateEmailField() == 1 && validatePasswordField() == 1) {
//            Log.d("Creating database CREATED","CREATED");
            insertUser(getUserEmail(),getUserPassword());
            Log.d("aFTER CREATED","CREATED");
            validateDataBaseUserBeenCreated();
            Intent changeToWeightPage = new Intent(this, Weight_and_Height_Activity.class);
            startActivity(changeToWeightPage);
        } else if (checkNumberEmail == 0 | checkNumberPassword == 0) {
            createToastWithText("Please ensure email and password are valid");
        }

    }


    private void validateDataBaseHasBeenCreated(){
        UsersDBHelper dbHelper = new UsersDBHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ UsersContract.UsersEntry.TABLE_NAME,null);



        try{
            TextView displayResult = (TextView) findViewById(debugging);
            displayResult.setText("Rows present: " +cursor.getCount());
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));

        }finally {
            cursor.close();
        }
    }

    private void validateDataBaseUserBeenCreated(){
        UsersDBHelper dbHelper = new UsersDBHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+UsersEntry.TABLE_NAME+" WHERE "+UsersEntry.COLUMN_USER_EMAIL+"= 'nas29@aber.ac.uk'",null);



        try{
            TextView displayResult = (TextView) findViewById(debugging);
//            displayResult.setText("Rows present: " +cursor.getCount());
            Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));

        }finally {
            cursor.close();
        }
    }

    private void insertUser(String email, String password){
        SQLiteDatabase db = usersDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersEntry.COLUMN_USER_EMAIL,email);
        contentValues.put(UsersEntry.COLUMN_USER_PASSWORD,password);

        long newRowId = db.insert(UsersEntry.TABLE_NAME,null,contentValues);

        Log.v("sign up activity","new row id"+newRowId);

    }







}
