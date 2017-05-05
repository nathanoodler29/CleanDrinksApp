package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import coffee.prototype.android.cleandrinksapplication.Model.Goal;
import coffee.prototype.android.cleandrinksapplication.R;

/**
 * created by Noodle on 09/04/2017.
 */


public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {
    private ArrayList<Goal> goals = new ArrayList<Goal>();
    private Context mContext;


    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        private TextView waterGoal;
        private TextView startTime;
        private TextView endTime;


        public ViewHolder(View v) {
            super(v);
        }
    }

    public GoalsAdapter(Context context, ArrayList<Goal> goalArrayList) {
        goals = goalArrayList;
        mContext = context;

    }


    /**
     * Initialises all of the layout elements which will be found in the recycler view.
     * Reference: This code for the View holder itself is from Android Studio Cookbook  Mike van Drongelen (isbn 1785286188) Pages 40-45.
     * Note all action listener code etc, was my own implementation.
     * @param parent   Refers to the Activites context.
     * @param viewType Refers to the layout used for the View holder.
     * @return The elements related to the View holder, which are found in the recycler view.
     */
    public GoalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Layout inflater notifies the Android screen, using declared xml elements, i.e Goal start time.
        //The layout resource is passed, to populate the view of the view holder.
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.goal_record_list_view, parent, false);

        //set the view holder to the layout passed.
        final GoalsAdapter.ViewHolder viewHolder = new GoalsAdapter.ViewHolder(v);
        //Refers to the users set Water goal.
        viewHolder.waterGoal = (TextView) v.findViewById(R.id.water_goal_record);
        //Refers to the start time of a goal, that a user has set.
        viewHolder.startTime = (TextView) v.findViewById(R.id.start_time_record);
        //Refers to the end time of a goal, that a user has set.
        viewHolder.endTime = (TextView) v.findViewById(R.id.end_time_record);
        //Returns the view with all the elements above.
        return viewHolder;
    }

    /**
     * This method adds all of the data to the elements from the layout.
     *
     * @param holder   Refers to the layout of the application for the recycler view.
     * @param position Refers to the position of the array list.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Populates the water Goal value with the value returned from the ArrayList
        holder.waterGoal.setText("Water Goal: "+goals.get(position).getWaterGoalStr()+" litres");
        //Populates the water start time  value with the value returned from the ArrayList
        holder.startTime.setText("Duration: "+goals.get(position).getStartTimeGoal()+" - ");
        //Populates the water end time  value with the value returned from the ArrayList
        holder.endTime.setText(goals.get(position).getEndTimeGoal());



    }


    @Override
    public int getItemCount() {
        //Returns the size of the goals arraylist.
        return goals.size();
    }
}



