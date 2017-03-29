package coffee.prototype.android.cleandrinksapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.Model.Water;


public class GoalActivity extends AppCompatActivity {


    private Button addgoalbutton;


    private EditText numOfLitres;

    private  EditText startTime;

    private  EditText endTime;

    private Goal goal = new Goal();

    private Water water = new Water();





    private int seekBarValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        //Creates a pop up dialog when a user clicks add goal.


        createGoalPrompt();
    }


    public void createGoalPrompt() {
        addgoalbutton = (Button) findViewById(R.id.adddrinkgoal);

        addgoalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reference to http://www.mkyong.com/android/android-custom-dialog-example/
                final Dialog dialog = new Dialog(GoalActivity.this);

                dialog.setContentView(R.layout.add_goal_dialog);


                numOfLitres = (EditText) dialog.findViewById(R.id.numberOfLitres);

                startTime = (EditText) dialog.findViewById(R.id.start_time_field);

                endTime = (EditText) dialog.findViewById(R.id.end_time_field);

                waterLitresField();

                startTimeFiledValidation();

                endTimeFiledValidation();

//
//                EditText endTimeDialog = (EditText) dialog.findViewById(R.id.endtime);

                Button dialogCloseButton = (Button) dialog.findViewById(R.id.canceldialog);


                dialogCloseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });

    }


    ///https://developer.android.com/guide/topics/ui/controls/radiobutton.html
    public int onCheckboxClickedDialog(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int exersizeLevel = 0;
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.low_radioButton:

                if (checked){
                    exersizeLevel = 1;
                    goal.setExerciseLevels(exersizeLevel);

                }
                break;
            case R.id.moderate_radioButton:
                if (checked){
                    exersizeLevel=2;
                    goal.setExerciseLevels(exersizeLevel);


                }

                break;
            case R.id.high_radioButton:
                if (checked){
                    exersizeLevel=3;
                    goal.setExerciseLevels(exersizeLevel);


                }

                break;
        }
        createToastWithText("Exercise levels"+goal.getExerciseLevels());
        return goal.getExerciseLevels();
    }


    public String waterLitresField(){




        EditText numberOfLitres = (EditText) findViewById(R.id.numberOfLitres);

        numOfLitres.addTextChangedListener(new TextWatcher() {

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
                    String userInput = numOfLitres.getText().toString();
                    int checkNumberEmail = 0;

                    if (userInput.isEmpty()) {
                        numOfLitres.setError("Please don't leave blank");
                    } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                            | userInput.contains("\0")
                            | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                            | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                            | userInput.contains("\\") | userInput.contains("%")) {

                        numOfLitres.setError("Special characters can't be used");

//                Regex from Google regex checker
                    }
                    if (userInput.matches("0(.)[2-9][0-9]") | userInput.matches("[1-5](.)[0-9][0-9]")) {
//                        createToastWithText("actual calucaltion"+water.calculateTotalWater(45,goal.getExerciseLevels()));
                        String waterGoal = userInput.trim();
                        goal.setWaterGoal(waterGoal);
                        createToastWithText(goal.getWaterGoal());

                        createToastWithText("Valid water measurement in litres");


                    } else if (!userInput.matches("0(.)[2-9][0-9]") | !userInput.matches("[1-5](.)[0-9][0-9]")) {
                        numOfLitres.setError("Invalid water measurement");
                    }
                }
            });
            createToastWithText("get goal water "+goal.getWaterGoal());
//            return goal.getWaterGoal();
        return goal.getWaterGoal();
    }


        public String startTimeFiledValidation(){

            EditText startTextField = (EditText) findViewById(R.id.start_time_field);

            startTime.addTextChangedListener(new TextWatcher() {

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
                    String input = startTime.getText().toString();
                    int checkNumberEmail = 0;

                    if (input.isEmpty()) {
                        startTime.setError("Please don't leave blank");
                    } else if (input.contains("*") | input.contains("\0") | input.contains("\'")
                            | input.contains("\0")
                            | input.contains("\"") | input.contains("\b") | input.contains("\n")
                            | input.contains("\r") | input.contains("\t") | input.contains("\t")
                            | input.contains("\\") | input.contains("%")) {

                        startTime.setError("Special characters can't be used");

//                Regex from Google regex checker
                    }
                    if (input.equals("now")) {
                   final String time =  getTimeHourAndMin();
                        goal.setStartTimeGoal(time);

                    startTime.setText(getTimeHourAndMin());

                    createToastWithText("Valid time");


                    }else if(input.matches("^[0-9][0-9]:[0-9][0-9]")){
                        createToastWithText("Valid time");

                    }else if(!input.matches("^[0-9][0-9]:[0-9][0-9]")|input.equals("now")){
                        startTime.setError("Not a valid time ");

                    }
                }
            });

            return goal.getStartTimeGoal();
        }





    public String endTimeFiledValidation(){

        EditText endTextField = (EditText) findViewById(R.id.start_time_field);

        endTime.addTextChangedListener(new TextWatcher() {

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
                String input = endTime.getText().toString();

                if (input.isEmpty()) {
                    endTime.setError("Please don't leave blank");
                } else if (input.contains("*") | input.contains("\0") | input.contains("\'")
                        | input.contains("\0")
                        | input.contains("\"") | input.contains("\b") | input.contains("\n")
                        | input.contains("\r") | input.contains("\t") | input.contains("\t")
                        | input.contains("\\") | input.contains("%")) {

                    endTime.setError("Special characters can't be used");

//                Regex from Google regex checker
                } else if(input.matches("^[0-9][0-9]:[0-9][0-9]")){
                    String endtime =  input.trim();
                    createToastWithText("Valid time");
                    goal.setEndTimeGoal(endtime);

                }else if(!input.matches("^[0-9][0-9]:[0-9][0-9]")| !input.equals("now") |!input.equals(getTimeHourAndMin())){
                    endTime.setError("Not a valid time ");
                    //need a less than the current time too.

                }
            }
        });

        return goal.getEndTimeGoal();
    }





    public void createToastWithText(String toastText) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastText, duration);
        toast.show();
    }


    //need to add in a helper class or a date handler class
    public String getTimeHourAndMin(){


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");

        String dateString= simpleDateFormat.format(new Date());

        return dateString;

    }

    public String getDayMonthAndYear(){


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

        String dateString= simpleDateFormat.format(new Date());

        return dateString;

    }


    public boolean compareTwoDates(){
        return true;
    }

}

