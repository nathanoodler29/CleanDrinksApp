package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import coffee.prototype.android.cleandrinksapplication.Model.UserAddedAlcholicDrink;
import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.MainAdapter;
import coffee.prototype.android.cleandrinksapplication.data.DBQueryHelper;



public class AddAlcholicDrink extends AppCompatActivity {

    private EditText alcoholDrinkName;
    private EditText alcoholDrinkVolume;
    private EditText alcoholDrinkType;
    private EditText alcoholPercentage;
    private Button cancelButton;
    private DBQueryHelper queryHelper = new DBQueryHelper();


    private MainAdapter mAdapter;
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


        validateDrinkNameField();
        validateDrinkVolume();
        validateAlcoholDrinkType();
        validateDrinkPercentage();


    }

    public String validateDrinkNameField() {


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


                if (userInput.isEmpty()) {
                    alcoholDrinkName.setError("Please don't leave blank");
                    userAddedDrink.setDrinkName("not valid");

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    alcoholDrinkName.setError("Special characters can't be used");
                    userAddedDrink.setDrinkName("not valid");


                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                } else if (userInput.matches("^([a-zA-Z]+ ?){4,15}")) {
                    userAddedDrink.setDrinkName(userInput);
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("^([a-zA-Z] ?){4,15}")) {
                    if (userInput.matches(".*\\d+.*")) {
                        alcoholDrinkName.setError("Numbers aren't accepted");

                    } else {
                        alcoholDrinkName.setError("Drink name, needs to be between 4 and 15 characters");
                        userAddedDrink.setDrinkName("not valid");

                    }
                } else if (userInput.matches(".*\\d+.*")) {
                    alcoholDrinkName.setError("A drink name can't contain numbers");
                    userAddedDrink.setDrinkName("not valid");

                }
            }
        });
        return userAddedDrink.getDrinkName();

    }

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


                if (userInput.isEmpty()) {
                    alcoholDrinkVolume.setError("Please don't leave blank");
                    userAddedDrink.setDrinkVolume(0.00);

                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    alcoholDrinkVolume.setError("Special characters can't be used");

                    userAddedDrink.setDrinkVolume(0.00);

                } else if (userInput.matches("[1-9]{2,4}") | userInput.matches("[0-9]{2,4}")) {
                    String drinkVolume = userInput.trim();
                    double drinkVolumeDuble = Double.parseDouble(drinkVolume);


                    userAddedDrink.setDrinkVolume(drinkVolumeDuble);

                } else if (!userInput.matches("[1-9]{2,4}") | !userInput.matches("[0-9]{2,4}")) {
                    alcoholDrinkVolume.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    userAddedDrink.setDrinkVolume(0.00);

                }
            }
        });
        return userAddedDrink.getDrinkVolume();
    }

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


                if (userInput.isEmpty()) {
                    alcoholDrinkType.setError("Please don't leave blank");
                    userAddedDrink.setDrinkType("not valid");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    alcoholDrinkType.setError("Special characters can't be used");
                    //http://stackoverflow.com/questions/15472764/regular-expression-to-allow-spaces-between-words
                    userAddedDrink.setDrinkType("not valid");
                } else if(userInput.matches("Red Wine")| userInput.matches("Lager") | userInput.matches("RealAle") |userInput.matches("Stout")
                        |userInput.matches("Gin")|userInput.matches("White Wine")
                        |userInput.matches("CraftBeer") |userInput.matches("Whiskey")|userInput.matches("Vodka")) {

                    userAddedDrink.setDrinkType(userInput);
                    helper.createToastWithText("Valid drink name!");
                } else if (!userInput.matches("Red Wine")| !userInput.matches("Lager") | !userInput.matches("RealAle") |!userInput.matches("Stout")
                        |!userInput.matches("Gin")|!userInput.matches("White Wine") |!userInput.matches("CraftBeer")|
                        !userInput.matches("Whiskey")|!userInput.matches("Vodka")) {
                    alcoholDrinkType.setError("Can only use the following types, RealAle,Whiskey,Lager,CraftBeer");



                } else if (userInput.matches(".*\\d+.*")) {
                    userAddedDrink.setDrinkType("not valid");
                    alcoholDrinkType.setError("A drink type can't contain numbers");

                }
            }
        });
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


                if (userInput.isEmpty()) {
                    alcoholPercentage.setError("Please don't leave blank");
                    userAddedDrink.setAlcoholPercentage(0.00);
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    alcoholPercentage.setError("Special characters can't be used");

                    userAddedDrink.setAlcoholPercentage(0.00);

                } else if (userInput.matches("^[0-9][.][0-9][0-9]") | userInput.matches("^[0-9][0-9][.][0-9][0-9]")) {
                    String drinkVolume = userInput.trim();
                    double drinkVolumeDuble = Double.parseDouble(drinkVolume);

                    userAddedDrink.setAlcoholPercentage(drinkVolumeDuble);

                } else if (!userInput.matches("^[0-9][.][0-9]") | !userInput.matches("[0-9][0-9][.]ow t[0-9][0-9]")) {
                    alcoholPercentage.setError("Invalid drink volume, should be between 2 and 4 digits ");
                    userAddedDrink.setAlcoholPercentage(0.00);

                }
            }
        });
        return userAddedDrink.getAlcoholPercentage();

    }


    public void cancelAloholicButton(View view) {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
                finish();
            }
        });
    }

    public void submitAddAlcholicDrink(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        if (validateDrinkNameField() != ("not valid") && validateAlcoholDrinkType() != ("not valid") && validateDrinkVolume() != 0.00 && validateDrinkPercentage() != 0.00) {
            helper.createToastWithText("Valid input");
            Intent changeToEspressoListing = new Intent(this, DrinksCategoryAlcoholTypes.class);

            helper.createToastWithText("Units for this drink are: "+userAddedDrink.calculateUnitTotal(validateDrinkVolume(),validateDrinkPercentage()));
            double userUnits = userAddedDrink.calculateUnitTotal(validateDrinkVolume(),validateDrinkPercentage());

            int value = returnImage(validateAlcoholDrinkType(),getApplicationContext());

            queryHelper.insertIntoDBForImage(getApplicationContext(),validateDrinkNameField()+" "+userUnits+" units",validateAlcoholDrinkType(),validateDrinkVolume(),userUnits,value);



            finish();
        } else if(validateDrinkNameField()==("not valid") && validateAlcoholDrinkType()==("not valid")  && validateDrinkVolume() == 0.00 && validateDrinkPercentage() != 0.00){

            helper.createToastWithText("Please review the values entered");
        }else{
            helper.createToastWithText("Please review the values entered");

        }




    }

    public int returnImage(String drinkType,Context context){
        int defaultImage = 1;

        //change to swithc?
        if(drinkType.equals("Red Wine")){
            defaultImage = context.getResources().getIdentifier("red_wine_item", "drawable", context.getPackageName());

        }else if(drinkType.equals("Coffee")){
            defaultImage = context.getResources().getIdentifier("caffine_cup", "drawable", context.getPackageName());

        }else if(drinkType.equals("Gin")){
            defaultImage = context.getResources().getIdentifier("vodka", "drawable", context.getPackageName());

        }else if(drinkType.equals("Lager")){
            defaultImage = context.getResources().getIdentifier("beerbottle", "drawable", context.getPackageName());

        }else if(drinkType.equals("RealAle")){
            defaultImage = context.getResources().getIdentifier("real_ale_listing", "drawable", context.getPackageName());

        }else if(drinkType.equals("Stout")){
            defaultImage = context.getResources().getIdentifier("stout_listing", "drawable", context.getPackageName());

        }else if(drinkType.equals("Vodka")){
            defaultImage = context.getResources().getIdentifier("gin", "drawable", context.getPackageName());

        }else if(drinkType.equals("Whiskey")){
            defaultImage = context.getResources().getIdentifier("whiskey_listing", "drawable", context.getPackageName());

        }else if(drinkType.equals("White Wine")){
            defaultImage = context.getResources().getIdentifier("white_wine_item", "drawable", context.getPackageName());

        }else if(drinkType.equals("CraftBeer")){
            defaultImage = context.getResources().getIdentifier("craft_beer_bottle", "drawable", context.getPackageName());

        }else if(drinkType.equals("Tea")){
            defaultImage = context.getResources().getIdentifier("decaf_tea", "drawable", context.getPackageName());

        } else{
            //need to add dfeault drink in here
            defaultImage = context.getResources().getIdentifier("wine_bottle", "drawable", context.getPackageName());

        }
        return defaultImage;
    }


}

