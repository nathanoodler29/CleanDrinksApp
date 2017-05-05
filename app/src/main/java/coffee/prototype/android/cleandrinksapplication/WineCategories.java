package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WineCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_categories);


    }


    /**
     * Changes the current activity to the Red wine listing pages.
     *
     * @param view References current view.
     */
    public void changeToRedWineListing(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToRedWinePage = new Intent(this, RedWineListingPage.class);
        //Switches the activity to sign up.
        startActivity(changeToRedWinePage);
    }


    /**
     * Changes the current activity to the White wine listing pages.
     *
     * @param view References current view.
     */
    public void changeToWhiteWineListing(View view) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToWhiteWinePage = new Intent(this, WhiteWineListingPage.class);
        //Switches the activity to sign up.
        startActivity(changeToWhiteWinePage);
    }
}
