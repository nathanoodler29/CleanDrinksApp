package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Nathan on 21/04/2017.
 */

public class Achievement {
    private String name;
    private String description;
    private int completionStatus;
    private int achievementAvatar;

    //Default constructor for calling methods.
    public Achievement() {

    }

    /**
     * Creates an Achievement
     * @param name Refers to the Achievement name.
     * @param description Refers to the Achievement description.
     * @param achievementAvater  Refers to the Achievement image.
     */
    public Achievement(String name, String description, int achievementAvater) {
        this.name = name;
        this.description = description;

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


    public int getAchievementAvater() {
        return achievementAvatar;
    }

    public void setAchievementAvater(int achievementAvater) {
        this.achievementAvatar = achievementAvater;
    }


}
