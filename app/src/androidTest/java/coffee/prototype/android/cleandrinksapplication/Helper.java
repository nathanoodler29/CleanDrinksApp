package coffee.prototype.android.cleandrinksapplication;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Noodle on 20/03/2017.
 */

public class Helper {

    public String getCurrentTime(){
        //http://stackoverflow.com/questions/15283245/java-date-in-mmddhhmmss-format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMddhhmmss");
        String dateString= simpleDateFormat.format(new Date());


        return dateString;

    }



}
