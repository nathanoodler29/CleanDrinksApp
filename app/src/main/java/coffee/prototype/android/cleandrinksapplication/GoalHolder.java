package coffee.prototype.android.cleandrinksapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by noodle on 09/04/2017.
 */

public class GoalHolder extends RecyclerView.ViewHolder {
        TextView displayRecord;
        ImageView goalImage;
        CheckBox checkBox;

        public GoalHolder(View myView) {
            super(myView);
            displayRecord = (TextView) myView.findViewById(R.id.displayrecord);
            goalImage = (ImageView) myView.findViewById(R.id.goal_icon);
            checkBox = (CheckBox) myView.findViewById(R.id.goal_checkbox);

        }
}
