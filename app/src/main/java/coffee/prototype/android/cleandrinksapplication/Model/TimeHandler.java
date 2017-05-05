package coffee.prototype.android.cleandrinksapplication.Model;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;

import static java.lang.String.valueOf;

/*
 * created by Nathan on 04/04/2017.
 */

public class TimeHandler {
    private ActivityHelper activityHelper = new ActivityHelper();

    /**
     * Gets the year  month and day in the created format.
     *
     * @return The month, day and year as a String.
     */
    public String getYearAndMonth() {
        //Creates a new date time object.
        DateTime date = new DateTime();
        //Creates the pattern for date required.
        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd");
        //Returns the formatted date as a string.
        return formatter.print(date);
    }

    /**
     * Gets the date and time using 24hour clock.
     *
     * @return The date and time in 24hour format.
     */
    public String getTotalDateWithTime() {
        //Creates a new date time object.
        DateTime date = new DateTime();
        //Creates the pattern for date and time. kk refers to 24hour clock.
        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd kk:mm");
        //Returns the date and time as a string.
        return formatter.print(date);
    }

    /**
     * Gets the time using 24hour clock.
     *
     * @return The Time in 24hour format.
     */
    public String getHourAndMin() {
        //Creates a new date time object.
        DateTime time = new DateTime();
        //Creates the pattern for time, kk refers to 24hour clock.
        DateTimeFormatter formatter = DateTimeFormat.forPattern("kk:mm");
        //Returns the time as a string.
        return formatter.print(time);
    }

    /**
     * Gets the current day name e.g Monday.
     *
     * @return The current date name as a String.
     */
    public String dayName() {
        //Creates a new date time object.
        DateTime dateName = new DateTime();
        //Creates the pattern for time, EE refers to date name.
        DateTimeFormatter formatter = DateTimeFormat.forPattern("EEEE");
        //Returns the day name as a string.
        String dayName = formatter.print(dateName);

        return dayName;
    }

    /**
     * This method validates whether the goal start times and end times are valid.
     * In terms of whether the end date is earlier than start date and vice versa.
     * This uses the Joda Time library using in built functions after() , before() and hoursBetween()
     *
     * @param startGoal Start time of the goal
     * @param endGoal   End time of the goal.
     * @return Whether the values are valid, true meaning valid.
     */
    public Boolean validateDates(String startGoal, String endGoal) {
        boolean dateCheck = false;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("kk:mm");
        //Initialises values to null to ensure, no pre-existing value is present.
        DateTime start = null;
        //Initialises values to null to ensure, no pre-existing value is present.
        DateTime end = null;
        //This parses the string value into a time date value to gain access to Joda methods.
        start = formatter.parseDateTime(startGoal);
        //This parses the string value into a time date value to gain access to Joda methods.
        end = formatter.parseDateTime(endGoal);
        //Calculates the difference between the times.
        String difference = String.valueOf(valueOf(Hours.hoursBetween(start, end)));
        //Removes pt and h from the returned difference.
        String removeSpecCharFrom = difference.replaceAll("PT", "").replaceAll("H", "").trim();
        int hourDifference = Integer.parseInt(removeSpecCharFrom);
        //Gets the current year for the start time
        start.getYear();
        //Checks to see whether the start and end dates the same.
        boolean resultOfDate = start.toLocalDate().isEqual(end.toLocalDate());
        //Checks to see if there's a negative difference in the difference betweeen the times
        String validateIfNegativeDifference = String.valueOf(hourDifference);
        //Gets the current year for the end time
        end.getYear();
        //Gets year and month
        getYearAndMonth();
        /*
            * This expression evaluates too, the start isn't after the end but is before end
            *If the difference between the hours is more than one, then this acceptable.
            * Checks also if the times don't have a negtive difference.
            *Checks that both times on the same date
            *And that the start date doesn't equal the end date.
         */

        if (start.isBefore(end) && !start.isAfter(end) && hourDifference >= 1 && !validateIfNegativeDifference.contains("-") && !start.equals(end) && resultOfDate) {
            activityHelper.createToastWithText("Correct times for set goal!");
            dateCheck = true;
        } else if (start.equals(end)) {
            //If the start date is the same as end then toast is thrown to explain the issue.
            activityHelper.createToastWithText("You can't have the same start and end time");
            dateCheck = false;

        } else if (hourDifference <= 1) {
            //If the difference is less than an hour then toast is thrown to explain the issue.
            activityHelper.createToastWithText("Times have to at least be 1 hour apart");
            dateCheck = false;

        } else if (start.isBefore(end)) {
            //If start is before end time then toast is thrown to explain the error.
            activityHelper.createToastWithText("Start time has to be before the end time");
            dateCheck = false;

        } else if (start.isAfter(end)) {
            //If start is after end time then toast is thrown to explain the error.
            activityHelper.createToastWithText("Start time has to be before the end time");
            dateCheck = false;

        } else if (end.isAfter(start)) {
            //If end is after start time then toast is thrown to explain the error.
            activityHelper.createToastWithText("end time has to be after the start time");
            dateCheck = false;

        } else if (!resultOfDate) {
            //If dates don't match then then toast is thrown to explain the error.
            activityHelper.createToastWithText("Dates don't match");
        } else if (validateIfNegativeDifference.contains("-")) {
            //If negative difference between times is present then toast error is thrown.
            activityHelper.createToastWithText("Can't have a negative difference");
            dateCheck = false;

        }
        //Returns whether the dates are valid.
        return dateCheck;
    }
}

