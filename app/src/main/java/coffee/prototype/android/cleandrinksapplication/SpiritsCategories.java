package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SpiritsCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spirits_categories);
    }

    public void goToWhiskey(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToWhiskey = new Intent(this,DrinkListingPageWhiskey.class);
        //Switches the activity to sign up.
        startActivity(changeToWhiskey);
    }

    public void goToVodka(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToVodka = new Intent(this,DrinkListingPageVodka.class);
        //Switches the activity to sign up.
        startActivity(changeToVodka);
    }

    public void goToGin(View view){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
        Intent changeToGin = new Intent(this,DrinkListingPageGin.class);
        //Switches the activity to sign up.
        startActivity(changeToGin);
    }


}
