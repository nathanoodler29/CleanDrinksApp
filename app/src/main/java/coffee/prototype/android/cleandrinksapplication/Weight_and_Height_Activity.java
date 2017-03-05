package coffee.prototype.android.cleandrinksapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Weight_and_Height_Activity extends AppCompatActivity {
    private Spinner weightSpinner;
    private Spinner heightSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_and__height_);
        createWeightSpinnerAndPopulateWithValues();
        createHeightSpinnerAndPopulateWithValues();
    }


    public void createWeightSpinnerAndPopulateWithValues(){
        Spinner weightSpinner = (Spinner) findViewById(R.id.weight_spinner);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.weight_units, android.R.layout.simple_spinner_item);
        weightSpinner.setAdapter(adapter);

    }

    public void createHeightSpinnerAndPopulateWithValues(){
        Spinner heightSpinner = (Spinner) findViewById(R.id.height_spinner);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.height_units, android.R.layout.simple_spinner_item);
        heightSpinner.setAdapter(adapter);

    }
}
