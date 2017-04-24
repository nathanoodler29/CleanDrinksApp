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



    public AlcoholicDrink(){
        super();

    }

    public AlcoholicDrink(String drinkName, double drinkVolume, String drinkType, double units,int imagePath) {
        super();
        //I think this will probabbly be better, can just pass the setters in the constructor.
        this.drinkName = drinkName;
        this.drinkVolume = drinkVolume;
        this.alcoholUnits = units;
        this.drinkType = drinkType;
        this.imagePath = imagePath;


    }


    @Override
    public void setUnitContent(double alcoholUnits) {

    }

    @Override
    public double getUnitContent() {
        return 0;
    }
}
