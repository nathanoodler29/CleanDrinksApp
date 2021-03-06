package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import coffee.prototype.android.cleandrinksapplication.data.UsersDBHelper;
import coffee.prototype.android.cleandrinksapplication.data.WeightContract.WeightEntry;

public class Weight_and_Height_Activity extends AppCompatActivity {
    private EditText weightEditText;
    private EditText footEditText;

    private int weightValue;


    private double heightValue;

    private int footField;

    private int validWeightField;
    private UsersDBHelper weightDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_and__height_);
        weightEditText = (EditText) findViewById(R.id.weight_edit_text);
        footEditText = (EditText) findViewById(R.id.editFoot);
        validateWeightField();
        validateFootField();
        weightDBHelper = new UsersDBHelper(this);


    }


    public int getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(int weightValue) {
        this.weightValue = weightValue;
    }

    public double getHeightValue() {
        return heightValue;
    }

    public void setHeightValue(double heightValue) {
        this.heightValue = heightValue;
    }


    public int getValidWeightField() {
        return validWeightField;
    }

    public void setValidWeightField(int validWeightField) {
        this.validWeightField = validWeightField;
    }


    public int getFootField() {
        return footField;
    }

    public void setFootField(int footField) {
        this.footField = footField;
    }

    /**
     * Validates a user weight
     *
     * @return Returns the user weight
     */
    public int validateWeightField() {
        setValidWeightField(0);
        weightEditText.addTextChangedListener(new TextWatcher() {

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
                String weightInput = weightEditText.getText().toString();
                //checks if input is empty.
                if (weightInput.isEmpty()) {
                    weightEditText.setError("Please don't leave blank ");
                } else if (weightInput.contains("*") | weightInput.contains("\0") | weightInput.contains("\'")
                        | weightInput.contains("\0")
                        | weightInput.contains("\"") | weightInput.contains("\b") | weightInput.contains("\n")
                        | weightInput.contains("\r") | weightInput.contains("\t") | weightInput.contains("\t")
                        | weightInput.contains("\\") | weightInput.contains("%")) {
                    weightEditText.setError("Special characters can't be used");
                    //Checks a user weight is between 2 and 3 digits
                } else if (weightInput.matches("[0-9]{2,3}")) {
                    createToastWithText("Valid weight ");
                    setValidWeightField(1);
                    int weightFieldValue = Integer.parseInt(weightInput.trim());
                    //sets the weight value
                    setWeightValue(weightFieldValue);
                    //If input doesn't match dd or ddd then error is thrown.
                } else if (!weightInput.matches("[0-9]{2,3}")) {
                    weightEditText.setError("Weight should only be 2 to 3 numbers");
                    setValidWeightField(0);


                } else if (weightInput.matches("^[a-z][A-Z]$")) {
                    createToastWithText("Shouldn't contain letters, format should be 50 as an example.");
                    setValidWeightField(0);
                } else if (weightInput.contains(".")) {
                    //if errors are present then value is set to zero.
                    weightEditText.setError("Please don't use decimal numbers.");
                    setValidWeightField(0);
                }


            }
        });


        return getValidWeightField();
    }

    /**
     * Validates the height vlaue
     *
     * @return The height value.
     */
    public int validateFootField() {
        setFootField(0);
        footEditText.addTextChangedListener(new TextWatcher() {

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
                String footInput = footEditText.getText().toString();
                //checks the field isn't empty.
                if (footInput.isEmpty()) {
                    //if the field is empty sets error.
                    footEditText.setError("Please don't leave blank ");
                } else if (footInput.contains("*") | footInput.contains("\0") | footInput.contains("\'")
                        | footInput.contains("\0")
                        | footInput.contains("\"") | footInput.contains("\b") | footInput.contains("\n")
                        | footInput.contains("\r") | footInput.contains("\t") | footInput.contains("\t")
                        | footInput.contains("\\") | footInput.contains("%")) {
                    footEditText.setError("Special characters can't be used");
                    setFootField(0);
                    //Checks that input includes at least 1 and allows 1.09, 1.11, and stops a user from entering a value over 7 foot
                } else if (footInput.matches("[1-7](.)0[1-9]") | footInput.matches("[1-7](.)1[011]") | footInput.matches("[1-7](.)0[00]")) {
                    //provides toast user feedback that the foot number is correct
                    createToastWithText("Correct Height");
                    //suggests correct valid input.
                    setFootField(1);
                    //sets the value for the foot and inches
                    double heightValue = Double.parseDouble(footInput.trim());
                    //sets the value for the foot and inches
                    setHeightValue(heightValue);
                    //If this isn't matched then error is thrown
                } else if (!footInput.matches("[1-7](.)0[1-9]") | !footInput.matches("[1-7](.)1[011]") | footInput.matches("[1-7](.)0[00]")) {
                    footEditText.setError("Inches value should only be between 0-11 and value should be like this 5.11");
                    setFootField(0);


                }


            }
        });

        return getValidWeightField();
    }


    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }

    /**
     * Validates that all fields have been entered.
     *
     * @param view
     */
    public void moveToDrinksCategory(View view) {
        int sum = getValidWeightField() + getFootField();
        int weightValue = getWeightValue();
        double heightValue = getHeightValue();
        //if the fields equal 2 then it's validated
        if (sum == 2) {
            //Adds the weight and height into the weight table.
            addWeightToDB(weightValue, heightValue);
            //checks if value has been populated.
            checkIfWeightDataHasBeenPopulated();
            //finishes this current activity
            finish();
            //Changes to the drinks category activity.
            Intent changeToDrinksCategoryPage = new Intent(this, DrinkCategory.class);
            //Switches the activity to drinks category.
            startActivity(changeToDrinksCategoryPage);
        } else {
            //error is thrown if the two fields haven't got a valid field.
            createToastWithText("Please ensure all weight and height inputs are filled");
        }

    }

    /**
     * Adds the weight to the databse
     *
     * @param weight Users weight
     * @param height Users height
     */
    private void addWeightToDB(int weight, double height) {
        //makes db writable
        SQLiteDatabase db = weightDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //adds the user weight in the weight column
        contentValues.put(WeightEntry.COLUMN_WEIGHT, weight);
        //adds the user height in the height column
        contentValues.put(WeightEntry.COLUMN_HEIGHT, height);
        //inserts into the table.
        long newRowId = db.insert(WeightEntry.TABLE_NAME, null, contentValues);
        //Log cat used to show that a database insertion is occuring.
        Log.v("Weight up activity", "new row id" + newRowId);
        //closes the db.
        db.close();

    }

    /**
     * Checks if the weight has been added for the user.
     */
    private void checkIfWeightDataHasBeenPopulated() {
        SQLiteDatabase db = weightDBHelper.getReadableDatabase();
        //displays all weights inthe table, this was mostly used for debugging
        Cursor cursor = db.rawQuery("SELECT * FROM " + WeightEntry.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }


}
