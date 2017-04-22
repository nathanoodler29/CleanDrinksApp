package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by noodle on 21/04/2017.
 */

public class Achievement {
    private String name;
    private String description;
    private int completionStatus;
    private int achievementAvatar;

    public Achievement() {

    }

    public Achievement(String name, String description, int achievementAvater) {
        this.name = name;
        this.description = description;
        this.completionStatus = completionStatus;
        this.achievementAvatar = achievementAvater;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(int completionStatus) {
        this.completionStatus = completionStatus;
    }

    public int getAchievementAvater() {
        return achievementAvatar;
    }

    public void setAchievementAvater(int achievementAvater) {
        this.achievementAvatar = achievementAvater;
    }


}
