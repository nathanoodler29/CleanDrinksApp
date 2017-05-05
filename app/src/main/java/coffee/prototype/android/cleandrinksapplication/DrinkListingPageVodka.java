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

import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.MainAdapter;
import coffee.prototype.android.cleandrinksapplication.data.DBQueryHelper;

public class DrinkListingPageVodka extends AppCompatActivity {
    private MainAdapter mAdapter;
    private ActivityHelper helper = new ActivityHelper();
    private DBQueryHelper queryHelper = new DBQueryHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //References vodka toolbar
        setContentView(R.layout.activity_drink_listing_page_vodka);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.vodka_toolbar);
        //sets the toolbar so it's visible.
        setSupportActionBar(myToolbar);
        //Locates and sets the recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.vodka_recycler);
        //Creates linear layout for this activity.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //sets the linear layout for the activity.
        recyclerView.setLayoutManager(linearLayoutManager);
        //Searches through the drinks array list for drinks of type vodka and places these values in mAdapter
        mAdapter = new MainAdapter(this, helper.populateDrinksArrayFromDataBaseGeneric(getApplicationContext(), "Vodka"));
        //Set the recycler view with the adapter.
        recyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0) {
            //Insert the vodka values in the database.
            queryHelper.insertVodkaBasedDrinksIntoDB(getApplicationContext());
            //Go back to previous screen
            finish();
            //Refresh the activity
            startActivity(getIntent());
            //Update the adapter.
            mAdapter.notifyDataSetChanged();
        }

    }


    /**
     * Creates a menu
     *
     * @param menu Needs a menu resource to populate the menu
     * @return True to display the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.espresso_drink_menu, menu);
        return true;
    }


    /**
     * Sets the events related to each menu
     *
     * @param item relates to the option in the menu
     * @return The menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //relates to adding the drink for the activity.
        if (item.getItemId() == R.id.action_add_drink) {
            finish();
            Intent changeToWeightPage = new Intent(this, AddAlcholicDrink.class);
            startActivity(changeToWeightPage);

            //shows log of drinks

        } else if (item.getItemId() == R.id.action_drinks_recipt) {
            finish();
            Intent changeToDrinksRecipt = new Intent(this, DrinkRecipt.class);
            startActivity(changeToDrinksRecipt);
        }
        return super.onOptionsItemSelected(item);
    }
}
