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

import coffee.prototype.android.cleandrinksapplication.Model.UserAddedAlcholicDrink;
import coffee.prototype.android.cleandrinksapplication.data.DBQueryHelper;


public class AddAlcholicDrink extends AppCompatActivity {

    private EditText alcoholDrinkName;
    private EditText alcoholDrinkVolume;
    private EditText alcoholDrinkType;
    private EditText alcoholPercentage;
    private Button cancelButton;
    private DBQueryHelper queryHelper = new DBQueryHelper();


    private UserAddedAlcholicDrink userAddedDrink = new UserAddedAlcholicDrink();
    private ActivityHelper helper = new ActivityHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alcholic_drink);
        alcoholDrinkName = (EditText) findViewById(R.id.name_your_drink_edit);
        alcoholDrinkVolume = (EditText) findViewById(R.id.drink_volume_edit);
        alcoholDrinkType = (EditText) findViewById(R.id.drink_type_edit);
        alcoholPercentage = (EditText) findViewById(R.id.drink_percentage_edit);
        cancelButton = (Button) findViewById(R.id.cancel_alcohol_button);

        //Validates the name of a drink when user is typing
        validateDrinkNameField();
        //Validates the volume of a drink when user is typing
        validateDrinkVolume();
        //Validates the type of a drink when user is typing
        validateAlcoholDrinkType();
        //Validates the percentage of a drink when user is typing and calculates the units.
        validateDrinkPercentage();


    }

    /**
     * This method validates using input for the drink name edit text.
     * This method checks that no illegal characters
     *
     * @return Returns the value that the user has typed into the edit text.
     */
    public String validateDrinkNameField() {

        //Text watcher reads what a user is typing.
        alcoholDrinkName.addTextChangedListener(new TextWatcher() {

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
                String userInput = alcoholDrinkName.getText().toString();

                //if the edit text doesn't contain text
                if (userInput.isEmpty()) {
                    //Throw error related to being blank
                    alcoholDrinkName.setError("Please don't leave blank");
                    //Sets drink name to not valid, if a user breaks validation rule.
                    userAddedDrink.setDrinkName("not valid");
                    //Validates if any special chars are used.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //Sets errors if spec chars are used.
                    alcoholDrinkName.setError("Special characters can't be used");
                    //Sets drink name to not valid, if a user breaks validation rule.
                    userAddedDrink.setDrinkName("not valid");
                    //Reference for regex: http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                    //Expression allows a user to type up to 15 chars with spaces for a name
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,15}")) {
                    userAddedDrink.setDrinkName(userInput);
                    //Sends user feedback that name was correct.
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("^([a-zA-Z] ?){4,15}")) {
                    //if a number is found anywhere in the input, then it's declined.
                    if (userInput.matches(".*\\d+.*")) {
                        //Error is set if numbers are added in the drink name
                        alcoholDrinkName.setError("Numbers aren't accepted");

                    } else {
                        //Sets error related to character length
                        alcoholDrinkName.setError("Drink name, needs to be between 4 and 15 characters");
                        userAddedDrink.setDrinkName("not valid");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    //Error is set if numbers are added in the drink name
                    alcoholDrinkName.setError("A drink name can't contain numbers");
                    userAddedDrink.setDrinkName("not valid");

                }
            }
        });
        //Returns the users drink name.
        return userAddedDrink.getDrinkName();

    }

    /**
     * This method validates the drink volume.
     * It checks that the drinks volume is like the following two digits, three or four digits.
     *
     * @return The  volume of the drink.
     */
    public double validateDrinkVolume() {


        alcoholDrinkVolume.addTextChangedListener(new TextWatcher() {

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
                String userInput = alcoholDrinkVolume.getText().toString();

                //If the field is blank then an error is thrown
                if (userInput.isEmpty()) {
                    alcoholDrinkVolume.setError("Please don't leave blank");
                    //Sets the value to zero, this is to combat against null values crashing the application.
                    userAddedDrink.setDrinkVolume(0.00);
                    //Checks that the user doesn't try to use any special characters for SQL injection.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //sets an error if special characters are used.
                    alcoholDrinkVolume.setError("Special characters can't be used");
                    //Sets the drink volume to zero.
                    userAddedDrink.setDrinkVolume(0.00);

                    //Check is performed to see if the drink volume is a number and at least has two digits.
                } else if (userInput.matches("[1-9]{2,4}") | userInput.matches("[0-9]{2,4}")) {
                    //Spaces are removed from the volume
                    String drinkVolume = userInput.trim();
                    //Then converted as a double value to be then used in the user added drink setter.
                    double drinkVolumeDuble = Double.parseDouble(drinkVolume);
                    //Sets the drink volume.
                    userAddedDrink.setDrinkVolume(drinkVolumeDuble);
                    //if the expression doesn't match the pattern then error is thrown and volume is set to 0.00
                } else if (!userInput.matches("[1-9]{2,4}") | !userInput.matches("[0-9]{2,4}")) {
                    alcoholDrinkVolume.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    userAddedDrink.setDrinkVolume(0.00);

                }
            }
        });
        //Returns the drink volume.
        return userAddedDrink.getDrinkVolume();
    }


    /**
     * This checks the only known types of alcohol or allowed to be entered for the user.
     * This is the the drink can be displayed with the correct image, and is populated from the db.
     *
     * @return The alcohol type.
     */
    public String validateAlcoholDrinkType() {


        alcoholDrinkType.addTextChangedListener(new TextWatcher() {

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
                String userInput = alcoholDrinkType.getText().toString();
                //If the field is blank then an error is thrown
                if (userInput.isEmpty()) {
                    alcoholDrinkType.setError("Please don't leave blank");
                    userAddedDrink.setDrinkType("not valid");
                    //Checks that the user doesn't try to use any special characters for SQL injection.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    alcoholDrinkType.setError("Special characters can't be used");
                    //Sets the drink type to not valid.
                    userAddedDrink.setDrinkType("not valid");
                    //Checks to see only if the matching alcohol types are present.
                } else if (userInput.matches("Red Wine") | userInput.matches("Lager") | userInput.matches("RealAle") | userInput.matches("Stout")
                        | userInput.matches("Gin") | userInput.matches("White Wine")
                        | userInput.matches("CraftBeer") | userInput.matches("Whiskey") | userInput.matches("Vodka")) {
                    //If the drink type is matched then the type is set.
                    userAddedDrink.setDrinkType(userInput);
                    //Response stating it's valid.
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("Red Wine") | !userInput.matches("Lager") | !userInput.matches("RealAle") | !userInput.matches("Stout")
                        | !userInput.matches("Gin") | !userInput.matches("White Wine") | !userInput.matches("CraftBeer") |
                        !userInput.matches("Whiskey") | !userInput.matches("Vodka")) {
                    //Error message to prompt the user for the types that are valid.
                    alcoholDrinkType.setError("Can only use the following types, RealAle, Whiskey, Lager, CraftBeer, White Wine, Red Wine, Gin, Stout");
                    //If any numbers are added then this isn't valid.
                } else if (userInput.matches(".*\\d+.*")) {
                    userAddedDrink.setDrinkType("not valid");
                    //Error message displaying that numbers can't be used.
                    alcoholDrinkType.setError("A drink type can't contain numbers");

                }
            }
        });
        //Returns the drink type.
        return userAddedDrink.getDrinkType();

    }


    public double validateDrinkPercentage() {


        alcoholPercentage.addTextChangedListener(new TextWatcher() {

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
                String userInput = alcoholPercentage.getText().toString();

                //If the field is blank then an error is thrown
                if (userInput.isEmpty()) {
                    alcoholPercentage.setError("Please don't leave blank");
                    userAddedDrink.setAlcoholPercentage(0.00);
                    //Checks that the user doesn't try to use any special characters for SQL injection.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    alcoholPercentage.setError("Special characters can't be used");

                    userAddedDrink.setAlcoholPercentage(0.00);
                    //This allows input to be d.dd or dd.dd for varying percentages.
                } else if (userInput.matches("^[0-9][.][0-9][0-9]") | userInput.matches("^[0-9][0-9][.][0-9][0-9]")) {
                    //Removes any spaces from the string
                    String drinkPercentage = userInput.trim();
                    //Converts into a double value.
                    double drinkPercentageDouble = Double.parseDouble(drinkPercentage);
                    //Sets the acohol percentage.
                    userAddedDrink.setAlcoholPercentage(drinkPercentageDouble);
                    //If input doesn't match the pattern
                } else if (!userInput.matches("^[0-9][.][0-9]") | !userInput.matches("[0-9][0-9][.][0-9][0-9]")) {
                    //Error is thrown regarding the value not being 2 or 4 digits.
                    alcoholPercentage.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    userAddedDrink.setAlcoholPercentage(0.00);

                }
            }
        });
        return userAddedDrink.getAlcoholPercentage();

    }

    /**
     * Returns the user back to the previous activity
     *
     * @param view References the current view.
     */
    public void cancelAlcoholicButton(View view) {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sets vibrate to the buttuon which goes back to the prior activity.
                cancelButton.setContentDescription("Cancel button for exiting the add a drink activity");
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                //Mild vibrate.
                vibe.vibrate(100);
                //returns to previous activity.
                finish();
            }
        });
    }


    /**
     * Checks that all user fields are valid then adds the goal to the database.
     *
     * @param view References the current view.
     */
    public void submitAddAlcoholicDrink(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        //Checks that values haven't been set with the non valid error messages
        if (validateDrinkNameField() != ("not valid") && validateAlcoholDrinkType() != ("not valid") && validateDrinkVolume() != 0.00 && validateDrinkPercentage() != 0.00) {
            helper.createToastWithText("Valid input");
            //Displays the units for the drink just added.
            helper.createToastWithText("Units for this drink are: " + userAddedDrink.calculateUnitTotal(validateDrinkVolume(), validateDrinkPercentage()));
            //Calculates the amount of alcohol found in the drink.
            double userUnits = userAddedDrink.calculateUnitTotal(validateDrinkVolume(), validateDrinkPercentage());
            //Returns the corresponding image related to type.
            int value = returnImage(validateAlcoholDrinkType(), getApplicationContext());
            //Inserts the drink into the drink category table.
            queryHelper.insertIntoDBImage(getApplicationContext(), validateDrinkNameField() + " units:" + userUnits, validateAlcoholDrinkType(), validateDrinkVolume(), userUnits, value);
            //Goes to previous activity.
            finish();
            //If any of the values equal not valid then input isn't correct, and the user will need to refill the fields.
            // == is used for comparison otherwise if equals() is used then null reference is thrown.
        } else if (validateDrinkNameField() == ("not valid") && validateAlcoholDrinkType() == ("not valid") && validateDrinkVolume() == 0.00 && validateDrinkPercentage() != 0.00) {
            //Toast displays review the values.
            helper.createToastWithText("Please review the values entered");
        } else {
            //Toast displays review the values.
            helper.createToastWithText("Please review the values entered");

        }


    }

    /**
     * Will return the corresponding image depending on drink type.
     *
     * @param drinkType References the type of drink
     * @param context   References the context of the application.
     * @return The image resource related to the type.
     */
    public int returnImage(String drinkType, Context context) {
        int defaultImage = 1;


        if (drinkType.equals("Red Wine")) {
            defaultImage = context.getResources().getIdentifier("red_wine_item", "drawable", context.getPackageName());

        } else if (drinkType.equals("Coffee")) {
            defaultImage = context.getResources().getIdentifier("caffine_cup", "drawable", context.getPackageName());

        } else if (drinkType.equals("Gin")) {
            defaultImage = context.getResources().getIdentifier("vodka", "drawable", context.getPackageName());

        } else if (drinkType.equals("Lager")) {
            defaultImage = context.getResources().getIdentifier("beerbottle", "drawable", context.getPackageName());

        } else if (drinkType.equals("RealAle")) {
            defaultImage = context.getResources().getIdentifier("real_ale_listing", "drawable", context.getPackageName());

        } else if (drinkType.equals("Stout")) {
            defaultImage = context.getResources().getIdentifier("stout_listing", "drawable", context.getPackageName());

        } else if (drinkType.equals("Vodka")) {
            defaultImage = context.getResources().getIdentifier("gin", "drawable", context.getPackageName());

        } else if (drinkType.equals("Whiskey")) {
            defaultImage = context.getResources().getIdentifier("whiskey_listing", "drawable", context.getPackageName());

        } else if (drinkType.equals("White Wine")) {
            defaultImage = context.getResources().getIdentifier("white_wine_item", "drawable", context.getPackageName());

        } else if (drinkType.equals("CraftBeer")) {
            defaultImage = context.getResources().getIdentifier("craft_beer_bottle", "drawable", context.getPackageName());

        } else if (drinkType.equals("Tea")) {
            defaultImage = context.getResources().getIdentifier("decaf_tea", "drawable", context.getPackageName());

        } else {
            //This sets the default drink to the wine bottle, if the type isn't known.
            defaultImage = context.getResources().getIdentifier("wine_bottle", "drawable", context.getPackageName());

        }
        return defaultImage;
    }


}

