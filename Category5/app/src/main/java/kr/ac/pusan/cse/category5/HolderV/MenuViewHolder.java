package kr.ac.pusan.cse.category5.HolderV;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ac.pusan.cse.category5.Interface.ItemClickListener;
import kr.ac.pusan.cse.category5.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView){
        super(itemView);
        txtMenuName = (TextView)itemView.findViewById(R.id.name_menu);
        imageView = (ImageView)itemView.findViewById(R.id.image_menu);//imageView

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view){

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

}
