package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 23/04/2017.
 */

public class AlcoholicDrink extends Drink implements GenericAlcoholicDrink {

    private double alcoholUnits;
    private String drinkName;
    private double drinkVolume;
    private String drinkType;
    private int imagePath;
    private int drinkQuanitiy;
    private String date;

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    private double alcoholPercentage;




    public AlcoholicDrink(){
        super();

    }

    /***
     * Builds a drink, by using the classes getter/setter methods from the super class Drink.
     * @param drinkName Refers to the dink name
     * @param drinkVolume Refers to the volume
     * @param drinkType Refers to the  drink type
     * @param units Refers to the units.
     * @param imagePath Refers to the image icon.
     */
    public AlcoholicDrink(String drinkName, double drinkVolume, String drinkType, double units,int imagePath) {
        super();
        this.drinkName = drinkName;
        this.drinkVolume = drinkVolume;
        this.alcoholUnits = units;
        this.drinkType = drinkType;
        this.imagePath = imagePath;


    }

//
//    public Double calculateUnitTotal(double volume, double strength) {
//        double units = (volume * strength / 1000);
//
//        return units;
//
//    }

    @Override
    public void setUnitContent(double alcoholUnits) {

    }

    @Override
    public double getUnitContent() {
        return 0;
    }

////      @Override
//public void setUnitContent(double alcoholUnits) {
//    this.alcoholUnits = alcoholUnits;
//}
//
//    @Override
//    public double getUnitContent() {
//        return alcoholUnits;
//    }
}
