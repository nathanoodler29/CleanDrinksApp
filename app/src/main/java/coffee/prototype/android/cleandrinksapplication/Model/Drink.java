package coffee.prototype.android.cleandrinksapplication.Model;

/*
 * created by Nathan on 10/04/2017.
 */


/**
 * THis class comprises of the simple getter and setter methods related to a drink.
 * This is to avoid duplication of methods in every single drink sub class.
 */
public class Drink {

    private String drinkName;
    private double drinkVolume;
    private int drinkQuantity;
    private String drinkType;

    private String date;


    private int imagePath;

    private String dateName;


    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


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
