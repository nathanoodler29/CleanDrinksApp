package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SpiritsCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spirits_categories);
    }

    /**
     * Changes the current activity to the Whiskey listing pages.
     *
     * @param view References current view.
     */
    public void goToWhiskey(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToWhiskey = new Intent(this, DrinkListingPageWhiskey.class);
        //Switches the activity to sign up.
        startActivity(changeToWhiskey);
    }


    /**
     * Changes the current activity to  vodka listing pages.
     *
     * @param view References current view.
     */
    public void goToVodka(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToVodka = new Intent(this, DrinkListingPageVodka.class);
        //Switches the activity to sign up.
        startActivity(changeToVodka);
    }

    /**
     * Changes the current activity to  gin listing pages.
     *
     * @param view References current view.
     */
    public void goToGin(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToGin = new Intent(this, DrinkListingPageGin.class);
        //Switches the activity to sign up.
        startActivity(changeToGin);
    }


}
