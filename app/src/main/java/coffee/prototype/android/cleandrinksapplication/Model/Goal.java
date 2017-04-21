package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 28/03/2017.
 */

public class Goal {

    private double waterGoal;
    private int exerciseLevels;
    private String endTimeGoal;
    private String startTimeGoal;


    private int userIDFk;


    private int goalID;

    public double getExpectedWaterGoal() {
        return expectedWaterGoal;
    }

    public double setExpectedWaterGoal(double expectedWaterGoal) {
        this.expectedWaterGoal = expectedWaterGoal;
        return expectedWaterGoal;
    }

    private double expectedWaterGoal;

    public Goal() {

    }


    public Goal(double waterGoal) {
//                this.goalID = goalID;
        this.waterGoal = waterGoal;
//                this.startTimeGoal = startTimeGoal;
//                this.endTimeGoal = endTimeGoal;

    }

    public Goal(String startTime,String endTime){
        this.startTimeGoal = startTime;
        this.endTimeGoal = endTime;

    }


    public double getWaterGoal() {
        return waterGoal;
    }

    public void setWaterGoal(double waterGoal) {
        this.waterGoal = waterGoal;
    }

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



