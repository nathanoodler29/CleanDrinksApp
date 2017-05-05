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

    /**
     * Changes the current activity to the Alcohol category page.
     *
     * @param view References current view.
     */
    public void moveToAlcoholCategory(View view) {

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToDrinksCategoryAlcohol = new Intent(this, DrinksCategoryAlcoholTypes.class);
        //Switches the activity to sign up.
        startActivity(changeToDrinksCategoryAlcohol);


    }

    /**
     * Changes the current activity to the caffeine category page.
     *
     * @param view References current view.
     */
    public void moveToCaffeineCategory(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        Intent changeToDrinksCategoryAlcohol = new Intent(this, Drinks_Category_Types_Caffeine.class);
        //Switches the activity to sign up.
        startActivity(changeToDrinksCategoryAlcohol);


    }

    /**
     * Changes the current activity to the water listing page.
     *
     * @param view References current view.
     */
    public void moveToWaterListing(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        Intent changeToWaterListingPage = new Intent(this, DrinkListingPageForWater.class);
        startActivity(changeToWaterListingPage);


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
        menuInflater.inflate(R.menu.add_goal_and_view_achievement_menu, menu);
        return true;
    }

    /**
     * Sets the events related to each menu
     *
     * @param item Item relates to the option in the menu
     * @return The menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (item.getItemId() == R.id.action_addgoal) {

            Intent changeToGoal = new Intent(this, GoalActivity.class);
            //Switches the activity to goal
            startActivity(changeToGoal);
        } else if (item.getItemId() == R.id.action_view_achivements) {
            //Switches the activity to achievement
            Intent achivements = new Intent(this, AchievementGallery.class);
            startActivity(achivements);


        }
        return super.onOptionsItemSelected(item);
    }
}



