package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import coffee.prototype.android.cleandrinksapplication.Model.Goal;

/**
 * Created by Noodle on 09/04/2017.
 */

public class GoalsAdapter extends RecyclerView.Adapter<GoalHolder>{

    Context c;
    List<Goal> goals = new ArrayList<>();
    ActivityHelper helper;

    public GoalsAdapter(Context c, List<Goal>goals){
        this.c = c;
        this.goals = goals;
    }
    @Override
    public GoalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_record_list_view,parent,false);
        GoalHolder goalHolder = new GoalHolder(v);

        return goalHolder;
    }

    @Override
    public void onBindViewHolder(GoalHolder holder, int position) {
        try{
            ArrayList<String>hello = new ArrayList<>();

            holder.displayRecord.setText(hello.get(position));
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return goals.size();
    }

    public void setAdapter(List<Goal> goals){
        notifyDataSetChanged();
//      notifyItemRangeChanged(0, getItemCount());

    }


}

