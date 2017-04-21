package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 10/04/2017.
 */

public class Drink {

    public String testing;
    private String drinkName;
    private double drinkVolume;
    private int drinkQuantity;
    private String drinkType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;



    private int imagePath;



    private int numberOfDrinks;


    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public void setDrinkVolume(double drinkVolume) {
        this.drinkVolume = drinkVolume;
    }

    public double getDrinkVolume() {
        return drinkVolume;
    }



    public int getDrinkQuantity() {
        return drinkQuantity;
    }

    public void setDrinkQuantity(int drinkQuantity) {
        this.drinkQuantity = drinkQuantity;
    }

    public String getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(String drinkType) {
        this.drinkType = drinkType;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
