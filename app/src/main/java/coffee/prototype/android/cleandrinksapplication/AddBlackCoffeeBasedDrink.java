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
import android.widget.RadioButton;

import coffee.prototype.android.cleandrinksapplication.Model.Coffee;
import coffee.prototype.android.cleandrinksapplication.data.DBQueryHelper;

public class AddBlackCoffeeBasedDrink extends AppCompatActivity {

    private EditText blackCoffeeDrinkName;
    private Coffee blackCoffee = new Coffee();
    private ActivityHelper helper = new ActivityHelper();

    private DBQueryHelper queryHelper = new DBQueryHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_black_coffee_based_drink);
        blackCoffeeDrinkName = (EditText) findViewById(R.id.black_coffee_drink_name);
        validateDrinkNameField();

    }


    /**
     * This method validates using input for the drink name edit text.
     * This method checks that no illegal characters
     *
     * @return Returns the value that the user has typed into the edit text.
     */
    public String validateDrinkNameField() {


        blackCoffeeDrinkName.addTextChangedListener(new TextWatcher() {

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
                String userInput = blackCoffeeDrinkName.getText().toString();


                if (userInput.isEmpty()) {
                    blackCoffeeDrinkName.setError("Please don't leave blank");
                    blackCoffee.setDrinkName("not valid");

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    blackCoffeeDrinkName.setError("Special characters can't be used");
                    blackCoffee.setDrinkName("not valid");


                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,15}")) {
                    blackCoffee.setDrinkName(userInput);
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("^([a-zA-Z] ?){4,15}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        blackCoffeeDrinkName.setError("Numbers aren't accepted");

                    } else {
                        blackCoffeeDrinkName.setError("Drink name, needs to be between 4 and 15 characters");
                        blackCoffee.setDrinkName("not valid");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    blackCoffeeDrinkName.setError("A drink name can't contain numbers");
                    blackCoffee.setDrinkName("not valid");

                }
            }
        });
        return blackCoffee.getDrinkName();

    }

    /**
     * Reference https://developer.android.com/guide/topics/ui/controls/checkbox.html
     * @param view References the view
     * @return The coffee drink type
     */
    public String onCheckCoffeeDrinkType(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String coffeeType = "";

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.americano:

                if (checked) {
                    //If Americano is selected set this as drink type
                    coffeeType = "Americano";
                    blackCoffee.setDrinkType(coffeeType);

                }

                break;
            case R.id.filter_coffee:
                if (checked) {
                    coffeeType = "filter coffee";
                    //If filter coffee is selected set this as drink type
                    blackCoffee.setDrinkType(coffeeType);


                }

                break;
            case R.id.instant_coffee:
                if (checked) {
                    coffeeType = "Instant coffee";
                    //If Instant coffee is selected set this as drink type
                    blackCoffee.setDrinkType(coffeeType);


                }

                break;

        }
        //Creates toast related to what has been selected.
        helper.createToastWithText("You've selected" + blackCoffee.getDrinkType());
        return blackCoffee.getDrinkName();
    }

    /**
     * Reference https://developer.android.com/guide/topics/ui/controls/checkbox.html
     * Reference for volume in drinks https://www.caffeineinformer.com/the-complete-guide-to-starbucks-caffeine
     * @param view References the view
     * @return The coffee drink size
     */
    public double onCheckCoffeeDrinkSize(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();
        double coffeeVolume = 0.00;

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.small_coffee:


                if (checked) {
                    coffeeVolume = 236.00;
                    //If Instant 236.00 is selected set this as drink volume
                    blackCoffee.setDrinkVolume(coffeeVolume);

                }

                break;
            case R.id.medium_coffee:
                if (checked) {
                    coffeeVolume = 254.00;
                    //If Instant 254.00 is selected set this as drink volume
                    blackCoffee.setDrinkVolume(coffeeVolume);


                }

                break;
            case R.id.large_coffee:
                if (checked) {
                    coffeeVolume = 473.00;
                    //If Instant 473.00 is selected set this as drink volume
                    blackCoffee.setDrinkVolume(coffeeVolume);


                }

                break;

        }
        //Notifies user which volume they selected
        helper.createToastWithText("You've selected" + blackCoffee.getDrinkVolume() + "ml");
        //Then sets this as the volume.
        return blackCoffee.getDrinkVolume();
    }

    /**
     * Goes back to prior activity.
     * @param view References the view.
     */
    public void onClickCancelDrink(View view) {
        //Creates a subtle vibrate.
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        finish();
    }

    /**
     * This method checks to see that the user has selected a type and volume
     * Then sets the caffeine in relation to that volume.
     * Reference for caffeine in drinks https://www.caffeineinformer.com/the-complete-guide-to-starbucks-caffeine
     * @param view Relates to the current view.
     */
    public void addBlackCoffeeBasedDrink(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        double caffine = 0.00;
        //Every statement checks the possible type and corresponding volume, then sets caffeine level based on that.
        if (blackCoffee.getDrinkType() == "Americano" && blackCoffee.getDrinkVolume() == 236.00) {
            blackCoffee.setCaffineContent(75.00);
        } else if (blackCoffee.getDrinkType() == "Americano" && blackCoffee.getDrinkVolume() == 254.00) {
            blackCoffee.setCaffineContent(150);
        } else if (blackCoffee.getDrinkType() == "Americano" && blackCoffee.getDrinkVolume() == 473.00) {
            blackCoffee.setCaffineContent(225);
        } else if (blackCoffee.getDrinkType() == "filter coffee" && blackCoffee.getDrinkVolume() == 236.00) {
            blackCoffee.setCaffineContent(160.00);
        } else if (blackCoffee.getDrinkType() == "filter coffee" && blackCoffee.getDrinkVolume() == 254.00) {
            blackCoffee.setCaffineContent(240.00);
        } else if (blackCoffee.getDrinkType() == "filter coffee" && blackCoffee.getDrinkVolume() == 473.00) {
            blackCoffee.setCaffineContent(320.00);
        } else if (blackCoffee.getDrinkType() == "Instant coffee" && blackCoffee.getDrinkVolume() == 236.00) {
            blackCoffee.setCaffineContent(57.00);
        } else if (blackCoffee.getDrinkType() == "Instant coffee" && blackCoffee.getDrinkVolume() == 254.00) {
            //calculated based on the amount of caffine in 236ml
            blackCoffee.setCaffineContent(62.7);
        } else if (blackCoffee.getDrinkType() == "Instant coffee" && blackCoffee.getDrinkVolume() == 473.00) {
            blackCoffee.setCaffineContent(114);
        }
        //If the type isn't null and the volume isn't 0.00 then the users input as accepted.
        if (blackCoffee.getDrinkType() != null && blackCoffee.getDrinkVolume() != 0.00 && blackCoffee.getDrinkName() != "not valid") {
            helper.createToastWithText("accepted");
            //Image for the drink type is took
            int image = queryHelper.returnImageRelatedToDrinkTypeForAddDrinkDb(blackCoffee.getDrinkType(), getApplicationContext());
            //Then inserted into the db, with the correct image.
            queryHelper.insertIntoDBImage(getApplicationContext(), validateDrinkNameField()+ " caffeine:"+blackCoffee.getCaffineContent()+"mg", "Black Coffee", blackCoffee.getDrinkVolume(), blackCoffee.getCaffineContent(), image);
            //Goes back to prior activity.
            finish();
          //If the type is null and 0.00 or not valid then input isn't valid.
        } else if (blackCoffee.getDrinkType() == null | blackCoffee.getDrinkVolume() == 0.00 | blackCoffee.getDrinkName() == "not valid") {
            helper.createToastWithText("Please review values entered");

        }

    }



}
