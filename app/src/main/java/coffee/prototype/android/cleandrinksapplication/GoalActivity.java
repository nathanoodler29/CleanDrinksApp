package coffee.prototype.android.cleandrinksapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GoalActivity extends AppCompatActivity {


    private Button addgoalbutton;


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


                EditText numOfLitesDialog = (EditText) findViewById(R.id.numberOfLitres);

                EditText startTimeDialog = (EditText) findViewById(R.id.starttime);

                EditText endTimeDialog = (EditText) findViewById(R.id.endtime);

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


}
