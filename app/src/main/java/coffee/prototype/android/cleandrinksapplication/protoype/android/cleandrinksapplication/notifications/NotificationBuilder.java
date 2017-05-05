package coffee.prototype.android.cleandrinksapplication.protoype.android.cleandrinksapplication.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coffee.prototype.android.cleandrinksapplication.AchievementGallery;
import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.GoalActivity;
import coffee.prototype.android.cleandrinksapplication.R;


/**
 * ceated by Noodle on 03/05/2017.
 */

public class NotificationBuilder {

    private AlarmManager alarmManager;

    private ActivityHelper helper = new ActivityHelper();


    /**
     * References for building notifications:
     * https://developer.android.com/training/notify-user/build-notification.html
     * https://www.tutorialspoint.com/android/android_notifications.htm
     *
     * @param context     Relates to current activity
     * @param title       Relates to the title of the notification
     * @param description Relates to the description of the notification.
     * @param image       Allows the image to be set in the notification.
     */
    public void createNotification(Context context, String title, String description, int image) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        //Creates the icon as a water drop
                        .setSmallIcon(R.drawable.waterdrop_blue)
                        //Sets notification title
                        .setContentTitle(title)
                        //Sets notification description
                        .setContentText(description)
                        //Sets the notification for default values such as vibrate.
                        .setDefaults(Notification.DEFAULT_ALL)
                        //Sets default sound for the notification
                        .setDefaults(Notification.DEFAULT_SOUND)
                        //Makes the notification have big style, which allows text to be dragged down to view extra.
                        .setStyle(new NotificationCompat.BigTextStyle()
                                //Sets the icon to large,
                                //used this resource: http://stackoverflow.com/questions/34225779/how-to-set-the-app-icon-as-the-notification-icon-in-the-notification-drawer
                                .bigText(title).bigText(description))
                        .setLargeIcon(BitmapFactory.decodeResource((context.getResources()), image));

        //Switches to the goal activity, to view the set goal.
        Intent notificationGoalIntent = new Intent(context, GoalActivity.class);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationGoalIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);
        //Creates the notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Builds the notification.
        manager.notify(0, builder.build());

    }

    /**
     * Creates a notification for an achievement, which sends the user to the achievement page using an intent.
     * Reference used for building notification: https://developer.android.com/training/notify-user/build-notification.html
     *
     * @param context     Uses the application context.
     * @param title       Title given to the notification.
     * @param description Description given to the notification.
     * @param image       Image icon  present in the notification.
     */
    public void createNotificationAchievement(Context context, String title, String description, int image) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        //Creates the icon as a water drop
                        .setSmallIcon(R.drawable.waterdrop_blue)
                        //Sets notification title
                        .setContentTitle(title)
                        //Sets notification description
                        .setContentText(description)
                        //Sets the notification for default values such as vibrate.
                        .setDefaults(Notification.DEFAULT_ALL)
                        //Sets default sound for the notification
                        .setDefaults(Notification.DEFAULT_SOUND)
                        //Makes the notification have big style, which allows text to be dragged down to view extra.
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(title).bigText(description))
                        //Sets the icon to large,
                        //used this resource: http://stackoverflow.com/questions/34225779/how-to-set-the-app-icon-as-the-notification-icon-in-the-notification-drawer
                        .setLargeIcon(BitmapFactory.decodeResource((context.getResources()), image));

        //Switches to the achievement gallery activity, to view the unlocked achievement.
        Intent notificationGoalIntent = new Intent(context, AchievementGallery.class);
        //1 provides the pending intent with a new id, to stop overriding of notifications
        PendingIntent intent = PendingIntent.getActivity(context, 1, notificationGoalIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);
        //Creates the Notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //sets id for notification as 1 and then builds the notification.
        manager.notify(1, builder.build());

    }


    public void alarmManger(Context context, int startHour, int startMin, int endHour, int endMin) {
        //Sets the broadcast to  sending notifications, using the system application events in Android.
        Intent intent = new Intent(context.getApplicationContext(), NotificationTimer.class);
        //References the startTime of the goal
        Calendar cal = new GregorianCalendar();
        //References the endTime of the goal
        Calendar calender = new GregorianCalendar();
        //Passes the starting hour
        cal.set(Calendar.HOUR_OF_DAY, startHour);
        //passes the starting min.
        cal.set(Calendar.MINUTE, startMin);


        //Pending intent, allows the time to be updated, as well as the notification itself, as the the intent is related to the notification.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Creates the alarm manager and alarm service.
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Sends the end time hour
        calender.set(Calendar.HOUR_OF_DAY, endHour);
        //Sets the end time min
        calender.set(Calendar.MINUTE, endMin);
        //If the two times aren't equal then send keep sending notifications
        if (cal.getTimeInMillis() != calender.getTimeInMillis()) {
            //This sets the notifications every half an hour using the setRepeating
            //RTC.WAKEUP allows the screen to show notification even while screen is off.
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
            //If the two times match then cancel the pending intent to stop the notificaitons.
            if (cal.getTimeInMillis() == calender.getTimeInMillis()) {
                alarmManager.cancel(pendingIntent);

            }
            //If the condition doesn't match the if then the alarm manager is cancelled stopping the notification.
        } else {
            alarmManager.cancel(pendingIntent);

        }
    }
}
