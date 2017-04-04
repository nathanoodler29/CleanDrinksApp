package coffee.prototype.android.cleandrinksapplication.Model;

import java.text.DecimalFormat;

/**
 * created by Noodle on 28/03/2017.
 */

public class Water {


    //http://www.indiatimes.com/health/tips-tricks/this-formula-will-help-you-figure-out-how-much-water-you-need-to-drink-every-day-249824.html
//http://www.mynetdiary.com/water-needs.html

    public double calculateTotalWater(double weight, double exercise) {
        double totalWater = (exercise * 0.7) + (weight / 30.00);
        //rounds the total water double to decimal places
        return Double.parseDouble(new DecimalFormat("#.##").format(totalWater));


    }


}


