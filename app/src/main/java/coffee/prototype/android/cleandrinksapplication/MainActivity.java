package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
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


public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton;

    //handles facebook fragment
    CallbackManager callbackManager;
    private View view;
    private int statusCode;


    private EditText emailAddressInputTest;
    private EditText passwordInputTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());


        emailAddressInputTest = (EditText) findViewById(R.id.email_address_field);

        passwordInputTest = (EditText) findViewById(R.id.password_field);


        createFacebookLoginButton();
        facebookLoginHandler();
        validateField(emailAddressInputTest, "^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$", "Email field empty", "Email field is valid", "Email is invalid.");

        validateField(passwordInputTest, ".*\\d+.*", "Password is empty", "Password is valid", "Password must include one number");


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


    public void createFacebookLoginButton() {

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);


    }


    public void facebookLoginHandler() {
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
                createToastWithText("Facebook login error" + error.getMessage());

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void openSignUpActivity(View view) {

        Intent changeToSignUpPage = new Intent(this, Sign_Up_Activity.class);
        startActivity(changeToSignUpPage);


    }


    public void openWeightActivityAfterCorrectSignOnTest(View view) {
        int resultEmail = validateField(emailAddressInputTest, "^(\\w[-._+\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})$", "Email field empty", "Email field is valid", "Email is invalid.");
        int resultPassword = validateField(passwordInputTest, ".*\\d+.*", "Password is empty", "Password is valid", "Password must include one number");
        if (resultEmail == 1 && resultPassword == 1) {
            Intent changeToWeightPage = new Intent(this, Weight_and_Height_Activity.class);
            startActivity(changeToWeightPage);
        } else if (statusCode == 0) {
            createToastWithText("Please ensure email and password are valid");
        }

    }

    public int validateField(final EditText element, final String validaiton, final String emptyErrorMessage, final String validValidaitonMsg, final String invalidValidaitonMessage) {

        element.addTextChangedListener(new TextWatcher() {
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
                String userInput = element.getText().toString();
                statusCode = 0;

                if (userInput.isEmpty()) {
                    element.setError(emptyErrorMessage);
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    element.setError("Special characters can't be used");

//                Regex from Google regex checker

                } else if (userInput.matches(validaiton)) {
                    createToastWithText(validValidaitonMsg);
                    statusCode += 1;
                } else if (!userInput.matches(validaiton)) {
                    element.setError(invalidValidaitonMessage);
                }
            }
        });
        return statusCode;
    }

}





