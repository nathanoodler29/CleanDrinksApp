package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.MainAdapter;

public class DrinkListingPageEspresso extends AppCompatActivity {
    private ActivityHelper activityHelper = new ActivityHelper();
    private AddEspressoDrink espressoDrink = new AddEspressoDrink();


    private MainAdapter mAdapter;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_listing_page_espresso);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.espresso_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MainAdapter(this, activityHelper.populateDrinksArrayFromDataBase(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0) {
            mAdapter.notifyDataSetChanged();
            activityHelper.insertValuesIntoDB(getApplicationContext());

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.espresso_drink_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add_drink) {
            finish();
            Intent changeToWeightPage = new Intent(this, AddEspressoDrink.class);
            startActivity(changeToWeightPage);

        }
        return super.onOptionsItemSelected(item);
    }
}