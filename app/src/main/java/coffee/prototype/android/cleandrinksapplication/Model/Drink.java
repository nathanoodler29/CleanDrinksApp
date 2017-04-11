package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * Created by Noodle on 10/04/2017.
 */

public class Drink {

    private String drinkName;
    private double drinkVolume;
    private double totalDrink;

    private int numberOfDrinks;


    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public double getDrinkVolume() {
        return drinkVolume;
    }

    public void setDrinkVolume(double drinkVolume) {
        this.drinkVolume = drinkVolume;
    }

    public double getTotalDrink() {
        return totalDrink;
    }

    public void setTotalDrink(double totalDrink) {
        this.totalDrink = totalDrink;
    }



}
