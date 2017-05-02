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


        drinkNameField();
        coffeeDrinkVolumeField();
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


                if (userInput.isEmpty()) {
                    drinkName.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    drinkName.setError("Special characters can't be used");
                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,15}")) {
                    userAddedDrink.setDrinkName(userInput);
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("^([a-zA-Z] ?){4,15}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        drinkName.setError("Numbers aren't accepted");

                    } else {
                        drinkName.setError("Drink name, needs to be between 4 and 15 characters");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    drinkName.setError("A drink name can't contain numbers");

                }
            }
        });
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


                if (userInput.isEmpty()) {
                    drinkVolume.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    drinkVolume.setError("Special characters can't be used");
                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("^(\\d){2,4}")) {
                    int userInputs = Integer.parseInt(userInput);
                    double userInputDouble = Double.parseDouble(userInput);

                    userAddedDrink.setDrinkVolume(userInputDouble);
                    if (userInputs > 1000) {
                        drinkVolume.setError("Drink volume can't be  more than a litre");
                    } else if (userInputs < 30) {
                        drinkVolume.setError("Drink volume can't be below the size of an espresso");

                    }
                } else if (!userInput.matches("^(\\d){2,4}")) {
                    drinkVolume.setError("A drink volume should be have at least 2 numbers");

                }
            }
        });
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


                if (userInput.isEmpty()) {
                    drinkStrength.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    drinkStrength.setError("Special characters can't be used");
                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("\\d")) {
                    helper.createToastWithText("Valid shot amount");
                    int caffineContent = Integer.parseInt(userInput);


                    double caffineQuantity = userAddedDrink.calculateCaffineAmount(caffineContent);
                    userAddedDrink.setCaffineContent(caffineQuantity);

                }
            }
        });
        return userAddedDrink.getCaffineContent();

    }

    public void submitAddDrink(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        helper.createToastWithText("Coffee object" + "drink name" + drinkNameField() + "dirnk volumne:" + coffeeDrinkVolumeField() + "drink strength " + coffeeStrengthField());


        helper.insertIntoDB(getApplicationContext(), drinkNameField() + " caffeine content:" + coffeeStrengthField()+"mg", "Coffee", coffeeDrinkVolumeField(), coffeeStrengthField());


        Intent changeToEspressoListing = new Intent(this, DrinkListingPageEspresso.class);
        //Switches the activity to sign up.
        startActivity(changeToEspressoListing);


        finish();
    }


}

