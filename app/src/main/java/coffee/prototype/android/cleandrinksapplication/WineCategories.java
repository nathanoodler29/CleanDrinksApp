package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WineCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_categories);


    }


    public void changeToRedWineListing(View view){
        Intent changeToRedWinePage = new Intent(this,RedWineListingPage.class);
        //Switches the activity to sign up.
        startActivity(changeToRedWinePage);
    }


    public void changeToWhiteWineListing(View view){
        Intent changeToWhiteWinePage = new Intent(this,WhiteWineListingPage.class);
        //Switches the activity to sign up.
        startActivity(changeToWhiteWinePage);
    }
}
