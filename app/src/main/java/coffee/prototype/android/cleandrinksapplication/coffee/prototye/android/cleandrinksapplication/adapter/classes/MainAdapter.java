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
import coffee.prototype.android.cleandrinksapplication.MainActivity;
import coffee.prototype.android.cleandrinksapplication.Model.Drink;
import coffee.prototype.android.cleandrinksapplication.R;

/**
 * created by Noodle on 12/04/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Drink> mdrinks;
    private Context mContext;
    private ActivityHelper helper = new ActivityHelper();
    private MainActivity activity= new MainActivity();


    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        public TextView drinkHeading;
        public ImageView drinkImage;
        public Button addDrink;
        public Button cancelDrink;


        public ViewHolder(View v) {
            super(v);
        }
    }

    public MainAdapter(Context context, ArrayList<Drink> drinks) {
        mdrinks = drinks;
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.espresso_drinks_recycler_list_view, parent, false);
        //set the view holder to the layout passed.
        final ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.drinkHeading = (TextView) v.findViewById(R.id.espresso_cat_card_title);

        viewHolder.drinkImage = (ImageView) v.findViewById(R.id.espresso_cat_card__image);

        viewHolder.addDrink = (Button) v.findViewById(R.id.add_button_template);

        viewHolder.cancelDrink = (Button) v.findViewById(R.id.remove_button_template);

        return viewHolder;
    }


        @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //binds data to the right ele,ent
        //retrieves the current drink from the arraylist
//        Drink currentDrink = mdrinks.get(position);
        //gets the name fromt he current drink and sets the text to the textview.
//        holder.drinkHeading.setText(currentDrink.getDrinkName());
//        holder.drinkHeading.setText(mdrinks.get(position).toString());

        holder.drinkHeading.setText(mdrinks.get(position).getDrinkName());
        holder.drinkImage.setImageResource(mdrinks.get(position).getImagePath());

        holder.addDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = mContext.getApplicationContext();

                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(400);

//
//                helper.createToastWithText("clicked Plus icon");
//                helper.createToastWithText("position from plus icon" + position);
//                helper.createToastWithText("positon from mdirnks" + mdrinks.get(position).getDrinkName());
                String drinkID =  helper.printAllFromDb(mContext.getApplicationContext(),mdrinks.get(position).getDrinkName());
                String userID = String.valueOf(helper.getUserId(context));
                helper.addDrinkQuanitiyValues(context,drinkID,userID);
                if(helper.checkDrinksQuantiiyValuesExist(context)>0){
                    helper.updateUsingCursor(context,drinkID);
                    helper.createToastWithText("Reading after update...");
                    helper.checkDrinksQuantiiyValuesExist(context);
                }




            }
        });
      holder.cancelDrink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Context context = mContext.getApplicationContext();

              Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
              vibrator.vibrate(100);
            helper.createToastWithText("Clicked cancel");
              String drinkID =  helper.printAllFromDb(mContext.getApplicationContext(),mdrinks.get(position).getDrinkName());

              String lastAddedId = helper.getLastAddedDrinksQuanitiy(context,drinkID);
              helper.createToastWithText("after last added id method");
              helper.getIDcHEC(context,lastAddedId);
              helper.upateIDToHave0Value(context,lastAddedId);
              helper.checkDrinksQuantiiyValuesExist(context);
              notifyDataSetChanged();




          }
      });


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
