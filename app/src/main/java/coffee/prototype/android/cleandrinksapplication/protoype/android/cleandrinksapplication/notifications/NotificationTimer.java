package coffee.prototype.android.cleandrinksapplication.protoype.android.cleandrinksapplication.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import coffee.prototype.android.cleandrinksapplication.DrinkListingPageForWater;
import coffee.prototype.android.cleandrinksapplication.R;

/**
 * created by Noodle on 03/05/2017.
 */

public class NotificationTimer extends BroadcastReceiver {
    /**
     * Reference for building Notification  //https://developer.android.com/training/notify-user/build-notification.html
     * @param context Uses activity context in Android.
     * @param intent Intent is used for changing the activity
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //Returns image as an integer.
        int image = context.getResources().getIdentifier("water_bottle", "drawable", context.getPackageName());

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        //Sets Top section of the notification as a blue water drop.
                        .setSmallIcon(R.drawable.waterdrop_blue)
                        .setContentTitle("Drink Clean!")
                        .setContentText("Click to add a glass of water, to be nearer to your water goal!")
                        //Default all sets the notification to have vibrate as standard.
                        .setDefaults(Notification.DEFAULT_ALL)
                        //Sets the default notification sound.
                        .setDefaults(Notification.DEFAULT_SOUND)
                        //This allows the notification to be dragged down to expand text.
                        .setStyle(new NotificationCompat.BigTextStyle()
                                //Sets the title and description to be dragged down
                                .bigText("Drink Clean!").bigText("Click to add a glass of water, to be nearer to your water goal!"))
                        //Makes the icon larger
                        .setLargeIcon(BitmapFactory.decodeResource((context.getResources()), image));
        //Sets the intent for the drink listing page.
        Intent waterCategory = new Intent(context, DrinkListingPageForWater.class);
        //This removes the prior instance of the activity.
        waterCategory.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //This intent allows the notification when clicked to be transported to the Drinks listing page for water.
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, waterCategory,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //Flat update current, which if a notification already exists then it'll replace it if the data is different
        //Sets the intent to go to the water category activity.
        builder.setContentIntent(pendingIntent);
        //Creates the Notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //sets id for notifcaiton as 0 and then builds the notification.
        manager.notify(0, builder.build());


    }
}
