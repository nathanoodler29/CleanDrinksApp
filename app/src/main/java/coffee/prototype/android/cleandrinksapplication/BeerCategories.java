package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BeerCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_categories);
    }


    /**
     * Changes the current activity to the lager listing page.
     *
     * @param view References current view.
     */
    public void goToLagerPage(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToLagerListingPage = new Intent(this,DrinkListingPageLager.class);
        //Switches the activity to sign up.
        startActivity(changeToLagerListingPage);
    }

    /**
     * Changes the current activity to the craft beer listing page.
     *
     * @param view References current view.
     */
    public void goToCraftBeerPage(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToCraftListingPage = new Intent(this,DrinkListingPageCraftBeer.class);
        //Switches the activity to sign up.
        startActivity(changeToCraftListingPage);
    }

    /**
     * Changes the current activity to the real ale listing page.
     *
     * @param view References current view.
     */
    public void goToRealAleBeerPage(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToRealAleListingPage = new Intent(this,DrinkListingPageRealAle.class);
        //Switches the activity to sign up.
        startActivity(changeToRealAleListingPage);
    }

    /**
     * Changes the current activity to the stout listing page.
     *
     * @param view References current view.
     */
    public void goToStoutPage(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToStoutListingPage = new Intent(this,DrinkListingPageStout.class);
        //Switches the activity to sign up.
        startActivity(changeToStoutListingPage);
    }
}
