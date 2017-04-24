package coffee.prototype.android.cleandrinksapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.MainAdapter;

public class DrinkListingPageTea extends AppCompatActivity {
    private MainAdapter mAdapter;
    private  ActivityHelper helper = new ActivityHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_listing_page_tea);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tea_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MainAdapter(this, helper.populateDrinksArrayFromDataBaseTea(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0) {
            helper.insertTeaIntoDatabase(getApplicationContext());
            finish();
            startActivity(getIntent());
            mAdapter.notifyDataSetChanged();
        }



    }
}
