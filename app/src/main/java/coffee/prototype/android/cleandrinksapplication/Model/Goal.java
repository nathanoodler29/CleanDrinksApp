package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 28/03/2017.
 */

/**
 * Class comprises of getters/setters for passing values to the database.
 */
public class Goal {

    private double waterGoal;
    private int exerciseLevels;
    private String endTimeGoal;
    private String startTimeGoal;

    private double expectedWaterGoal;

    public String getWaterGoalStr() {
        return waterGoalStr;
    }

    public void setWaterGoalStr(String waterGoalStr) {
        this.waterGoalStr = waterGoalStr;
    }

    private String waterGoalStr;

    //    private int userIDFk;
//
//
//    private int goalID;


    public Goal() {

    }


    public Goal(double waterGoal) {
        this.waterGoal = waterGoal;


    }

    /**
     * Overloaded constructor is used for storing the waterGoal, startime and end time.
     *
     * @param waterGoalStr Relates to the water goal as a string
     * @param startTime    Relates to the start time of a goal
     * @param endTime      Relates to the end time of a goal.
     */
    public Goal(String waterGoalStr, String startTime, String endTime) {
        this.startTimeGoal = startTime;
        this.endTimeGoal = endTime;
        this.waterGoalStr = waterGoalStr;
    }


    public double getExpectedWaterGoal() {
        return expectedWaterGoal;
    }

    public double setExpectedWaterGoal(double expectedWaterGoal) {
        this.expectedWaterGoal = expectedWaterGoal;
        return expectedWaterGoal;
    }


    public double getWaterGoal() {
        return waterGoal;
    }

    public void setWaterGoal(double waterGoal) {
        this.waterGoal = waterGoal;
    }

    /**
     * Relates to a users exercise levels, this is then used for calculating hydration.
     *
     * @return exerciseLevels Value.
     */
    public int getExerciseLevels() {
        return exerciseLevels;
    }

    public void setExerciseLevels(int exerciseLevels) {
        this.exerciseLevels = exerciseLevels;
    }

    public String getStartTimeGoal() {
        return startTimeGoal;
    }

    public String setStartTimeGoal(String startTimeGoal) {
        this.startTimeGoal = startTimeGoal;
        return startTimeGoal;
    }

    public String getEndTimeGoal() {
        return endTimeGoal;
    }

    public void setEndTimeGoal(String endTimeGoal) {
        this.endTimeGoal = endTimeGoal;
    }


}



