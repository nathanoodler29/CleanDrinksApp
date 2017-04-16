package coffee.prototype.android.cleandrinksapplication.coffee.prototye.android.cleandrinksapplication.adapter.classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.R;

/**
 * created by Noodle on 12/04/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Drink> mdrinks;
    private Context mContext;

    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        public TextView drinkHeading;
        public ImageView drinkImage;

        public ViewHolder(View v) {
            super(v);
        }
    }
    public MainAdapter(Context context, ArrayList<Drink>drinks){
        mdrinks = drinks;
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.espresso_drinks_recycler_list_view, parent, false);
        //set the view holder to the layout passed.
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.drinkHeading = (TextView)v.findViewById(R.id.espresso_cat_card_title);

        viewHolder.drinkImage = (ImageView) v.findViewById(R.id.espresso_cat_card__image);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //binds data to the right ele,ent
        //retrieves the current drink from the arraylist
//        Drink currentDrink = mdrinks.get(position);
        //gets the name fromt he current drink and sets the text to the textview.
//        holder.drinkHeading.setText(currentDrink.getDrinkName());
//        holder.drinkHeading.setText(mdrinks.get(position).toString());

                holder.drinkHeading.setText(mdrinks.get(position).getDrinkName());
                holder.drinkImage.setImageResource(mdrinks.get(position).getImagePath());



    }

    @Override
    public int getItemCount() {

        return mdrinks.size();
    }

}
//    private ArrayList<Drink>mdrinks;
//
//    private Context mContext;
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder{
//        public TextView espressoTitle;
//        public ImageView espressoImage;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//
//    public MainAdapter(Context context, ArrayList<Drink>drinks){
//        mContext = context;
//        mdrinks = drinks;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(
//                parent.getContext()).inflate(
//                R.layout.espresso_drinks_recycler_list_view, parent, false);
//        ViewHolder viewHolder = new ViewHolder(v);
//        viewHolder.espressoTitle = (TextView)v.findViewById(R.id.espresso_cat_card_title);
//
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Drink currentDrink = mdrinks.get(position);
//        holder.espressoTitle.setText(currentDrink.getDrinkName());
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mdrinks.size();
//    }