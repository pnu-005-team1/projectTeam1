package kr.ac.pusan.cse.category5.HolderV;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ac.pusan.cse.category5.Interface.ItemClickListener;
import kr.ac.pusan.cse.category5.R;

public class UserViewHolder extends RestaurantViewHolder implements View.OnClickListener {
    public TextView name_user;
    public TextView phone_user;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public UserViewHolder(View itemView) {
        super(itemView);

        name_user = (TextView)itemView.findViewById(R.id.username);
//        phone_user = (TextView)itemView.findViewById(R.id.phoneNumber);


        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);


    }

}
