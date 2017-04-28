package coffee.prototype.android.cleandrinksapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.MainAdapter;

public class DrinkListingPageVodka extends AppCompatActivity {
    private MainAdapter mAdapter;
    private  ActivityHelper helper = new ActivityHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_listing_page_vodka);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.vodka_toolbar);
        setSupportActionBar(myToolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.vodka_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new MainAdapter(this, helper.populateDrinksArrayFromDataBaseGeneric(getApplicationContext(),"Vodka"));
        recyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0) {
            helper.insertVodkaIntoDatabase(getApplicationContext());
            finish();
            startActivity(getIntent());
            mAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.espresso_drink_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        if (item.getItemId() == R.id.action_add_drink) {
            finish();
            Intent changeToWeightPage = new Intent(this, AddAlcholicDrink.class);
            startActivity(changeToWeightPage);


        } else if (item.getItemId() == R.id.action_drinks_recipt) {
            finish();
            Intent changeToDrinksRecipt = new Intent(this, DrinkRecipt.class);
            startActivity(changeToDrinksRecipt);
        }
        return super.onOptionsItemSelected(item);
    }
}
