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

public class DrinksCategoryAlcoholTypes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_alcohol_category_types);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }


    /**
     * Changes the current activity to the wine category pages.
     *
     * @param view References current view.
     */
    public void goToWineCategory(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToWineCategoryPage = new Intent(this, WineCategories.class);
        //Switches the activity to sign up.
        startActivity(changeToWineCategoryPage);
    }


    /**
     * Changes the current activity to the beer category pages.
     *
     * @param view References current view.
     */
    public void goToBeerCat(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToBeerCategoryPage = new Intent(this, BeerCategories.class);
        //Switches the activity to sign up.
        startActivity(changeToBeerCategoryPage);
    }


    /**
     * Changes the current activity to the spirits category pages.
     *
     * @param view References current view.
     */
    public void goToSpiritsCat(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToSpiritsCategoryPage = new Intent(this, SpiritsCategories.class);
        //Switches the activity to sign up.
        startActivity(changeToSpiritsCategoryPage);
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
