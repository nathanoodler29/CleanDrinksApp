package coffee.prototype.android.cleandrinksapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Progress_Tracking extends AppCompatActivity {

    /**
     * @Todo: Need to pass sql query, to show progress, just set set
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //uses progress tracking layout file
        setContentView(R.layout.activity_progress__tracking);

    }
}
