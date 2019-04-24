package kr.ac.pusan.cse.category5.HolderV;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ac.pusan.cse.category5.Interface.ItemClickListener;
import kr.ac.pusan.cse.category5.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name_restaurant;
    public ImageView image_restaurant;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }




    public RestaurantViewHolder(View itemView) {
        super(itemView);

        name_restaurant = (TextView)itemView.findViewById(R.id.name_restaurant);
        image_restaurant = (ImageView)itemView.findViewById(R.id.image_restaurant);//imageView

        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);


    }
}
