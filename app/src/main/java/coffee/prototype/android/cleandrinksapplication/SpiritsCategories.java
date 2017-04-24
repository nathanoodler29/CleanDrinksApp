package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
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
        Intent changeToWhiskey = new Intent(this,DrinkListingPageWhiskey.class);
        //Switches the activity to sign up.
        startActivity(changeToWhiskey);
    }

    public void goToVodka(View view){
        Intent changeToVodka = new Intent(this,DrinkListingPageVodka.class);
        //Switches the activity to sign up.
        startActivity(changeToVodka);
    }

    public void goToGin(View view){
        Intent changeToGin = new Intent(this,DrinkListingPageGin.class);
        //Switches the activity to sign up.
        startActivity(changeToGin);
    }


}
