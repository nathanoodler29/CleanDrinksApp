package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.MainActivity;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.R;

/**
 * Created by Noodle on 09/04/2017.
 */


public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {
    private ArrayList<Goal> goals = new ArrayList<Goal>();
    private Context mContext;
    private ActivityHelper helper = new ActivityHelper();

    private MainActivity activity = new MainActivity();

    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        public TextView waterGoal;
        public TextView endTime;


        public ViewHolder(View v) {
            super(v);
        }
    }

    public GoalsAdapter(Context context, ArrayList<Goal> goalArrayList) {
        goals = goalArrayList;
        mContext = context;

    }


    @Override
    public GoalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.goal_record_list_view, parent, false);

        //set the view holder to the layout passed.
        final GoalsAdapter.ViewHolder viewHolder = new GoalsAdapter.ViewHolder(v);

        viewHolder.waterGoal = (TextView) v.findViewById(R.id.water_goal_record);
//        viewHolder.waterGoal = (TextView) v.findViewById(R.id.end_time_field);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.waterGoal.setText("Water Target"+goals.get(position).getStartTimeGoal());

//        holder.waterGoal.setText("Water Target"+goals.get(position).getEndTimeGoal());


    }


    @Override
    public int getItemCount() {

        return goals.size();
    }
}



