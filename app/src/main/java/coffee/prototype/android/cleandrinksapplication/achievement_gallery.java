package coffee.prototype.android.cleandrinksapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes.AchievementAdapter;

public class achievement_gallery extends AppCompatActivity {
    private AchievementAdapter mAdapter;
    private ActivityHelper helper = new ActivityHelper();
    private TextView totalAchivements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_gallery);
        totalAchivements = (TextView) findViewById(R.id.total_numberOfAchivements);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.achievement_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new AchievementAdapter(this, helper.populateAchivementsFromDataBase(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
        totalAchivements.setText(helper.getNumOfAchivementsInDB()+" Achievements");
        if (mAdapter.getItemCount() == 0) {
            helper.populateAchievementsDatabase(getApplicationContext());
            finish();
            startActivity(getIntent());
            mAdapter.notifyDataSetChanged();

        }


    }
}
//getNumOfAchivementsInDB

//populateAchivementsFromDataBase
