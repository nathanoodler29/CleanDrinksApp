package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Weight_and_Height_Activity extends AppCompatActivity {
    private EditText weightEditText;
    private EditText footEditText;
    private EditText inchesEditText;

    public int getInchesField() {
        return inchesField;
    }

    public void setInchesField(int inchesField) {
        this.inchesField = inchesField;
    }

    private int inchesField;

    public int getFootField() {
        return footField;
    }

    public void setFootField(int footField) {
        this.footField = footField;
    }

    private int footField;


    private Spinner heightSpinner;

    public int getValidWeightField() {
        return validWeightField;
    }

    public void setValidWeightField(int validWeightField) {
        this.validWeightField = validWeightField;
    }

    private int validWeightField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_and__height_);
        weightEditText = (EditText) findViewById(R.id.weight_edit_text);
        footEditText = (EditText) findViewById(R.id.editFoot);
//        inchesEditText = (EditText) findViewById(R.id.editInches);
        validateWeightField();
        validateFootField();


    }


    public int validateWeightField(){
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

                } else if (!weightInput.matches("[0-9]{2,3}")) {
                    weightEditText.setError("Weight should only be 2 to 3 numbers");
                    setValidWeightField(0);



                }else if(weightInput.matches("^[a-z][A-Z]$")){
                    createToastWithText("Shouldn't contain letters");
                    setValidWeightField(0);
                }else if(weightInput.contains(".")){
                    weightEditText.setError("Please don't use decimal numbers.");
                    setValidWeightField(0);
                }


            }
        });


        return getValidWeightField();
    }

    public int validateFootField(){
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

                } else if (footInput.matches("[1-7](.)0[1-9]") |footInput.matches("[1-7](.)1[011]")){
                    createToastWithText("Correct foot number");
                    setFootField(1);

                } else if(!footInput.matches("[1-7](.)0[1-9]") |!footInput.matches("[1-7](.)1[011]")){
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

//

    public void moveToDrinksCategory(View view){
        int sum = getValidWeightField() + getFootField();
        if(sum==2){
            Intent changeToDrinksCategoryPage = new Intent(this, DrinkCategory.class);
            //Switches the activity to sign up.
            startActivity(changeToDrinksCategoryPage);
        }else{
            createToastWithText("Please ensure all weight and height inputs are filled");
        }

    }





}
