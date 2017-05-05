package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import coffee.prototype.android.cleandrinksapplication.Model.Tea;
import coffee.prototype.android.cleandrinksapplication.data.DBQueryHelper;

public class AddTeaBasedDrink extends AppCompatActivity {

    private EditText teaDrinKName;
    private EditText teadrinkVolume;
    private Tea tea = new Tea();
    private ActivityHelper helper = new ActivityHelper();
    private DBQueryHelper queryHelper = new DBQueryHelper();

    private double teaStrength;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tea_based_drink);
        teaDrinKName = (EditText) findViewById(R.id.tea_drink_name);
        teadrinkVolume = (EditText) findViewById(R.id.tea_drink_volume);
        validateDrinkNameField();
        validateDrinkVolume();


    }


    public double getTeaStrength() {
        return teaStrength;
    }

    public void setteastrenght(double teaStrenght) {
        this.teaStrength = teaStrenght;
    }


    /**
     * This method validates the drink volume.
     * It checks that the drinks volume is like the following two digits, three or four digits.
     *
     * @return The  volume of the drink.
     */
    public String validateDrinkNameField() {


        teaDrinKName.addTextChangedListener(new TextWatcher() {

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
                String userInput = teaDrinKName.getText().toString();


                if (userInput.isEmpty()) {
                    //Throw error related to being blank
                    teaDrinKName.setError("Please don't leave blank");
                    //Sets drink name to not valid, if a user breaks validation rule.
                    tea.setDrinkName("not valid");
                    //Validates if any special chars are used.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    teaDrinKName.setError("Special characters can't be used");
                    //Sets drink name to not valid, if a user breaks validation rule.
                    tea.setDrinkName("not valid");
                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,15}")) {
                    tea.setDrinkName(userInput);
                    //Sends user feedback that name was correct.
                    helper.createToastWithText("Valid drink name!");
                    //Reference for regex: http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                    //Expression allows a user to type up to 15 chars with spaces for a name
                } else if (!userInput.matches("^([a-zA-Z] ?){4,15}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        //if a number is found anywhere in the input, then it's declined.
                        teaDrinKName.setError("Numbers aren't accepted");

                    } else {
                        //Sets error related to character length
                        teaDrinKName.setError("Drink name, needs to be between 4 and 15 characters");
                        tea.setDrinkName("not valid");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    //Error is set if numbers are added in the drink name
                    teaDrinKName.setError("A drink name can't contain numbers");
                    tea.setDrinkName("not valid");

                }
            }
        });
        //Returns the users drink name.
        return tea.getDrinkName();

    }


    /**
     * This method validates the drink volume.
     * It checks that the drinks volume is like the following two digits, three or four digits.
     *
     * @return The validated volume of the drink.
     */
    public double validateDrinkVolume() {


        teadrinkVolume.addTextChangedListener(new TextWatcher() {

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
                String userInput = teadrinkVolume.getText().toString();


                if (userInput.isEmpty()) {
                    teadrinkVolume.setError("Please don't leave blank");
                    tea.setDrinkVolume(0.00);

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //sets an error if special characters are used.
                    teadrinkVolume.setError("Special characters can't be used");
                    //Sets the drink to zero.
                    tea.setDrinkVolume(0.00);
                    //Check is performed to see if the drink volume is a number and at least has two digits.
                } else if (userInput.matches("[1-9]{2,4}") | userInput.matches("[0-9]{2,4}")) {
                    //Spaces are removed from the volume
                    String drinkVolume = userInput.trim();
                    //Then converted as a double value to be then used in the user added drink setter.
                    double drinkVolumeDuble = Double.parseDouble(drinkVolume);
                    //Sets the drink volume.
                    tea.setDrinkVolume(drinkVolumeDuble);
                    //if the expression doesn't match the pattern then error is thrown and volume is set to 0.00
                } else if (!userInput.matches("[1-9]{2,4}") | !userInput.matches("[0-9]{2,4}")) {
                    teadrinkVolume.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    tea.setDrinkVolume(0.00);

                }
            }
        });
        //Returns the drink volume.
        return tea.getDrinkVolume();
    }



    /**
     * Calculates the tea strength
     * Reference for caffeine in tea:https://www.caffeineinformer.com/caffeine-content/tea-brewed
     * Reference for caffeine in tea:http://www.pkdiet.com/pdf/Caffeine%20BrewedTeas.pdf

     * @param view Refers to current view.
     * @return tea Strength
     */
    public double onCheckTeaStrengthDialog(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.one_minitue:

                if (checked) {
                    double teaStrength = 14.00;
                    //If one  is selected then caffeine is set to 14.00mg
                    setteastrenght(teaStrength);

                }

                break;
            case R.id.three_minitue:
                if (checked) {
                    //If three  is selected then caffeine is set to 22.00mg
                    double teaStrength = 22.00;
                    setteastrenght(teaStrength);

                }

                break;
            case R.id.five_minitue:
                if (checked) {
                    //If five  is selected then caffeine is set to 25.00mg
                    double teaStrength = 25.00;
                    setteastrenght(teaStrength);


                }

                break;

        }
        //Returns the tea strength
        return getTeaStrength();
    }

    /**
     * Checks to see if input from all fields is valid.
     * If input is valid then it's added to the database.
     * @param view refers to the current view.
     */
    public void submitTeaDrink(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        //If the fields are valid
        if (validateDrinkNameField() != ("not valid") && validateDrinkVolume() != 0.00) {

            double teaContent = getTeaStrength();
            helper.createToastWithText("Tea caffeine content value" + teaContent);
            if (teaContent == 14.0 | teaContent == 22.0 | teaContent == 25.0) {
                helper.createToastWithText("Valid input");
                //Returns the corresponding image related to type.
                int image = helper.returnImage("Tea", getApplicationContext());
                //Inserts record into Db.
                queryHelper.insertIntoDBImage(getApplicationContext(), validateDrinkNameField()+" caffeine:"+ getTeaStrength()+"mg", "Tea", validateDrinkVolume(), getTeaStrength(), image);

                finish();

            }

            //if the values haven't been set via the checkbox, name or volume then toast error is thrown.
        } else if (validateDrinkNameField() == ("not valid") && tea.getCaffineContent() != 14.0 | tea.getCaffineContent() != 22.00 | tea.getCaffineContent() != 25.0 && validateDrinkVolume() == 0.00) {

            helper.createToastWithText("Please review the values entered");
        } else {
            helper.createToastWithText("Please review the values entered");

        }
    }
}

