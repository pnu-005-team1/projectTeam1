package kr.ac.pusan.cse.category5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import kr.ac.pusan.cse.category5.Database.Database;
import kr.ac.pusan.cse.category5.HolderV.RestaurantViewHolder;
import kr.ac.pusan.cse.category5.Interface.ItemClickListener;
import kr.ac.pusan.cse.category5.UserModel.Restaurants;

public class RestaurantList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference restaurantList;

    String categoryId="";

    FirebaseRecyclerAdapter<Restaurants, RestaurantViewHolder> adapter;

    //Favs
    Database localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        restaurantList = database.getReference("Restaurant");

        //LocalDB
        localDB = new Database(this);

        recyclerView  = (RecyclerView)findViewById(R.id.recycler_restaurants);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Intent
        if(getIntent()!= null)
            categoryId=getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty())
        {
            loadListRestaurant(categoryId);
        }
    }
    private void loadListRestaurant(String categoryId){
        adapter=new FirebaseRecyclerAdapter<Restaurants, RestaurantViewHolder>(Restaurants.class, R.layout.restaurant_item,RestaurantViewHolder.class, restaurantList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(final RestaurantViewHolder viewHolder, final Restaurants model, final int position) {
                viewHolder.name_restaurant.setText(model.getName());
//                viewHolder.description_restaurant.setText(model.getDescription());
//                viewHolder.location_restaurant.setText(model.getLocation());
//                viewHolder.information_1_restaurant.setText(model.getInformation_1());
//                viewHolder.information_2_restaurant.setText(model.getInformation_2());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.image_restaurant);

                //Favs
                if(localDB.isFavorite(adapter.getRef(position).getKey()))
                    viewHolder.fav_image.setImageResource(R.drawable.ic_star_black_24dp);

                viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!localDB.isFavorite(adapter.getRef(position).getKey()))
                        {
                            localDB.addToFavorites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_star_black_24dp);
                            Toast.makeText(RestaurantList.this, ""+model.getName()+"Added to Favorites", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            localDB.removeFromFavorites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_star_black_24dp);
                            Toast.makeText(RestaurantList.this, ""+model.getName()+"Removed to Favorites", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                final Restaurants clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent restaurantDetail = new Intent(RestaurantList.this, RestaurantDetails.class);
                        restaurantDetail.putExtra("MenuId", adapter.getRef(position).getKey());
                        startActivity(restaurantDetail);
                    }
                });



            }
        };

        recyclerView.setAdapter(adapter);

    }
}
