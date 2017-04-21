package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
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
 * Created by noodle on 19/04/2017.
 */

public class DrinkReciptAdapter extends RecyclerView.Adapter<DrinkReciptAdapter.ViewHolder>  {

    private ArrayList<Drink> drinksRecipt= new ArrayList<Drink>();
    private Context mContext;
    private ActivityHelper helper = new ActivityHelper();
    private MainActivity activity= new MainActivity();


    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        public TextView drinkName;
        public TextView drinkTime;



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

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DrinkReciptAdapter.ViewHolder holder, final int position) {

        holder.drinkName.setText(drinksRecipt.get(position).getDrinkName());
        holder.drinkTime.setText(drinksRecipt.get(position).getDate());



    }

    @Override
    public int getItemCount() {

        return drinksRecipt.size();
    }


}

