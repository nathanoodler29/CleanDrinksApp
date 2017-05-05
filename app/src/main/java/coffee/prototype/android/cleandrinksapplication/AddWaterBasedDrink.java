package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import coffee.prototype.android.cleandrinksapplication.Model.Water;
import coffee.prototype.android.cleandrinksapplication.data.DBQueryHelper;

public class AddWaterBasedDrink extends AppCompatActivity {
    private EditText drinkName;
    private EditText drinkVolume;
    private Button addWater;


    private Water water = new Water();
    private ActivityHelper helper = new ActivityHelper();
    private DBQueryHelper queryHelper = new DBQueryHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water_based_drink);


        drinkName = (EditText) findViewById(R.id.add_drink_name_edit);
        drinkVolume = (EditText) findViewById(R.id.add_drink_volume_edit);
        addWater = (Button) findViewById(R.id.add_water_drink);

        validateDrinkNameField();
        validateDrinkVolume();

    }

    /**
     * This method validates using input for the drink name edit text.
     * This method checks that no illegal characters
     *
     * @return Returns the value that the user has typed into the edit text.
     */
    public String validateDrinkNameField() {


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
                    water.setDrinkName("not valid");

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //Sets errors if spec chars are used.
                    drinkName.setError("Special characters can't be used");
                    //Sets drink name to not valid, if a user breaks validation rule.
                    water.setDrinkName("not valid");
                    //Reference for regex: http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                    //Expression allows a user to type up to 15 chars with spaces for a name
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,15}")) {
                    water.setDrinkName(userInput);
                    //Sends user feedback that name was correct.
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("^([a-zA-Z] ?){4,15}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        //Error is set if numbers are added in the drink name
                        drinkName.setError("Numbers aren't accepted");

                    } else {
                        drinkName.setError("Drink name, needs to be between 4 and 15 characters");
                        //Error is set if numbers are added in the drink name
                        water.setDrinkName("not valid");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    drinkName.setError("A drink name can't contain numbers");
                    water.setDrinkName("not valid");

                }
            }
        });
        return water.getDrinkName();

    }

    /**
     * This method validates the drink volume.
     * It checks that the drinks volume is like the following two digits, three or four digits.
     *
     * @return The  volume of the drink.
     */
    public double validateDrinkVolume() {


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

                //If the field is blank then an error is thrown
                if (userInput.isEmpty()) {
                    drinkVolume.setError("Please don't leave blank");
                    //Sets the value to zero, this is to combat against null values crashing the application.
                    water.setDrinkVolume(0.00);

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //sets an error if special characters are used.
                    drinkVolume.setError("Special characters can't be used");
                    //Sets the drink volume to zero.
                    water.setDrinkVolume(0.00);
                    //Check is performed to see if the drink volume is a number and at least has two digits.
                } else if (userInput.matches("[1-9]{2,4}") | userInput.matches("[0-9]{2,4}")) {
                    String drinkVolume = userInput.trim();
                    //Then converted as a double value to be then used in the user added drink setter.
                    double drinkVolumeDuble = Double.parseDouble(drinkVolume);
                    //Sets the drink volume.
                    water.setDrinkVolume(drinkVolumeDuble);
                    //if the expression doesn't match the pattern then error is thrown and volume is set to 0.00
                } else if (!userInput.matches("[1-9]{2,4}") | !userInput.matches("[0-9]{2,4}")) {
                    drinkVolume.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    water.setDrinkVolume(0.00);

                }
            }
        });
        //Returns the drink volume.
        return water.getDrinkVolume();
    }


    /**
     * Checks to see if input from all fields is valid.
     * If input is valid then it's added to the database.
     *
     * @param view refers to the current view.
     */
    public void submitWater(View view) {
        //Sets vibrate
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        //If fields are not equal to valid then allow to populate database.
        if (validateDrinkNameField() != ("not valid") && validateDrinkVolume() != (0.00)) {
            helper.createToastWithText("Valid input");

            int value = queryHelper.returnImageRelatedToDrinkTypeForAddDrinkDb("Water", getApplicationContext());
            //Inserts water based drink into database
            queryHelper.insertIntoDBImage(getApplicationContext(), validateDrinkVolume() + "ml " + validateDrinkNameField(), "Water", validateDrinkVolume(), 0.00, value);
            //Goes back to prior activity.
            finish();
            //If not valid and not volume set then review values.
        } else if (validateDrinkNameField() == ("not valid") && validateDrinkVolume() == (0.00)) {
            helper.createToastWithText("Please review the values entered");
        } else {
            helper.createToastWithText("Please review the values entered");

        }

    }

    /**
     * Cancels the water prompt
     * @param view References the view
     */
    public void cancelWaterPrompt(View view){
        //sets vibrate
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        //back to prior activity
        finish();
    }
}

