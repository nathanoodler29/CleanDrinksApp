package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Caffine_Activity_Listing_Coffee_Based_Types extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffine___listing__coffee__based__types);
    }


    /**
     * Changes the current activity to the espresso listing pages.
     *
     * @param view References current view.
     */
    public void listDrinks(View view){
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(100);

            Intent drinkListingPageEspresso = new Intent(this,DrinkListingPageEspresso.class);
            //Switches the activity to sign up.
            startActivity(drinkListingPageEspresso);
        }


    /**
     * Changes the current activity to the espresso listing pages.
     *
     * @param view References current view.
     */
    public void listBlackCoffeeBasedDrinks(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        Intent drinkListingBlackCoffee = new Intent(this,BlackCoffeeBasedDrinksListing.class);
        //Switches the activity to sign up.
        startActivity(drinkListingBlackCoffee);
    }
    }

