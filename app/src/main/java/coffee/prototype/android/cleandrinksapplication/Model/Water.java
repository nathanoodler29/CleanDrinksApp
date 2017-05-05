package coffee.prototype.android.cleandrinksapplication.Model;

import java.text.DecimalFormat;

/*
 * created by Noodle on 28/03/2017.
 */

/**
 * Comprises of simple getters and setters for a Water based drink.
 */
public class Water extends Drink {

    private double caffine;
    private String drinkName;
    private double drinkVolume;
    private String drinkType;
    private int imagePath;
    private int drinkQuanitiy;
    private String date;


    public Water() {
        super();

    }


    public Water(String drinkName, double drinkVolume, String drinkType, double caffine, int imagePath) {
        super();
        //I think this will probabbly be better, can just pass the setters in the constructor.
        this.drinkName = drinkName;
        this.drinkVolume = drinkVolume;
        this.caffine = caffine;
        this.drinkType = drinkType;
        this.imagePath = imagePath;


    }


    /**
     * Calculates a users water intake based on their weight and exercise levels.
     * References for calculation:http://www.indiatimes.com/health/tips-tricks/this-formula-will-help-you-figure-out-how-much-water-you-need-to-drink-every-day-249824.html
     * References for calculation:http://www.mynetdiary.com/water-needs.html
     *
     * @param weight   Users specific weight.
     * @param exercise Amount of exercise from a user,
     * @return Returns the suggested amount of water to drink, in 2 decimal places.
     */
    public double calculateTotalWater(double weight, double exercise) {
        double totalWater = (exercise * 0.7) + (weight / 30.00);
        //rounds the total water double to decimal places
        return Double.parseDouble(new DecimalFormat("#.##").format(totalWater));


    }


}


