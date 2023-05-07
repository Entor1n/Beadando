package com.example.beadando;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> implements Filterable {

    private ArrayList<ShoppingItem> myShoppingItemData;
    private ArrayList<ShoppingItem> myShoppingItemDataAll;
    private Context myContext;
    private int lastPosition = -1;


    ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> itemsData){
        this.myShoppingItemData = itemsData;
        this.myShoppingItemDataAll = itemsData;
        this.myContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(myContext).inflate
                (R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ShoppingItemAdapter.ViewHolder holder, int position) {
        ShoppingItem currentItem = myShoppingItemData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(myContext, R.anim.rotation);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        } else{
            Animation animation = AnimationUtils.loadAnimation(myContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }

    }

    @Override
    public int getItemCount() {
        return myShoppingItemData.size();
    }

    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private Filter shoppingFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ShoppingItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = myShoppingItemDataAll.size();
                results.values = myShoppingItemDataAll;
            } else {
                String filterPatter = charSequence.toString().toLowerCase().trim();

                for(ShoppingItem item : myShoppingItemDataAll){
                    if(item.getName().toLowerCase().contains(filterPatter)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            myShoppingItemData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView myTitleText;
        private TextView myInfoText;
        private TextView myPriceText;
        private ImageView myItemImage;
        private RatingBar myRatingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            myTitleText = itemView.findViewById(R.id.itemTitle);
            myInfoText = itemView.findViewById(R.id.subTitle);
            myPriceText = itemView.findViewById(R.id.price);
            myItemImage = itemView.findViewById(R.id.itemImage);
            myRatingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bindTo(ShoppingItem currentItem) {
            myTitleText.setText(currentItem.getName());
            myInfoText.setText(currentItem.getInfo());
            myPriceText.setText(currentItem.getPrice());
            myRatingBar.setRating(currentItem.getRatedInfo());

            Glide.with(myContext).load(currentItem.getImageResource()).into(myItemImage);
            itemView.findViewById(R.id.add_to_cart).setOnClickListener(
                    view -> ((ShopListActivity)myContext).updateAlertIcon(currentItem));
            itemView.findViewById(R.id.delete).setOnClickListener(
                    view -> ((ShopListActivity)myContext).deleteItem(currentItem));
        }
    };
}


