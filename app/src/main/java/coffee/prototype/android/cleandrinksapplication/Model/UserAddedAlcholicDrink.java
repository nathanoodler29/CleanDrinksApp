package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 25/04/2017.
 */

public class UserAddedAlcholicDrink extends Drink implements CustomAlcholicDrink {

    private double alcoholUnits;
    private String drinkName;
    private double drinkVolume;
    private String drinkType;
    private int imagePath;
    private int drinkQuanitiy;
    private String date;

    private double alcoholPercentage;



    public UserAddedAlcholicDrink(){
        super();

    }

    public UserAddedAlcholicDrink(String drinkName, double drinkVolume, String drinkType, double units,int imagePath) {
        super();
        //I think this will probabbly be better, can just pass the setters in the constructor.
        this.drinkName = drinkName;
        this.drinkVolume = drinkVolume;
        this.alcoholUnits = units;
        this.drinkType = drinkType;
        this.imagePath = imagePath;


    }

    //http://www.nhs.uk/Livewell/alcohol/Pages/alcohol-units.aspx
    @Override
    public Double calculateUnitTotal(double volume, double strength) {
        double units = (volume * strength / 1000);

        return units;

    }


    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

}
