package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DrinkCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink__category);
    }


    public void moveToAlcoholCategory(View view){
        Intent changeToDrinksCategoryAlcohol = new Intent(this, DrinksCategoryTypes.class);
        //Switches the activity to sign up.
        startActivity(changeToDrinksCategoryAlcohol);


    }
}



