package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.Model.TimeHandler;
import coffee.prototype.android.cleandrinksapplication.Model.Water;
import coffee.prototype.android.cleandrinksapplication.data.GoalDBQueries;
import coffee.prototype.android.cleandrinksapplication.protoype.android.cleandrinksapplication.notifications.NotificationBuilder;


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

    private GoalDBQueries goalQuery = new GoalDBQueries();
    private NotificationBuilder notificationBuilder = new NotificationBuilder();
    private AchievementGallery gallery = new AchievementGallery();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgoal);
        numOfLitres = (EditText) findViewById(R.id.numberOfLitres);
        submitGoal = (Button) findViewById(R.id.submitgoal);
        cancelGoal = (Button) findViewById(R.id.canceladdgoal);

        startTime = (EditText) findViewById(R.id.start_time_field);

        endTime = (EditText) findViewById(R.id.end_time_field);

        //validates the litres field.
        waterLitresField();
        //validates the start time field
        startTimeFiledValidation();
        //validates the end time field.
        endTimeFiledValidation();
        //checks all fields have been filled.
        validateFields();


    }


    /**
     * Reference https://developer.android.com/guide/topics/ui/controls/radiobutton.html
     * <p>
     * Checks to see which checkbox is clicked related to exercise.
     *
     * @param view Relates to current view.
     * @return The exercise levels.
     */
    public int onCheckboxClickedDialog(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int exersiseLevel;
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.low_radioButton:

                // Check which checkbox was clicked
                if (checked) {
                    exersiseLevel = 1;
                    //Sets value to one if low is selected
                    goal.setExerciseLevels(exersiseLevel);

                }
                break;
            case R.id.moderate_radioButton:
                if (checked) {
                    exersiseLevel = 2;
                    //Sets value to two if moderate is selected
                    goal.setExerciseLevels(exersiseLevel);


                }

                break;
            case R.id.high_radioButton:
                if (checked) {
                    exersiseLevel = 3;
                    //Sets value to three if high is selected
                    goal.setExerciseLevels(exersiseLevel);


                }

                break;
        }
        //Gets the weight of the user from the session, then calculates the recommended water level.
        double waterGoalValue = goal.setExpectedWaterGoal(water.calculateTotalWater(activityHelper.getWeightFromUserSession(getApplicationContext()), goal.getExerciseLevels()));
        //Sets the water goal.
        goal.setExpectedWaterGoal(waterGoalValue);
        //Returns the water goal value.
        return goal.getExerciseLevels();
    }

    /**
     * Checks the user a valid water goal, that isn't above their actual water goal according to weight.
     *
     * @return Water goal
     */
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

                //If the field is blank then an error is thrown
                if (userInput.isEmpty()) {
                    numOfLitres.setError("Please don't leave blank");
                    //Checks that the user doesn't try to use any special characters for SQL injection.
                } else if (userInput.contains("*") | userInput.contains("\0") | userInput.contains("\'")
                        | userInput.contains("\0")
                        | userInput.contains("\"") | userInput.contains("\b") | userInput.contains("\n")
                        | userInput.contains("\r") | userInput.contains("\t") | userInput.contains("\t")
                        | userInput.contains("\\") | userInput.contains("%")) {
                    //sets an error if special characters are used.
                    numOfLitres.setError("Special characters can't be used");
                    //Validates that litres are d.dd or 0.dd
                } else if (userInput.matches("0(.)[2-9][0-9]") | userInput.matches("[1-9](.)[0-9][0-9]")) {
                    String waterGoal = userInput.trim();
                    double userWaterGoal = Double.parseDouble(waterGoal);
                    //If the water goal set by the user is larger than their expected goal.
                    if (userWaterGoal >= goal.getExpectedWaterGoal()) {
                        //Error is thrown.
                        numOfLitres.setError("Water goal is too large, as it should be equal or less than" + goal.getExpectedWaterGoal());
                    } else if (userWaterGoal <= goal.getExpectedWaterGoal()) {
                        //If goal is less than expected or equal to then set the goal.
                        activityHelper.createToastWithText("Valid Water Goal!");
                        goal.setWaterGoal(userWaterGoal);
                    }
                    //Else throw error.
                } else if (!userInput.matches("0(.)[2-9][0-9]") | !userInput.matches("[1-9](.)[0-9][0-9]")) {
                    numOfLitres.setError("Invalid water measurement");
                }
            }
        });
        //Return the goal.
        return goal.getWaterGoal();
    }

    /**
     * Checks the user enters a valid start time
     *
     * @return Start time.
     */
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
                    //Get the current time in hour and mins (hh:mm)
                    String currentTime = timeHandler.getHourAndMin();
                    //set the text of the edit edit tet as the current time
                    startTime.setText(currentTime);
                    //remove any spacing
                    String currentTimeSpace = currentTime.trim();
                    //sets the start time with current variable.
                    goal.setStartTimeGoal(currentTimeSpace);
                } else if (input.equals("now") && input.matches("^[0-9][0-9]:[0-6][0-9]")) {
                    //Error is thrown.
                    startTime.setError("Please don't use 'now' and type your on time at once");
                    goal.setStartTimeGoal("invalid");
                } else if (!input.matches("^[0-9][0-9]:[0-6][0-9]")) {
                    //Error is thrown.
                    startTime.setError("Not a valid time ");
                    goal.setStartTimeGoal("invalid");

                }


            }
        });
        //Return the start time of a goal.
        return goal.getStartTimeGoal();
    }

    /**
     * Checks the user enters a valid end time
     *
     * @return Start end.
     */
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
                    //Has to match the format of time hh:mm
                } else if (endTimeInput.matches("^[0-9][0-9]:[0-6][0-9]")) {
                    //removes any spacing
                    String time = endTimeInput.trim();
                    //sets the end time value.
                    goal.setEndTimeGoal(time);
                    //Gets the current year and month
                    timeHandler.getYearAndMonth();
                    //Checks to see if now or any incorrect formats are prsent thne marks then invalid.
                } else if (!endTimeInput.matches("^[0-9][0-9]:[0-6][0-9]") | endTimeInput.equals("now") | endTimeInput.matches("[a-z][A-Z]")) {
                    endTime.setError("Not a valid time ");
                    goal.setEndTimeGoal("invalid");
                    //If now or any capital letters are used then this isn't a valid time.
                } else if (endTimeInput.equals("now") | endTimeInput.matches("[a-z][A-Z]")) {
                    //error is thrown.
                    endTime.setError("Not a valid time ");
                    goal.setEndTimeGoal("invalid");

                }
            }
        });
        //end goal is returned.
        return goal.getEndTimeGoal();

    }


    /**
     * Ensures that all fields have been filled correctly.
     */
    public void validateFields() {


        submitGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sets vibration.
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
                //If water goal is 0.00 then this isn't valid.
                if (goal.getWaterGoal() == 0.00) {
                    activityHelper.createToastWithText("Please ensure you've set a valid water goal");
                    //Or if the start time of goal is invalid, as this needs to be used since 'now' is the current time.
                } else if (goal.getStartTimeGoal().equals("invalid")) {
                    //error thrown via toast message.
                    activityHelper.createToastWithText("Start time is  not valid!");
                    //if end time is invalid then toast throws another error message.
                } else if (goal.getEndTimeGoal().equals("invalid")) {
                    activityHelper.createToastWithText("End time is  not valid!");
                    //Checks that a goal for the current date hasn't already been set alongside the start and time values aren't null
                } else if (goal.getWaterGoal() > 0.00 && !goalQuery.checkIfGoalHasAlreadyBeenSet(getApplicationContext()) && goal.getStartTimeGoal() != null && goal.getEndTimeGoal() != null && timeHandler.validateDates(goal.getStartTimeGoal(), goal.getEndTimeGoal())) {
                    activityHelper.createToastWithText("Water goal is valid and times are valid:)");
                    Context context = getApplicationContext();
                    //Returns the user  ID
                    String userID = String.valueOf(activityHelper.getUserId(context));
                    //Adds the validated goal into the Goal table.
                    goalQuery.addGoalToGoalTable(context, userID, goal.getWaterGoal(), goal.getStartTimeGoal(), goal.getEndTimeGoal(), timeHandler.getYearAndMonth());
                    //Intent navigates to the goal main activity.
                    Intent changeToGoalMainPage = new Intent(getApplicationContext(), GoalActivity.class);
                    //Switches the activity to goal activity
                    startActivity(changeToGoalMainPage);
                    //Sets the goal icon image, reference: http://stackoverflow.com/questions/3476430/how-to-get-a-resource-id-with-a-known-resource-name
                    int goalIcon = context.getResources().getIdentifier("goal_icon", "drawable", context.getPackageName());
                    //Creates a notification related to a goal.
                    notificationBuilder.createNotification(context, "Drink clean!", "Your Water Goal has been set: Please click to view latest Goal.", goalIcon);
                    //Gets the goal start time
                    String goalStart = goal.getStartTimeGoal();
                    //removes : from the start time
                    String timeClean = goalStart.replace(":", "");
                    //gets first char of the start time
                    String startTimeFirstHourDigit = String.valueOf(timeClean.charAt(0));
                    //gets second char of goal time
                    String startTimeSecondHourDigit = String.valueOf(timeClean.charAt(1));
                    //concatenate the two hour strings together.
                    String hourClean = startTimeFirstHourDigit + startTimeSecondHourDigit.trim();
                    //gets the mins values from the start time
                    String getHourTwo = timeClean.substring(2);
                    //Gets the goal end time
                    String endGoal = goal.getEndTimeGoal();
                    //removes : from the end time
                    String endTimeClean = endGoal.replace(":", "");
                    //gets the first char from the end time
                    String endTimeFirstHourDigit = String.valueOf(endTimeClean.charAt(0));
                    //get sthe second char from the end time
                    String endTimeSecondHourDigit = String.valueOf(endTimeClean.charAt(1));
                    //concatenate the two hour strings together.
                    String hourCleanHourEnd = endTimeFirstHourDigit + endTimeSecondHourDigit.trim();
                    //Returns min values from the string.
                    String getHourTwoEnd = endTimeClean.substring(2);
                    //Creates notifications related to the start and end times.
                    notificationBuilder.alarmManger(context, Integer.parseInt(hourClean), Integer.parseInt(getHourTwo), Integer.parseInt(hourCleanHourEnd), Integer.parseInt(getHourTwoEnd));
                    //Goes back to previous activity.
                    finish();
                    //If a user has created a goal, then an achievement should be made
                    if (goalQuery.numOfGoalsCreatedForAUser(context) == 1) {
                        //Retrieve the achievement image reference: http://stackoverflow.com/questions/3476430/how-to-get-a-resource-id-with-a-known-resource-name
                        int achievementImage = getResources().getIdentifier("achievement_activity_banner_image", "drawable", context.getPackageName());
                        //Creates the notification and sends to user screen.
                        notificationBuilder.createNotificationAchievement(getApplicationContext(), "Achievement Unlocked!", "Click to view unlocked achievement.", achievementImage);
                    }

                    //Peforms checks on dates.
                    if (timeHandler.validateDates(goal.getStartTimeGoal(), goal.getEndTimeGoal())) {
                        activityHelper.createToastWithText("Dates are valid:)");
                        //if dates don't match then dates aren't valid.
                    } else if (!timeHandler.validateDates(goal.getStartTimeGoal(), goal.getEndTimeGoal())) {
                        activityHelper.createToastWithText("Dates aren't valid");
                    }
                    //Uses == to avoid a null reference.
                } else if (goal.getStartTimeGoal() == ("invalid") && goal.getEndTimeGoal() == ("invalid") && goal.getWaterGoal() == 0.00) {
                    activityHelper.createToastWithText("Please fill out all fields, before trying to add a goal.");
                }

            }
        });
    }

    public void cancelButton(View view) {
        cancelGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Switches activity to back to previous screen.
                finish();
            }
        });
    }


}




