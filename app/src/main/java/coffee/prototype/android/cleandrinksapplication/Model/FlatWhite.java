package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 10/04/2017.
 */

public class FlatWhite extends Drink implements GenericCaffineDrink {

    Drink drink = new Drink();
    private double caffine;


    private double user;

    public FlatWhite() {
        super();

    }

    //Need a constructor to pass in all the info, so this can then be added to the database curosr.
    // so the flow is, user clicks 'add drink' then fromt here they set all this info from actiivty.
    // THen this gets added to a database.
    //then we display it.

    public FlatWhite(String drinkName, double drinkVolume, double totalDrink, double caffine) {
        super();
        drinkName = drink.getDrinkName();
        drinkVolume = drink.getDrinkVolume();
        totalDrink = drink.getTotalDrink();
        caffine = getCaffineContent();


    }

    @Override
    public void setCaffineContent(double Caffine) {
        this.caffine = Caffine;
    }

    @Override
    public double getCaffineContent() {
        return caffine;
    }
}


