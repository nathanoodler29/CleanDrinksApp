package coffee.prototype.android.cleandrinksapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import coffee.prototype.android.cleandrinksapplication.data.GoalDBQueries;

public class AchievementGallery extends AppCompatActivity {
    private TextView totalAchivements;
    private GoalDBQueries goalQueryHelper = new GoalDBQueries();
    private CardView rookieCard;
    private TextView rookieTitle;
    private TextView rookieDescription;
    private ImageView rookieImage;
    private int unlocked = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_gallery);
        //locates the number of achievements unlocked.
        totalAchivements = (TextView) findViewById(R.id.total_numberOfAchivements);
        // card view relates to the coloured squares in the layout for each of the achivements.
        rookieCard = (CardView) findViewById(R.id.achievement_displayed_rookie);
        //Rookie title text.
        rookieTitle = (TextView) findViewById(R.id.achievement_invidual_title);
        //Rookie title description.
        rookieDescription = (TextView) findViewById(R.id.achievement_description_invidual);
        //Rookie image view.
        rookieImage = (ImageView) findViewById(R.id.achievement_image_invidual_rookie);
        Context context = getApplicationContext();
        //Total achievements set to Zero since at this stage no achievements are unlocked.
        String haveUnlockedZero = "You have unlocked 0/2 ";
        totalAchivements.setText(haveUnlockedZero);
        //Checks to see if a user has unlocked their first achievement by adding a goal
        checkIfAchievementHasBeenUnlocked(context);

    }


    public int checkIfAchievementHasBeenUnlocked(Context context) {
        //Checks to see if a user has added a goal in the database.
        if (goalQueryHelper.numOfGoalsCreatedForAUser(getApplicationContext()) == 1) {
            //if so then unlocked is 1 for achievement
            unlocked += 1;
            context.getApplicationContext();
            //Total of achievements is now 1.
            String haveUnlockedOne = "You have unlocked 1/2 ";

            totalAchivements.setText(haveUnlockedOne);
            //Color of the card view is now purple instead of grey.
            rookieCard.setBackgroundColor(Color.parseColor("#6666b2"));
            //Text colour is now black instead of grey. This reflects a notifcation unlocked.
            rookieTitle.setTextColor(Color.parseColor("#000000"));
            //Text colour for description is now black instead of grey. This reflects a notifcation unlocked.
            rookieDescription.setTextColor(Color.parseColor("#000000"));
            //Ges the image resource as an image.
            int unlockedRookieAch = getResources().getIdentifier("rookie_color_crown_achi", "drawable", context.getPackageName());
            //Sets the image resource.
            rookieImage.setImageResource(unlockedRookieAch);
            //Creates a content description for a visually impaired user.
            rookieImage.setContentDescription("Unlocked Crown, now coloured Gold.");

        }

        return unlocked;
    }
}
