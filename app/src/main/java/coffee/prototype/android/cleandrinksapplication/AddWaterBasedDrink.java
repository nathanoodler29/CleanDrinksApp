package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
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
                    drinkName.setError("Special characters can't be used");
                    water.setDrinkName("not valid");


                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,20}")) {
                    water.setDrinkName(userInput);
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("^([a-zA-Z] ?){4,20}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        drinkName.setError("Numbers aren't accepted");

                    } else {
                        drinkName.setError("Drink name, needs to be between 4 and 20 characters");
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


                if (userInput.isEmpty()) {
                    drinkVolume.setError("Please don't leave blank");
                    water.setDrinkVolume(0.00);

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    drinkVolume.setError("Special characters can't be used");

                    water.setDrinkVolume(0.00);

                } else if (userInput.matches("[1-9]{2,4}") | userInput.matches("[0-9]{2,4}")) {
                    String drinkVolume = userInput.trim();
                    double drinkVolumeDuble = Double.parseDouble(drinkVolume);


                    water.setDrinkVolume(drinkVolumeDuble);

                } else if (!userInput.matches("[1-9]{2,4}") | !userInput.matches("[0-9]{2,4}")) {
                    drinkVolume.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    water.setDrinkVolume(0.00);

                }
            }
        });
        return water.getDrinkVolume();
    }


    public void submitWater(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        if (validateDrinkNameField() != ("not valid") && validateDrinkVolume() != (0.00)) {
            helper.createToastWithText("Valid input");
            Intent changeToEspressoListing = new Intent(this, DrinksCategoryAlcoholTypes.class);

            int value = helper.returnImage("Water", getApplicationContext());

            queryHelper.insertIntoDBForImage(getApplicationContext(), validateDrinkNameField(), "Water", validateDrinkVolume(), 0.00, value);

            finish();
        } else if (validateDrinkNameField() == ("not valid") && validateDrinkVolume() == (0.00)) {
            helper.createToastWithText("Please review the values entered");

        } else {
            helper.createToastWithText("Please review the values entered");

        }

    }

}
