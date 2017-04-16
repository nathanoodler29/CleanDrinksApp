package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import coffee.prototype.android.cleandrinksapplication.data.SessionManager;

public class DrinkCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink__category);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

    }


    public void moveToAlcoholCategory(View view) {

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToDrinksCategoryAlcohol = new Intent(this, DrinksCategoryTypes.class);
        //Switches the activity to sign up.
        startActivity(changeToDrinksCategoryAlcohol);


    }

    public void moveToCaffineCategory(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        Intent changeToDrinksCategoryAlcohol = new Intent(this, Drinks_Category_Types_Caffeine.class);
        //Switches the activity to sign up.
        startActivity(changeToDrinksCategoryAlcohol);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.current_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (item.getItemId() == R.id.action_logout) {
            sessionManager.deleteSession();
//            createToastWithText("Successfully logged out");
        } else if (item.getItemId() == R.id.action_addgoal) {

            Intent changeToGoal = new Intent(this, GoalActivity.class);
            //Switches the activity to sign up.
            startActivity(changeToGoal);
        }
        return super.onOptionsItemSelected(item);
    }
}



