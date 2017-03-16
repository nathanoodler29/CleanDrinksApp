package coffee.prototype.android.cleandrinksapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

                if (weightInput.isEmpty()) {
                    weightEditText.setError("Please don't leave blank ");
                } else if (weightInput.contains("*") | weightInput.contains("\0") | weightInput.contains("\'")
                        | weightInput.contains("\0")
                        | weightInput.contains("\"") | weightInput.contains("\b") | weightInput.contains("\n")
                        | weightInput.contains("\r") | weightInput.contains("\t") | weightInput.contains("\t")
                        | weightInput.contains("\\") | weightInput.contains("%")) {
                    weightEditText.setError("Special characters can't be used");

                } else if (weightInput.matches("[0-9]{2,3}")) {
                    createToastWithText("Valid weight ");
                    setValidWeightField(1);
                    int weightFieldValue = Integer.parseInt(weightInput.trim());
                    setWeightValue(weightFieldValue);

                } else if (!weightInput.matches("[0-9]{2,3}")) {
                    weightEditText.setError("Weight should only be 2 to 3 numbers");
                    setValidWeightField(0);


                } else if (weightInput.matches("^[a-z][A-Z]$")) {
                    createToastWithText("Shouldn't contain letters");
                    setValidWeightField(0);
                } else if (weightInput.contains(".")) {
                    weightEditText.setError("Please don't use decimal numbers.");
                    setValidWeightField(0);
                }


            }
        });


        return getValidWeightField();
    }

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

                if (footInput.isEmpty()) {
                    footEditText.setError("Please don't leave blank ");
                } else if (footInput.contains("*") | footInput.contains("\0") | footInput.contains("\'")
                        | footInput.contains("\0")
                        | footInput.contains("\"") | footInput.contains("\b") | footInput.contains("\n")
                        | footInput.contains("\r") | footInput.contains("\t") | footInput.contains("\t")
                        | footInput.contains("\\") | footInput.contains("%")) {
                    footEditText.setError("Special characters can't be used");
                    setFootField(0);

                } else if (footInput.matches("[1-7](.)0[1-9]") | footInput.matches("[1-7](.)1[011]")) {
                    createToastWithText("Correct foot number");
                    setFootField(1);
                    double heightValue = Double.parseDouble(footInput.trim());
                    setHeightValue(heightValue);

                } else if (!footInput.matches("[1-7](.)0[1-9]") | !footInput.matches("[1-7](.)1[011]")) {
                    footEditText.setError("Inches value should only be between 0-11");
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


    public void moveToDrinksCategory(View view) {
        int sum = getValidWeightField() + getFootField();
        int weightValue = getWeightValue();
        double heightValue = getHeightValue();

        if (sum == 2) {
            addWeightToDB(weightValue,heightValue);
            checkIfWeightDataHasBeenPopulated();
            finish();

            Intent changeToDrinksCategoryPage = new Intent(this, DrinkCategory.class);
            //Switches the activity to sign up.
            startActivity(changeToDrinksCategoryPage);
        } else {
            createToastWithText("Please ensure all weight and height inputs are filled");
        }

    }


    private void addWeightToDB(int weight, double height){
        SQLiteDatabase db = weightDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(WeightEntry.COLUMN_WEIGHT,weight);
        contentValues.put(WeightEntry.COLUMN_HEIGHT,height);

        long newRowId = db.insert(WeightEntry.TABLE_NAME, null, contentValues);
        //Log cat used to show that a database insertion is occuring.
        Log.v("Weight up activity", "new row id" + newRowId);


        db.close();

    }

//
    private void checkIfWeightDataHasBeenPopulated() {
        SQLiteDatabase db = weightDBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + WeightEntry.TABLE_NAME, null);
       createToastWithText(DatabaseUtils.dumpCursorToString(cursor));
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }




    }
