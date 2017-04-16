package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 10/04/2017.
 */

public class Coffee extends Drink implements GenericCaffineDrink {

    private double caffine;
    private String drinkName;
    private double drinkVolume;
    private String drinkType;
    private int imagePath;




    public Coffee() {
        super();

    }

    //Need a constructor to pass in all the info, so this can then be added to the database curosr.
    // so the flow is, user clicks 'add drink' then fromt here they set all this info from actiivty.
    // THen this gets added to a database.
    //then we display it.

    public Coffee(String drinkName, double drinkVolume, String drinkType, double caffine,int imagePath) {
        super();
        //I think this will probabbly be better, can just pass the setters in the constructor.
        this.drinkName = drinkName;
        this.drinkVolume = drinkVolume;
        this.caffine = caffine;
        this.drinkType = drinkType;
        this.imagePath = imagePath;


    }

    @Override
    public void setCaffineContent(double Caffine) {
        this.caffine = Caffine;
    }

    @Override
    public double getCaffineContent() {
        return caffine;
    }



    public double calculateCaffineAmount(int numberOfShots){
        double oneShotStrength = 92;
        double totalAmountCaffine  = (numberOfShots * oneShotStrength);

        return totalAmountCaffine;
    }
}


