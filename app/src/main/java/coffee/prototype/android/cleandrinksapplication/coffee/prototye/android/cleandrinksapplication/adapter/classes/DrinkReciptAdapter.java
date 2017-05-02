package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.ActivityHelper;
import coffee.prototype.android.cleandrinksapplication.MainActivity;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.R;

/**
 * created by noodle on 19/04/2017.
 */

public class  DrinkReciptAdapter extends RecyclerView.Adapter<DrinkReciptAdapter.ViewHolder>  {

    private ArrayList<Drink> drinksRecipt= new ArrayList<Drink>();
    private Context mContext;
    private ActivityHelper helper = new ActivityHelper();
    private MainActivity activity= new MainActivity();



    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        public TextView drinkName;
        public TextView drinkTime;
        private CardView drinkNameCard;
        private CardView drinksDate;


        public ViewHolder(View v) {
            super(v);
        }
    }

    public DrinkReciptAdapter(Context context, ArrayList<Drink> drinks) {
        drinksRecipt = drinks;
        mContext = context;

    }

    @Override
    public DrinkReciptAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.drinks_recipt_list_view, parent, false);

        //set the view holder to the layout passed.
        final DrinkReciptAdapter.ViewHolder viewHolder = new DrinkReciptAdapter.ViewHolder(v);

        viewHolder.drinkName = (TextView) v.findViewById(R.id.drinks_recipt_row_drink_name);

        viewHolder.drinkTime = (TextView) v.findViewById(R.id.drinks_recipt_row_drink_time);

        viewHolder.drinkNameCard = (CardView) v.findViewById(R.id.drinks_recipt_card);



        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DrinkReciptAdapter.ViewHolder holder, final int position) {

        holder.drinkName.setText(drinksRecipt.get(position).getDrinkName());
        holder.drinkTime.setText(drinksRecipt.get(position).getDate());

      if (drinksRecipt.get(position).getDrinkName().matches(".*units:[0-9].*")){
            holder.drinkNameCard.setCardBackgroundColor(Color.parseColor("#76bda2"));
        }else if (drinksRecipt.get(position).getDrinkName().matches(".*ml.*")){
            holder.drinkNameCard.setCardBackgroundColor(Color.parseColor("#9ce0f1"));
        }else if (drinksRecipt.get(position).getDrinkName().matches(".*caffeine:[0-9]{2,3}[mg].*")){
          holder.drinkNameCard.setCardBackgroundColor(Color.parseColor("#e5d4b2"));
      }

        //if drink name = this
        //then change this // reference the middle item
    }

    @Override
    public int getItemCount() {

        return drinksRecipt.size();
    }


}

