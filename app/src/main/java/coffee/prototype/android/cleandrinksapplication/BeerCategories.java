package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BeerCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_categories);
    }

    public void goToLagerPage(View view){
        Intent changeToLagerListingPage = new Intent(this,DrinkListingPageLager.class);
        //Switches the activity to sign up.
        startActivity(changeToLagerListingPage);
    }

    public void goToCraftBeerPage(View view){
        Intent changeToCraftListingPage = new Intent(this,DrinkListingPageCraftBeer.class);
        //Switches the activity to sign up.
        startActivity(changeToCraftListingPage);
    }

    public void goToRealAleBeerPage(View view){
        Intent changeToRealAleListingPage = new Intent(this,DrinkListingPageRealAle.class);
        //Switches the activity to sign up.
        startActivity(changeToRealAleListingPage);
    }

    public void goToStoutPage(View view){
        Intent changeToStoutListingPage = new Intent(this,DrinkListingPageStout.class);
        //Switches the activity to sign up.
        startActivity(changeToStoutListingPage);
    }
}
