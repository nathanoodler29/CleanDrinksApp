package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import coffee.prototype.android.cleandrinksapplication.Model.Goal;


public class GoalActivity extends AppCompatActivity {


    private Button addGoalButton;
    private Button removeGoalbutton;
    private ActivityHelper helper = new ActivityHelper();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Goal> goals;
    private Goal goalClass;
    private Parcelable recyclerViewState;
    private CardView cardView;
    private TextView noGoalsText;
    private TextView goalHeading;
    private TextView paragraphText;

//    List<Goal> goals = new ArrayList<>();
//    GoalsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_goal);
        addGoalButton = (Button) findViewById(R.id.adddrinkgoal);
        removeGoalbutton = (Button) findViewById(R.id.remove_drink_goal);
        //card view realted layout
        cardView = (CardView) findViewById(R.id.no_goals_set);
        noGoalsText = (TextView) findViewById(R.id.no_goals_set_text);


        //goal layout

        goalHeading = (TextView) findViewById(R.id.goal_parent_heading);
        paragraphText = (TextView) findViewById(R.id.goal_paragraph_text);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.goal_recycle);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

        GoalsAdapter adapter = new GoalsAdapter(this, helper.checkIfDataHasBeenAddedToDb(getApplicationContext()));
        adapter.setAdapter(helper.checkIfDataHasBeenAddedToDb(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        if (adapter.getItemCount() == 0) {

            //http://stackoverflow.com/questions/4249237/can-i-hide-an-image-button-on-a-layout-dimensions-and-background-until-a-call
            cardView.setVisibility(View.VISIBLE);
            noGoalsText.setVisibility(View.VISIBLE);
        } else if (adapter.getItemCount() >= 0) {
            cardView.setVisibility(View.GONE);
            noGoalsText.setVisibility(View.GONE);
            goalHeading.setText("View your goals");
            paragraphText.setText("Review your goals");
        }


    }


    public void openAddGoalActivity(View view) {
        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeToAddGoalActivity = new Intent(getApplicationContext(), addgoal.class);
                //Switches the activity to sign up.
                startActivity(changeToAddGoalActivity);
                helper.createToastWithText("Add a Goal!");
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









