package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import coffee.prototype.android.cleandrinksapplication.Model.Coffee;


public class AddEspressoDrink extends AppCompatActivity {


    private EditText drinkName;
    private EditText drinkVolume;
    private EditText drinkStrength;
    private Coffee userAddedDrink = new Coffee();
    private ActivityHelper helper = new ActivityHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_espresso_drink);

        drinkName = (EditText) findViewById(R.id.coffee_drink_name);
        drinkVolume = (EditText) findViewById(R.id.coffee_drink_volume);
        drinkStrength = (EditText) findViewById(R.id.drink_strength);

        //Validates the name of a drink when user is typing
        drinkNameField();
        //Validates the volume of a drink when user is typing
        coffeeDrinkVolumeField();
        //calculates strength off coffee.
        coffeeStrengthField();


    }


    public String drinkNameField() {


        drinkName.addTextChangedListener(new TextWatcher() {

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
                String userInput = drinkName.getText().toString();

                //if the edit text doesn't contain text

                if (userInput.isEmpty()) {
                    drinkName.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //Sets errors if spec chars are used.
                    drinkName.setError("Special characters can't be used");
                    //Reference for regex: http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                    //Expression allows a user to type up to 15 chars with spaces for a name
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,15}")) {
                    userAddedDrink.setDrinkName(userInput);
                    //Sends user feedback that name was correct.
                    helper.createToastWithText("Valid drink name!");
                    //if a number is found anywhere in the input, then it's declined.
                } else if (!userInput.matches("^([a-zA-Z] ?){4,15}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        //Error is set if numbers are added in the drink name
                        drinkName.setError("Numbers aren't accepted");

                    } else {
                        drinkName.setError("Drink name, needs to be between 4 and 15 characters");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    //Error is set if numbers are added in the drink name
                    drinkName.setError("A drink name can't contain numbers");

                }
            }
        });
        //Returns the users drink name.
        return userAddedDrink.getDrinkName();

    }

    public double coffeeDrinkVolumeField() {


        drinkVolume.addTextChangedListener(new TextWatcher() {

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
                String userInput = drinkVolume.getText().toString();

                //if the edit text doesn't contain text
                if (userInput.isEmpty()) {
                    //Throw error related to being blank
                    drinkVolume.setError("Please don't leave blank");
                    //Validates if any special chars are used.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //Sets errors if spec chars are used.
                    drinkVolume.setError("Special characters can't be used");
                    //Check is performed to see if the drink volume is a number and at least has two digits.
                } else if (userInput.matches("^(\\d){2,4}")) {
                    int userInputs = Integer.parseInt(userInput);
                    //Then converted as a double value to be then used in the user added drink setter.
                    double userInputDouble = Double.parseDouble(userInput);
                    //Sets the drink volume.
                    userAddedDrink.setDrinkVolume(userInputDouble);
                    //Checks to see if volume is more than a litre.
                    if (userInputs > 1000) {
                        drinkVolume.setError("Drink volume can't be  more than a litre");
                        // checks to see if less than a shot of espresso
                    } else if (userInputs < 30) {
                        drinkVolume.setError("Drink volume can't be below the size of an espresso");

                    }
                    //If it doesn't match dd or dddd then error is thrown.
                } else if (!userInput.matches("^(\\d){2,4}")) {
                    drinkVolume.setError("A drink volume should be have at least 2 numbers");

                }
            }
        });
        //returns volume
        return userAddedDrink.getDrinkVolume();

    }


    public Double coffeeStrengthField() {


        drinkStrength.addTextChangedListener(new TextWatcher() {

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
                String userInput = drinkStrength.getText().toString();

                //if the edit text doesn't contain text
                if (userInput.isEmpty()) {
                    //Throw error related to being blank
                    drinkStrength.setError("Please don't leave blank");
                    //Validates if any special chars are used.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //Sets errors if spec chars are used.
                    drinkStrength.setError("Special characters can't be used");
                    //If input has one number then this is valid.
                } else if (userInput.matches("\\d")) {
                    helper.createToastWithText("Valid shot amount");
                    //sets user input value and converts to an int.
                    int caffineContent = Integer.parseInt(userInput);
                    //calculates the amount of caffine present.
                    double caffineQuantity = userAddedDrink.calculateCaffeineAmount(caffineContent);
                    //Then sets the caffeine content.
                    userAddedDrink.setCaffineContent(caffineQuantity);

                }
            }
        });
        //Returns the caffeine content.
        return userAddedDrink.getCaffineContent();

    }

    public void submitAddDrink(View view) {
        //Sets vibrate
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        //Inserts the new drink into the database.
        helper.insertIntoDB(getApplicationContext(), drinkNameField() + " caffeine content:" + coffeeStrengthField() + "mg", "Coffee", coffeeDrinkVolumeField(), coffeeStrengthField());
        //Go back to the drinks listing page.
        Intent changeToEspressoListing = new Intent(this, DrinkListingPageEspresso.class);
        //Start activity now.
        startActivity(changeToEspressoListing);
        //Go back an activity.
        finish();
    }

    /**
     * Quits the menu and returns user to previous activity.
     * @param view References the iew
     */
    public void cancelAddEspressoDrink(View view) {
        //Sets vibrate
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        //Go back an activity.
        finish();
    }


}

