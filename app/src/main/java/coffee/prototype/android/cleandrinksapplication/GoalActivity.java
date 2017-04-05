package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GoalActivity extends AppCompatActivity {



    private Button addgoalbutton;
    private Button removegoalbutton;

    private ActivityHelper helper= new ActivityHelper();
    private TextView displayRecord;



    private int seekBarValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        addgoalbutton = (Button) findViewById(R.id.adddrinkgoal);
        removegoalbutton = (Button) findViewById(R.id.remove_drink_goal);

        openAddGoalActivity();
        DisplayData();

    }




    public void openAddGoalActivity() {
        addgoalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeToAddGoalActivity = new Intent(getApplicationContext(), addgoal.class);
                //Switches the activity to sign up.
                startActivity(changeToAddGoalActivity);
                helper.createToastWithText("Add a Goal!");
            }
        });

    }


    public void closeAddGoalActivity() {
        removegoalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.createToastWithText("Deleted goal");
            }
        });

    }


    public void DisplayData(){
        displayRecord = (TextView) findViewById(R.id.displayrecord);
        Context context = getApplicationContext();
        String data = helper.checkIfDataHasBeenAddedToDb(context);
        displayRecord.setText(data);
    }



}








