package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.GoalsAdapter;
import coffee.prototype.android.cleandrinksapplication.data.GoalDBQueries;
import coffee.prototype.android.cleandrinksapplication.data.SessionManager;


public class GoalActivity extends AppCompatActivity {


    private Button addGoalButton;
    private Button removeGoalbutton;
    private ActivityHelper helper = new ActivityHelper();
    private CardView cardView;
    private TextView noGoalsText;
    private TextView goalHeading;
    private TextView paragraphText;
    private TextView totalNumOfGoals;
    private GoalsAdapter mAdapter;

    private GoalDBQueries goalDBQueries = new GoalDBQueries();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_goal);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        addGoalButton = (Button) findViewById(R.id.adddrinkgoal);
        removeGoalbutton = (Button) findViewById(R.id.remove_drink_goal);
        //card view realted layout
        cardView = (CardView) findViewById(R.id.no_goals_set);
        noGoalsText = (TextView) findViewById(R.id.no_goals_set_text);


        //goal layout

        goalHeading = (TextView) findViewById(R.id.goal_parent_heading);
        paragraphText = (TextView) findViewById(R.id.goal_paragraph_text);

        totalNumOfGoals = (TextView)findViewById(R.id.total_num_of_goals);



        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.goal_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new GoalsAdapter(this, helper.populateGoalAdapter(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);

        if (mAdapter.getItemCount() == 0) {

            //http://stackoverflow.com/questions/4249237/can-i-hide-an-image-button-on-a-layout-dimensions-and-background-until-a-call
            cardView.setVisibility(View.VISIBLE);
            noGoalsText.setVisibility(View.VISIBLE);
            totalNumOfGoals.setVisibility(View.GONE);

        } else if (mAdapter.getItemCount() >= 0) {
            cardView.setVisibility(View.GONE);
            noGoalsText.setVisibility(View.GONE);
            goalHeading.setText("View your goals");
            paragraphText.setText("Review your goals");
            totalNumOfGoals.setVisibility(View.VISIBLE);
            totalNumOfGoals.setText("Total num of goals "+goalDBQueries.numOfGoalsCreatedForAUser(getApplicationContext()));

//            mAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.goal_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_view_goal_progress) {
            finish();
            Intent changeToProGRESS = new Intent(this, Progress_Tracking.class);
            startActivity(changeToProGRESS);


        }
        return super.onOptionsItemSelected(item);
    }


    public void openAddGoalActivity(View view) {

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://stackoverflow.com/questions/20149415/vibrate-onclick
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);

                Intent changeToAddGoalActivity = new Intent(getApplicationContext(), addgoal.class);
                if (goalDBQueries.checkIfGoalHasAlreadyBeenSet(getApplicationContext())){
                    helper.createToastWithText("Sorry you've already set a goal, One goal a day can only be set.");
                }else if(!goalDBQueries.checkIfGoalHasAlreadyBeenSet(getApplicationContext())){
                    startActivity(changeToAddGoalActivity);
                    helper.createToastWithText("Add a Goal!");
                    finish();


                }

            }
        });

    }


    public void closeAddGoalActivity(View view) {
        removeGoalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.createToastWithText("Deleted goal");
            }
        });

    }

}









