package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    public void goToWineCategory(View view){
        Intent changeToWineCategoryPage = new Intent(this, WineCategories.class);
        //Switches the activity to sign up.
        startActivity(changeToWineCategoryPage);
    }

    public void goToBeerCat(View view){
        Intent changeToBeerCategoryPage = new Intent(this, BeerCategories.class);
        //Switches the activity to sign up.
        startActivity(changeToBeerCategoryPage);
    }

    public void goToSpiritsCat(View view){
        Intent changeToSpiritsCategoryPage = new Intent(this, SpiritsCategories.class);
        //Switches the activity to sign up.
        startActivity(changeToSpiritsCategoryPage);
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
        }else if(item.getItemId()==R.id.action_addgoal){

            Intent changeToGoal = new Intent(this, GoalActivity.class);
            //Switches the activity to sign up.
            startActivity(changeToGoal);
        }
        return super.onOptionsItemSelected(item);
    }




}
