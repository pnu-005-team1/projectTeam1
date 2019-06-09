package kr.ac.pusan.cse.category5.HolderV;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ac.pusan.cse.category5.Interface.ItemClickListener;
import kr.ac.pusan.cse.category5.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name_restaurant;
    public ImageView image_restaurant, fav_image, share;
    public TextView description_restaurant;
    public TextView location_restaurant;
    public TextView information_1_restaurant;
    public TextView information_2_restaurant;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }




    public RestaurantViewHolder(View itemView) {
        super(itemView);

        name_restaurant = (TextView)itemView.findViewById(R.id.name_restaurant);
        image_restaurant = (ImageView)itemView.findViewById(R.id.image_restaurant);//imageView
        fav_image = (ImageView)itemView.findViewById(R.id.favorite);//imageView
        share = (ImageView)itemView.findViewById(R.id.share);//share
        description_restaurant=(TextView)itemView.findViewById(R.id.description_restaurant);//TextView
        location_restaurant=(TextView)itemView.findViewById(R.id.location_restaurant);
        information_1_restaurant=(TextView)itemView.findViewById(R.id.information_1_restaurant);
        information_2_restaurant=(TextView)itemView.findViewById(R.id.information_2_restaurant);

        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);


    }
}
