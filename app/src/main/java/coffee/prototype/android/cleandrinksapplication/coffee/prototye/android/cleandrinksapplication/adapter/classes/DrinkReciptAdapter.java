package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.R;

/**
 * created by noodle on 19/04/2017.
 */

public class DrinkReciptAdapter extends RecyclerView.Adapter<DrinkReciptAdapter.ViewHolder> {

    private ArrayList<Drink> drinksRecipt = new ArrayList<Drink>();
    private Context mContext;
    private ActivityHelper helper = new ActivityHelper();


    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        private TextView drinkName;
        private TextView drinkTime;
        private CardView drinkNameCard;
        private TextView drinksDate;
        private Button cancelDrink;


        public ViewHolder(View v) {
            super(v);
        }
    }

    public DrinkReciptAdapter(Context context, ArrayList<Drink> drinks) {
        drinksRecipt = drinks;
        mContext = context;

    }


    /**
     * Intiaises all of the layout elements which will be found in the recycler view.
     * Reference: This code for the View holder itself is from Android Studio Cookbook  Mike van Drongelen (isbn 1785286188) Pages 40-45.
     * Note all action listener code etc, was my own implementation.
     *
     * @param parent   Refers to the Activites context.
     * @param viewType Refers to the layout used for the View holder.
     * @return The elements related to the View holder, which are found in the recycler view.
     */
    @Override
    public DrinkReciptAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Layout inflater notifies the Android screen, using declared xml elements, i.e drinks receipt title.
        //The layout resource is passed, to populate the view of the view holder.
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.drinks_recipt_list_view, parent, false);

        //set the view holder to the layout passed.
        final DrinkReciptAdapter.ViewHolder viewHolder = new DrinkReciptAdapter.ViewHolder(v);
        //Refers the name of the drink.
        viewHolder.drinkName = (TextView) v.findViewById(R.id.drinks_recipt_row_drink_name);
        //Refers to date number and time.
        viewHolder.drinkTime = (TextView) v.findViewById(R.id.drinks_recipt_row_drink_time);
        //References the card, which the elements are placed within.
        viewHolder.drinkNameCard = (CardView) v.findViewById(R.id.drinks_recipt_card);
        //Refers to the date name.
        viewHolder.drinksDate = (TextView) v.findViewById(R.id.date_text);
        viewHolder.cancelDrink = (Button) v.findViewById(R.id.remove_icon);

        return viewHolder;
    }

    /**
     * This method adds all of the data to the elements from the layout.
     *
     * @param holder   Refers to the layout of the application for the recycler view.
     * @param position Refers to the position of the array list.
     */
    @Override
    public void onBindViewHolder(final DrinkReciptAdapter.ViewHolder holder, final int position) {
        //Populates the drink name xml text edit with the name of the drink.
        holder.drinkName.setText(drinksRecipt.get(position).getDrinkName());
        //Populates the drink name xml text edit with the name of the date and time of the drink when added.
        holder.drinkTime.setText(drinksRecipt.get(position).getDate());
        //Populates the drink name xml text edit with the name of the name of the drink when added.
        holder.drinksDate.setText(drinksRecipt.get(position).getDateName());

        //Checks to see if the drink name contains the keyword 'units:' followed with a number
        if (drinksRecipt.get(position).getDrinkName().matches(".*units:[0-9].*")) {
            //If the drink name does have this then the colour of the card is changed to a green.
            holder.drinkNameCard.setCardBackgroundColor(Color.parseColor("#76bda2"));
            //Checks to see if the drink name contains the keyword 'ml'
        } else if (drinksRecipt.get(position).getDrinkName().matches(".*ml.*")) {
            //If the drink name does have this then the colour of the card is changed to a light blue.
            holder.drinkNameCard.setCardBackgroundColor(Color.parseColor("#9ce0f1"));
        } else if (drinksRecipt.get(position).getDrinkName().matches(".*caffeine:[0-9]{1,2}.*")) {
            //If the drink name does have this then the colour of the card is changed to a beige.
            holder.drinkNameCard.setCardBackgroundColor(Color.parseColor("#e5d4b2"));
        }

        //References the button in the list view and creates a response to a user clicking the button
        holder.cancelDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cancelDrink.setContentDescription("delete drink button,for deleting from the drink log");
                Context context = mContext.getApplicationContext();
                //Sets vibration when the button is pressed.
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                //Sets the vibrate to a short burst.
                vibrator.vibrate(100);
                helper.createToastWithText("Deleting drink");
                //Returns the drinks id from getting the name of the drink, and returning the ID from the drinks table.
                String drinkID = helper.getDrinksIDFromDrinkName(mContext.getApplicationContext(), drinksRecipt.get(position).getDrinkName());
                //Deletes the drink from the arraylist, so it's no longer displayed in the view.
                drinksRecipt.remove(position).getDrinkName();
                //Gets the last added drink with the same drink ID with the latest date.
                String lastAddedId = helper.getLastAddedDrinksQuanitiy(context, drinkID);
                //Outputs if the drink has been deleted.
                helper.checkIfDrinksIDExists(context, lastAddedId);
                //Deletes the drink from the dateabase.
                helper.upateIDToHave0Value(context, lastAddedId);
                //Prints to see if the drink has been deleted.
                helper.checkDrinksQuantiiyValuesExist(context);
                //Updates the Drinks adapter to state a change has been present, and to refresh the values in the adapter.
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        //Returns the size of the arraylist
        return drinksRecipt.size();
    }
}


