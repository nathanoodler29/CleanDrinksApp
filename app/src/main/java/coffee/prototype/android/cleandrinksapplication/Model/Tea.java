package coffee.prototype.android.cleandrinksapplication.Model;

/**
 * created by Noodle on 22/04/2017.
 */

//https://www.goldenmoontea.com/blogs/tea/106689799-how-to-measure-caffeine-in-tea

public class Tea extends Drink implements GenericCaffineDrink{



    private double caffine;
    private String drinkName;
    private double drinkVolume;
    private String drinkType;
    private int imagePath;
    private int drinkQuanitiy;
    private String date;








    public Tea(){
        super();

    }
    public Tea(String drinkName, double drinkVolume,String drinkType, double caffine, int imagePath) {
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

    }

    @Override
    public double getCaffineContent() {
        return 0;
    }
}
