package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.MainActivity;
import coffee.prototype.android.cleandrinksapplication.Model.Achievement;
import coffee.prototype.android.cleandrinksapplication.R;

/*
 * created by noodle on 21/04/2017.
 */

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    private ArrayList<Achievement> achievements = new ArrayList<Achievement>();
    private Context mContext;
    private ActivityHelper helper = new ActivityHelper();
    private MainActivity activity = new MainActivity();


    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        public TextView achievementName;
        public TextView achievementDescription;
        public ImageView achievementAvater;


        public ViewHolder(View v) {
            super(v);
        }
    }


    public AchievementAdapter(Context context, ArrayList<Achievement> achivement) {
        achievements = achivement;
        mContext = context;

    }

    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.achievement_gallery_list_item, parent, false);

        //set the view holder to the layout passed.
        final AchievementAdapter.ViewHolder viewHolder = new AchievementAdapter.ViewHolder(v);


        viewHolder.achievementName = (TextView) v.findViewById(R.id.achievement_title);

        viewHolder.achievementDescription = (TextView) v.findViewById(R.id.achievement_description);

        viewHolder.achievementAvater = (ImageView) v.findViewById(R.id.achievement_image);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.achievementName.setText(achievements.get(position).getName());
        holder.achievementDescription.setText(achievements.get(position).getDescription());
        holder.achievementAvater.setImageResource(achievements.get(position).getAchievementAvater());

    }


    @Override
    public int getItemCount() {

        return achievements.size();
    }


}



