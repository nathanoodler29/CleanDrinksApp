package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivitiy extends AppCompatActivity {
    /**
     * This displays the splash screen when the applicaiton is executed.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent splash_intent = new Intent(this,MainActivity.class);
        startActivity(splash_intent);
        finish();
    }
}
