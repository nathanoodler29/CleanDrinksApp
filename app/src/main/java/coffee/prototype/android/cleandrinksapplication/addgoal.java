package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.Model.TimeHandler;
import coffee.prototype.android.cleandrinksapplication.Model.Water;


public class addgoal extends AppCompatActivity {

    private Goal goal = new Goal();
    private Water water = new Water();
    private ActivityHelper activityHelper = new ActivityHelper();
    private EditText numOfLitres;
    private Button submitGoal;
    private Button cancelGoal;

    private EditText startTime;
    private EditText endTime;
    private TimeHandler timeHandler = new TimeHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgoal);
        numOfLitres = (EditText) findViewById(R.id.numberOfLitres);
        submitGoal = (Button) findViewById(R.id.submitgoal);
        cancelGoal = (Button) findViewById(R.id.canceladdgoal);

        startTime = (EditText) findViewById(R.id.start_time_field);

        endTime = (EditText) findViewById(R.id.end_time_field);


        waterLitresField();
        startTimeFiledValidation();
        endTimeFiledValidation();
        validateFields();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.current_menu, menu);
        return true;
    }


    ///https://developer.android.com/guide/topics/ui/controls/radiobutton.html
    public int onCheckboxClickedDialog(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int exersizeLevel;
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.low_radioButton:

                if (checked) {
                    exersizeLevel = 1;
                    goal.setExerciseLevels(exersizeLevel);

                }
                break;
            case R.id.moderate_radioButton:
                if (checked) {
                    exersizeLevel = 2;
                    goal.setExerciseLevels(exersizeLevel);


                }

                break;
            case R.id.high_radioButton:
                if (checked) {
                    exersizeLevel = 3;
                    goal.setExerciseLevels(exersizeLevel);


                }

                break;
        }
        createToastWithText("Exercise levels" + goal.getExerciseLevels());
        double value = goal.setExpectedWaterGoal(water.calculateTotalWater(activityHelper.getWeightFromSesh(getApplicationContext()), goal.getExerciseLevels()));

        createToastWithText("Weight " + activityHelper.getWeightFromSesh(getApplicationContext()));

        createToastWithText("Value from water calucaltion" + value);

        goal.setExpectedWaterGoal(value);


        return goal.getExerciseLevels();
    }


    public double waterLitresField() {


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


                if (userInput.isEmpty()) {
                    numOfLitres.setError("Please don't leave blank");
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {

                    numOfLitres.setError("Special characters can't be used");

                } else if (userInput.matches("0(.)[2-9][0-9]") | userInput.matches("[1-9](.)[0-9][0-9]")) {
                    String waterGoal = userInput.trim();
                    double userWaterGoal = Double.parseDouble(waterGoal);

                    if (userWaterGoal >= goal.getExpectedWaterGoal()) {
                        numOfLitres.setError("Water goal is too large, as it should be equal or less than" + goal.getExpectedWaterGoal());
                    } else if (userWaterGoal <= goal.getExpectedWaterGoal()) {
                        createToastWithText("Valid Water Goal!");
                        goal.setWaterGoal(userWaterGoal);
                    }

                } else if (!userInput.matches("0(.)[2-9][0-9]") | !userInput.matches("[1-9](.)[0-9][0-9]")) {
                    numOfLitres.setError("Invalid water measurement");
                }
            }
        });
        return goal.getWaterGoal();
    }


    public String startTimeFiledValidation() {


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

                if (input.isEmpty()) {
                    startTime.setError("Please don't leave blank");
                } else if (input.contains("*") | input.contains("\0") | input.contains("\'")
                        | input.contains("\0")
                        | input.contains("\"") | input.contains("\b") | input.contains("\n")
                        | input.contains("\r") | input.contains("\t") | input.contains("\t")
                        | input.contains("\\") | input.contains("%")) {

                    startTime.setError("Special characters can't be used");

                } else if (input.matches("^[0-9][0-9]:[0-6][0-9]")) {
                    String time = input.trim();


                    goal.setStartTimeGoal(time);


                } else if (input.matches("now")) {
                    String currentTime = timeHandler.getHourAndMin();
                    startTime.setText(currentTime);

                    String currentTimeSpace = currentTime.trim();
                    createToastWithText("current time variable" + currentTimeSpace);
                    goal.setStartTimeGoal(currentTimeSpace);


                } else if (input.equals("now") && input.matches("^[0-9][0-9]:[0-6][0-9]")) {
                    startTime.setError("Please don't use 'now' and type your on time at once");
                    goal.setStartTimeGoal("invalid");
                } else if (!input.matches("^[0-9][0-9]:[0-6][0-9]")) {
                    startTime.setError("Not a valid time ");
                    goal.setStartTimeGoal("invalid");

                }


            }
        });

        createToastWithText(goal.getStartTimeGoal());
        return goal.getStartTimeGoal();
    }

    public String endTimeFiledValidation() {


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
                String endTimeInput = endTime.getText().toString();

                if (endTimeInput.isEmpty()) {
                    endTime.setError("Please don't leave blank");
                } else if (endTimeInput.contains("*") | endTimeInput.contains("\0") | endTimeInput.contains("\'")
                        | endTimeInput.contains("\0")
                        | endTimeInput.contains("\"") | endTimeInput.contains("\b") | endTimeInput.contains("\n")
                        | endTimeInput.contains("\r") | endTimeInput.contains("\t") | endTimeInput.contains("\t")
                        | endTimeInput.contains("\\") | endTimeInput.contains("%")) {

                    endTime.setError("Special characters can't be used");

                } else if (endTimeInput.matches("^[0-9][0-9]:[0-6][0-9]")) {

                    String time = endTimeInput.trim();

                    goal.setEndTimeGoal(time);
                    createToastWithText("date " + timeHandler.getYearAndMonth());
                    createToastWithText("Valid time");

                } else if (!endTimeInput.matches("^[0-9][0-9]:[0-6][0-9]") | endTimeInput.equals("now") | endTimeInput.matches("[a-z][A-Z]")) {
                    endTime.setError("Not a valid time ");
                    goal.setEndTimeGoal("invalid");

                } else if (endTimeInput.equals("now") | endTimeInput.matches("[a-z][A-Z]")) {

                    endTime.setError("Not a valid time ");
                    goal.setEndTimeGoal("invalid");

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


    public void validateFields() {


        submitGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);


                if (goal.getWaterGoal() == 0.00) {
                    createToastWithText("Please ensure you've set a valid water goal");
                } else if (goal.getStartTimeGoal().equals("invalid")) {
                    createToastWithText("Start time is  not valid!");

                } else if (goal.getEndTimeGoal().equals("invalid")) {
                    createToastWithText("End time is  not valid!");


                } else if (goal.getWaterGoal() > 0.00 && goal.getStartTimeGoal() != null && goal.getEndTimeGoal() != null && timeHandler.validateDates(goal.getStartTimeGoal(), goal.getEndTimeGoal())) {
                    createToastWithText("Water goal is valid and times are valid:)");
                    //make it so you can ust do goal new(get,get,get,get);

                    Context context = getApplicationContext();
                    String userID = String.valueOf(activityHelper.getUserId(context));
                    activityHelper.addGoalToGoalTable(context, userID, goal.getWaterGoal(), goal.getStartTimeGoal(), goal.getEndTimeGoal());

                    Intent changeToGoalMainPage = new Intent(getApplicationContext(), GoalActivity.class);
                    //Switches the activity to sign up.
                    startActivity(changeToGoalMainPage);
//                    ad.setAdatper(goals);

//                    activityHelper.checkIfDataHasBeenAddedToDb(getApplicationContext());
                    //CAN ADD A CHECK TO SEE IF IT'S POPUALTED?
//                    activityHelper.checkIfDataHasBeenAddedToDb(context);

//                    activityHelper.addGoalToGoalProgressTable(context);
//                    activityHelper.checkIfDataHasBeenAddedToDbGoalProgres(context);

                    //    public void addGoalToGoalTable(Context context,String userID, double waterGoal, String startTime, String endTime){


                    if (timeHandler.validateDates(goal.getStartTimeGoal(), goal.getEndTimeGoal())) {
                        createToastWithText("Dates are valid:)");

                    } else if (!timeHandler.validateDates(goal.getStartTimeGoal(), goal.getEndTimeGoal())) {
                        createToastWithText("Dates aren't valid please, ensure times are at least an hour apart");
                    }

                    // do the time check in here, for the between, otherwise if stirngs are null then it'll throw exception

                } else if (goal.getStartTimeGoal().equals("invalid") && goal.getEndTimeGoal().equals("invalid") && goal.getWaterGoal() == 0.00) {
                    createToastWithText("Please fill out all fields, before trying to add a goal.");
                }

            }
        });
    }

    public void cancelButton(View view) {
        cancelGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent changeToGoalMainPage = new Intent(getApplicationContext(), GoalActivity.class);
                //Switches the activity to sign up.
                startActivity(changeToGoalMainPage);
            }
        });
    }



}




