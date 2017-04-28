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

    public double getTeaStrenght() {
        return teaStrenght;
    }

    public void setTeaStrenght(double teaStrenght) {
        this.teaStrenght = teaStrenght;
    }

    private double teaStrenght;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tea_based_drink);
        teaDrinKName = (EditText) findViewById(R.id.tea_drink_name);
        teadrinkVolume = (EditText) findViewById(R.id.tea_drink_volume);
        validateDrinkNameField();
        validateDrinkVolume();


    }

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
                    teaDrinKName.setError("Please don't leave blank");
                    tea.setDrinkName("not valid");

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    teaDrinKName.setError("Special characters can't be used");
                    tea.setDrinkName("not valid");


                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,20}")) {
                    tea.setDrinkName(userInput);
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("^([a-zA-Z] ?){4,20}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        teaDrinKName.setError("Numbers aren't accepted");

                    } else {
                        teaDrinKName.setError("Drink name, needs to be between 4 and 20 characters");
                        tea.setDrinkName("not valid");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    teaDrinKName.setError("A drink name can't contain numbers");
                    tea.setDrinkName("not valid");

                }
            }
        });
        return tea.getDrinkName();

    }


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

                    teadrinkVolume.setError("Special characters can't be used");

                    tea.setDrinkVolume(0.00);

                } else if (userInput.matches("[1-9]{2,4}") | userInput.matches("[0-9]{2,4}")) {
                    String drinkVolume = userInput.trim();
                    double drinkVolumeDuble = Double.parseDouble(drinkVolume);


                    tea.setDrinkVolume(drinkVolumeDuble);

                } else if (!userInput.matches("[1-9]{2,4}") | !userInput.matches("[0-9]{2,4}")) {
                    teadrinkVolume.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    tea.setDrinkVolume(0.00);

                }
            }
        });
        return tea.getDrinkVolume();
    }

    //https://www.caffeineinformer.com/caffeine-content/tea-brewed
    //http://www.pkdiet.com/pdf/Caffeine%20BrewedTeas.pdf
    public double onCheckTeaStrengthDialog(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.one_minitue:

                if (checked) {
                    double teaStrength = 14.00;
                    setTeaStrenght(teaStrength);

                }

                break;
            case R.id.three_minitue:
                if (checked) {

                    double teaStrength = 22.00;
                    setTeaStrenght(teaStrength);

                }

                break;
            case R.id.five_minitue:
                if (checked) {
                    double teaStrength = 25.00;
                    setTeaStrenght(teaStrength);


                }

                break;

        }

        return getTeaStrenght();
    }


    public void submitTeaDrink(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        if (validateDrinkNameField() != ("not valid") && validateDrinkVolume() != 0.00) {

            double teaContent = getTeaStrenght();
            helper.createToastWithText("Tea cntent value" + teaContent);
            if (teaContent == 14.0 | teaContent == 22.0 | teaContent == 25.0) {
                helper.createToastWithText("Valid input");
                int image = helper.returnImage("Tea", getApplicationContext());

                queryHelper.insertIntoDBForImage(getApplicationContext(), validateDrinkNameField()+" caffeine: "+getTeaStrenght(), "Tea", validateDrinkVolume(), getTeaStrenght(), image);

                finish();

            }

        } else if (validateDrinkNameField() == ("not valid") && tea.getCaffineContent() != 14.0 | tea.getCaffineContent() != 22.00 | tea.getCaffineContent() != 25.0 && validateDrinkVolume() == 0.00) {

            helper.createToastWithText("Please review the values entered");
        } else {
            helper.createToastWithText("Please review the values entered");

        }
    }
}

