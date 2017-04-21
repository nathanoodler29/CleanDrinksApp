package coffee.prototype.android.cleandrinksapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.Model.Coffee;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.DrinkReciptAdapter;
import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.MainAdapter;

public class DrinkRecipt extends AppCompatActivity {
    private ActivityHelper helper= new ActivityHelper();
    private ArrayList<Drink>drinksRecipt = new ArrayList<>();
    private DrinkReciptAdapter mAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink__reciept);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.drinks_receipt_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new DrinkReciptAdapter(this, helper.populateDrinksReciptAdatper(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
    }
}
//populateDrinksReciptAdatper