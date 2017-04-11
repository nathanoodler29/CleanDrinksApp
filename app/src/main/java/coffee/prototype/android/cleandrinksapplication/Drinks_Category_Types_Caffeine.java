package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import coffee.prototype.android.cleandrinksapplication.data.SessionManager;

public class Drinks_Category_Types_Caffeine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks__category__types__caffeine);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
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

    public void changeToCaffieneTypePage(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        Intent changeToCaffineDisplayCat = new Intent(this,Caffine_Activity_Listing_Coffee_Based_Types.class);
        //Switches the activity to sign up.
        startActivity(changeToCaffineDisplayCat);
    }
}
