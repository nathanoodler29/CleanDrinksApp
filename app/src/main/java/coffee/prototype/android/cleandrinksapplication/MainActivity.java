package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends AppCompatActivity {
    private EditText emailAddressInput;
    private EditText passwordInput;
    private LoginButton loginButton;

    private Button button;
    //handles facebook fragment?
    CallbackManager callbackManager;
    private View view;
    private int checkNumberEmail;
    private int checkNumberPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());

        emailAddressInput = (EditText) findViewById(R.id.email_address_field);
        passwordInput = (EditText) findViewById(R.id.password_field);

        validateEmailField();
        validatePasswordField();
        createFacebookLoginButton();
        facebookLoginHandler();
//        openWeightActivityAfterCorrectSignOn(view);

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

    /**
     * This method checks that a user isn't passing in illegal special characters.
     * or empty strings or invalid emails.
     */
    public int validateEmailField() {

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
                checkNumberEmail=0;

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
                    createToastWithText("Valid Email");
                    checkNumberEmail+=1;
                } else if (!userInput.matches("^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$")) {
                    emailAddressInput.setError("Please include a valid email");
                }
            }
        });
        return checkNumberEmail;
    }

    public int validatePasswordField() {

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
                checkNumberPassword=0;

                if (userInput.isEmpty()) {
                    passwordInput.setError("Please don't leave blank ");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    passwordInput.setError("Special characters can't be used");

                } else if (userInput.length()<=3) {
                    passwordInput.setError("Password must be longer than four characters");
                }else if (!userInput.matches(".*\\d+.*")) {
                    passwordInput.setError("Password Needs to contain 1 number");
                }else if(userInput.matches(".*\\d+.*")){
                    createToastWithText("Valid Password");
                    checkNumberPassword=+1;


                }

            }
        });
        return checkNumberPassword;

    }

    public void createFacebookLoginButton(){
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);



    }


    public void facebookLoginHandler(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                createToastWithText("Facebook login successful");


            }

            @Override
            public void onCancel() {
                createToastWithText("Facebook login canceled ");

            }

            @Override
            public void onError(FacebookException error) {
                createToastWithText("Facebook login error"+error.getMessage());

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void openSignUpActivity(View view){

        Intent changeToSignUpPage = new Intent(this, Sign_Up_Activity.class);
        startActivity(changeToSignUpPage);


    }

    public void openWeightActivityAfterCorrectSignOn(View view){
        if(validateEmailField()==1 && validatePasswordField()==1){
            Intent changeToWeightPage = new Intent(this, Weight_and_Height_Activity.class);
            startActivity(changeToWeightPage);
        }else if(checkNumberEmail==0 | checkNumberPassword==0){
            createToastWithText("Please ensure email and password are valid");
        }

    }


}






