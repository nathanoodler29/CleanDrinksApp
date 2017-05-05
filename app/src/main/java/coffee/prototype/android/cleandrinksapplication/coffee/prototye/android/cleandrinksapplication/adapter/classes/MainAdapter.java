package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.DrinkRecipt;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.R;
import coffee.prototype.android.cleandrinksapplication.data.DrinksReceiptQueries;

/**
 * created by Noodle on 12/04/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Drink> mdrinks;
    private Context mContext;
    private ActivityHelper helper = new ActivityHelper();
    private DrinksReceiptQueries drinksQueryHelper = new DrinksReceiptQueries();

    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        private TextView drinkHeading;
        private ImageView drinkImage;
        private Button addDrink;
        private Button cancelDrink;


        public ViewHolder(View v) {
            super(v);
        }
    }

    public MainAdapter(Context context, ArrayList<Drink> drinks) {
        mdrinks = drinks;
        mContext = context;

    }

    /**
     * Initialises all of the layout elements which will be found in the recycler view.
     * Reference: This code for the View holder itself is from Android Studio Cookbook  Mike van Drongelen (isbn 1785286188) Pages 40-45.
     * Note all action listener code etc, was my own implementation.
     *
     * @param parent   Refers to the Activites context.
     * @param viewType Refers to the layout used for the View holder.
     * @return The elements related to the View holder, which are found in the recycler view.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Layout inflater notifies the Android screen, using declared xml elements, i.e drinks receipt title.
        //The layout resource is passed, to populate the view of the view holder.
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.espresso_drinks_recycler_list_view, parent, false);
        //set the view holder to the layout passed.
        final ViewHolder viewHolder = new ViewHolder(v);
        //Refers the name of the drink.
        viewHolder.drinkHeading = (TextView) v.findViewById(R.id.espresso_cat_card_title);
        //Refers the drink image icon.
        viewHolder.drinkImage = (ImageView) v.findViewById(R.id.espresso_cat_card__image);
        //Refers the add drink button.
        viewHolder.addDrink = (Button) v.findViewById(R.id.add_button_template);
        //Refers the add cancel button.
        viewHolder.cancelDrink = (Button) v.findViewById(R.id.remove_button_template);
        //Returns the view.
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Gets the drink name, and adds it to the text view to display.
        holder.drinkHeading.setText(mdrinks.get(position).getDrinkName());
        //Gets the drink image, and adds it to the Image view to display.

        holder.drinkImage.setImageResource(mdrinks.get(position).getImagePath());

        holder.addDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addDrink.setContentDescription("Add drink button,for adding this to the drink log");
                Context context = mContext.getApplicationContext();
                //Sets a mild vibrate once button is clicked.
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                //Sets duration of vibrate.
                vibrator.vibrate(100);
                //Returns the drinks ID by checking which button has been clicked, and getting the name of the drink from this.
                String drinkID = helper.getDrinksIDFromDrinkName(mContext.getApplicationContext(), mdrinks.get(position).getDrinkName());
                //Gets the current users id.
                String userID = String.valueOf(helper.getUserId(context));
                //Adds the drink to the drinks quanitiy table, which tracks the users drinks added.
                helper.addDrinkQuanitiyValues(context, drinkID, userID);
                //Checks to see if the table has any values populated,
                if (drinksQueryHelper.checkDrinksQuantiiyValuesExist(context) > 0) {
                    //Adds 1 to the quanitiy of the drink
                    helper.updateUsingCursor(context, drinkID);
                    //Returns the number of entries  in the table.
                    drinksQueryHelper.checkDrinksQuantiiyValuesExist(context);
                }


            }
        });

        holder.cancelDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cancelDrink.setContentDescription("delete drink button,for deleting in the drink log");

                Context context = mContext.getApplicationContext();
                //Sets a mild vibrate once button is clicked.
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                //Sets duration of vibrate.
                vibrator.vibrate(100);
                //prints clicked delete.
                helper.createToastWithText("Clicked delete");
                //Returns the drinks ID by checking which button has been clicked, and getting the name of the drink from this.
                String drinkID = helper.getDrinksIDFromDrinkName(mContext.getApplicationContext(), mdrinks.get(position).getDrinkName());
                //Removes the drink from the arraylist, so it's no longer visible in the activity.
                mdrinks.remove(position).getDrinkName();
                //Returns the drink that was latest added by the drinks, id and checking which was the latest added.
                String lastAddedId = helper.getLastAddedDrinksQuanitiy(context, drinkID);
                //Checks to see if the the latest drink added, actually exists otherwise error would be thrown in db.
                drinksQueryHelper.checkIfDrinksIDExists(context, lastAddedId);
                //Deletes the drink.
                drinksQueryHelper.deleteDrinkFromTableBasedOnDrinkID(context, lastAddedId);
                //Returns the number of items from the table, this was more used for Debugging.
                drinksQueryHelper.checkDrinksQuantiiyValuesExist(context);
                //Notifies the adapter that the size of the arraylist has changed, therefore it should update itself.
                notifyDataSetChanged();


            }
        });


    }

    @Override
    public int getItemCount() {
        //Returns the number of elements in the arraylist.
        return mdrinks.size();
    }


}
