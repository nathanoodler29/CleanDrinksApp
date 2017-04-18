package coffee.prototype.android.cleandrinksapplication.Model;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;

import static java.lang.String.valueOf;

/**CFGF
 * created by Noodle on 04/04/2017.
 */

public class TimeHandler {
    private ActivityHelper activityHelper = new ActivityHelper();

    public String getYearAndMonth() {

        DateTime date = new DateTime();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd");

        return formatter.print(date);
    }

    public String getTotalDateWithTime() {

        DateTime date = new DateTime();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd:kk:mm");

        return formatter.print(date);
    }

    public String getHourAndMin() {

        DateTime time = new DateTime();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("kk:mm");

        return formatter.print(time);
    }


    public Boolean validateDates(String startGoal, String endGoal) {
        boolean dateCheck = false;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("kk:mm");
        activityHelper.createToastWithText("start" + startGoal);
        activityHelper.createToastWithText("emd" + endGoal);

        DateTime start = null;
        DateTime end = null;

        start = formatter.parseDateTime(startGoal);

        end = formatter.parseDateTime(endGoal);

        String difference = String.valueOf(valueOf(Hours.hoursBetween(start, end)));

        String removeSpecCharFrom = difference.replaceAll("PT", "").replaceAll("H", "").trim();
        int hourDifference = Integer.parseInt(removeSpecCharFrom);
        activityHelper.createToastWithText("Hour difference" + hourDifference);

        start.getYear();
        boolean resultOfDate = start.toLocalDate().isEqual(end.toLocalDate());

        String validateIfNegativeDifference = String.valueOf(hourDifference);
        end.getYear();

        getYearAndMonth();
        //add a check for year!
        if (start.isBefore(end) && !start.isAfter(end) && hourDifference >= 1 && !validateIfNegativeDifference.contains("-") && !start.equals(end) && resultOfDate) {
            activityHelper.createToastWithText("Start time is before end time, whcih is valid");
            dateCheck = true;
        } else if (start.equals(end)) {
            activityHelper.createToastWithText("You can't have the same start and end time");
            dateCheck = false;

        } else if (hourDifference <= 1) {
            activityHelper.createToastWithText("Times have to at least be 1 hour apart");
            dateCheck = false;

        } else if (start.isBefore(end)) {
            activityHelper.createToastWithText("Start time has to be before the end time");
            dateCheck = false;

        } else if (start.isAfter(end)) {
            activityHelper.createToastWithText("Start time has to be before the end time");
            dateCheck = false;

        } else if (end.isAfter(start)) {
            activityHelper.createToastWithText("end time has to be after the start time");
            dateCheck = false;

        } else if (!resultOfDate) {
            activityHelper.createToastWithText("Dates don't match");
        }else if(validateIfNegativeDifference.contains("-")){
            activityHelper.createToastWithText("Can't have a negative difference");
            dateCheck = false;

        }

        return dateCheck;
    }
}

