package coffee.prototype.android.cleandrinksapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.Model.Coffee;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.DrinkReciptAdapter;
import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.MainAdapter;

public class DrinkRecipt extends AppCompatActivity {
    private ActivityHelper helper= new ActivityHelper();
    private ArrayList<Drink>drinksRecipt = new ArrayList<>();
    private DrinkReciptAdapter mAdapter;
    private CardView cardViewHeading;
    private CardView cardViewBottom;
    private TextView noDrinksSetHeading;
    private TextView noDrinksSetParagraph;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink__reciept);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.drinks_receipt_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new DrinkReciptAdapter(this, helper.populateDrinksReciptAdatper(getApplicationContext()));
        cardViewHeading = (CardView) findViewById(R.id.no_values_drink_recipt_heading);
        cardViewBottom = (CardView) findViewById(R.id.no_drinks_set_card);
        noDrinksSetHeading = (TextView) findViewById(R.id.no_drinks_set_parent_heading);
        noDrinksSetParagraph = (TextView) findViewById(R.id.onstrike_paragraph_text);

        recyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount()==0){
            cardViewHeading.setVisibility(View.VISIBLE);
            cardViewBottom.setVisibility(View.VISIBLE);
            noDrinksSetHeading.setVisibility(View.VISIBLE);
            noDrinksSetParagraph.setVisibility(View.VISIBLE);
        }else{
            cardViewHeading.setVisibility(View.GONE);
            cardViewBottom.setVisibility(View.GONE);
            noDrinksSetHeading.setVisibility(View.GONE);
            noDrinksSetParagraph.setVisibility(View.GONE);
        }
    }
}
