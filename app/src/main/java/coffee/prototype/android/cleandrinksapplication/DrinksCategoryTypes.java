package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DrinksCategoryTypes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_category_types);
    }


    public void goToWineCategory(View view){
        Intent changeToWineCategoryPage = new Intent(this, WineCategory.class);
        //Switches the activity to sign up.
        startActivity(changeToWineCategoryPage);
    }

}
