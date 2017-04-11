package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 10/04/2017.
 */

public interface Caffeine {




    public double calculateCaffeineCoffee(int weight,double drinkVolume, int amountOfBeans);

    //http://1chemistry.blogspot.co.uk/2012/10/extraction-of-caffeine-from-tea-leaves.html
    public double calculateCaffeineTea(int weight,double drinkVolume, int numOfTeaBags);

    //This interface would alloe extending of drinks, by adding a method for fizzy drinks.



}
