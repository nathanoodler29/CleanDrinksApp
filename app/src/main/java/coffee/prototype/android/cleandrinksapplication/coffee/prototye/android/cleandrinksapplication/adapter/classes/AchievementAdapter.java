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

    /**
     * View holder class is used to notify the class where the elements are placed in the recycler view
     * For example the Achievement name, relates to 'r.id.achievement_title' in the list view layout file.
     * The getName method then populates the name for that Achievement.
      */
    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        private TextView achievementName;
        private TextView achievementDescription;
        private ImageView achievementAvater;


        public ViewHolder(View v) {
            super(v);
        }
    }


    public AchievementAdapter(Context context, ArrayList<Achievement> achivement) {
        achievements = achivement;
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
    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Layout inflater notifies the Android screen, using declared xml elements, i.e achievement title.
        //The layout resource is passed, to populate the view of the view holder.
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.achievement_gallery_list_item, parent, false);

        //set the view holder to the layout passed.
        final AchievementAdapter.ViewHolder viewHolder = new AchievementAdapter.ViewHolder(v);

        //initialises the achievement name with the relevant xml element from the layout file.
        viewHolder.achievementName = (TextView) v.findViewById(R.id.achievement_title);

        viewHolder.achievementDescription = (TextView) v.findViewById(R.id.achievement_description);

        viewHolder.achievementAvater = (ImageView) v.findViewById(R.id.achievement_image);
        return viewHolder;
    }

    /**
     * This method adds the data to the recycler view elements. In this case, name, description
     * and image of the achievement.
     * @param holder The holder refers to the prior created view holder, to state the elements
     * in the achievement list item xml.
     * @param position Refers to the position within the Achievement ArrayList and retrieves
     * the achivement data from this, then sets the values of the elements.
     *
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //The set text method, adds the value from the arraylist, so a user is able to see it.
        holder.achievementName.setText(achievements.get(position).getName());
        holder.achievementDescription.setText(achievements.get(position).getDescription());
        //The set Image method, adds the image from the arraylist, so a user is able to see it.
        holder.achievementAvater.setImageResource(achievements.get(position).getAchievementAvater());

    }


    @Override
    public int getItemCount() {
        //Returns the number of enteries in the Achievements list.
        return achievements.size();
    }


}



