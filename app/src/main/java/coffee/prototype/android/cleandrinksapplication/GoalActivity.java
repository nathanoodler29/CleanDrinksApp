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

import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.GoalsAdapter;
import coffee.prototype.android.cleandrinksapplication.data.GoalDBQueries;


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
        //card view related layout
        cardView = (CardView) findViewById(R.id.no_goals_set);
        noGoalsText = (TextView) findViewById(R.id.no_goals_set_text);

        //Goal heading layout change if the goal exists.
        goalHeading = (TextView) findViewById(R.id.goal_parent_heading);
        //Goal paragraph layout change if the goal exists.
        paragraphText = (TextView) findViewById(R.id.goal_paragraph_text);
        //Total number of goal layout change if the goal exists.
        totalNumOfGoals = (TextView) findViewById(R.id.total_num_of_goals);
        //Creates a recycler view for goals
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.goal_recycle);
        //creates a linear layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //sets a linear layout
        recyclerView.setLayoutManager(linearLayoutManager);
        //Populate the adapter with goals
        mAdapter = new GoalsAdapter(this, goalDBQueries.populateGoalAdapter(getApplicationContext()));
        //sets the adapter.
        recyclerView.setAdapter(mAdapter);
        //If the item count is zero.
        if (mAdapter.getItemCount() == 0) {

            //Reference: http://stackoverflow.com/questions/4249237/can-i-hide-an-image-button-on-a-layout-dimensions-and-background-until-a-call
            //makes the cared view visible.
            cardView.setVisibility(View.VISIBLE);
            //no goals text is displayed
            noGoalsText.setVisibility(View.VISIBLE);
            //doesn't display goals because none are presnt
            totalNumOfGoals.setVisibility(View.GONE);
            //if more than or euqal  0 goal exists
        } else if (mAdapter.getItemCount() >= 0) {
            //sets the card view to non visible
            cardView.setVisibility(View.GONE);
            //removes no goal text
            noGoalsText.setVisibility(View.GONE);
            //sets the text to view goals.
            goalHeading.setText("View your goals");
            //Review your goal text is dispalyed.
            paragraphText.setText("Review your goals");
            //Total number is displayed.
            totalNumOfGoals.setVisibility(View.VISIBLE);
            //Query from db to return the number of goals related to user session.
            totalNumOfGoals.setText("Total num of goals " + goalDBQueries.numOfGoalsCreatedForAUser(getApplicationContext()));


        }

    }

    /**
     * Creates a menu
     *
     * @param menu Needs a menu resource to populate the menu
     * @return True to display the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.goal_activity_menu, menu);
        return true;
    }

    /**
     * Sets the events related to each menu
     *
     * @param item relates to the option in the menu
     * @return The menu.
     * @todo need to set the progress to a value or query that's been written.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_view_goal_progress) {
            finish();
            //change to progress view
            Intent changeToProgress = new Intent(this, Progress_Tracking.class);
            startActivity(changeToProgress);


        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Open the add goal activity.
     *
     * @param view references current view.
     */
    public void openAddGoalActivity(View view) {

        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reference to vibrate onclick: http://stackoverflow.com/questions/20149415/vibrate-onclick
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                //sets duration.
                vibe.vibrate(100);
                //Opens the add goal class
                Intent changeToAddGoalActivity = new Intent(getApplicationContext(), addgoal.class);
                //Check to see if a goal has already beens et on current day.
                if (goalDBQueries.checkIfGoalHasAlreadyBeenSet(getApplicationContext())) {
                    //If so, then sorry goal has already been set message displayed
                    helper.createToastWithText("Sorry you've already set a goal, One goal a day can only be set.");
                    //else if a goal hasn't been set then user is able to add goa.
                } else if (!goalDBQueries.checkIfGoalHasAlreadyBeenSet(getApplicationContext())) {
                    //changes to add goal activity.
                    startActivity(changeToAddGoalActivity);
                    //toast message displays add a goa
                    helper.createToastWithText("Add a Goal!");
                    finish();


                }

            }
        });

    }

    /**
     * This was meant to delete a goal, but didn't get round to do the functionality.
     *
     * @param view References the current view.
     */
    public void closeAddGoalActivity(View view) {
        removeGoalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.createToastWithText("Deleted goal functionalty doesn't work");
            }
        });

    }

}









