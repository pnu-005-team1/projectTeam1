package kr.ac.pusan.cse.category5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Queue;

import kr.ac.pusan.cse.category5.Coms.Common;
import kr.ac.pusan.cse.category5.HolderV.RestaurantViewHolder;
import kr.ac.pusan.cse.category5.UserModel.Rating;
import kr.ac.pusan.cse.category5.UserModel.Restaurants;

public class RestaurantDetails extends AppCompatActivity implements RatingDialogListener {

    TextView name_restaurant, description_restaurant, location_restaurant, information_1_restaurant, information_2_restaurant;
//    ImageView image_restaurant;
    FloatingActionButton btnRating;
    RatingBar ratingBar;
    String restaurantId="";
    FirebaseDatabase database;
    DatabaseReference restaurantList;
    DatabaseReference ratingDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);


        database = FirebaseDatabase.getInstance();
        restaurantList=database.getReference("Restaurant");
        ratingDB=database.getReference("Rating");

        btnRating=(FloatingActionButton)findViewById(R.id.btnrating);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDialog();
            }
        });


        name_restaurant = (TextView)findViewById(R.id.name_restaurant);
        description_restaurant = (TextView)findViewById(R.id.description_restaurant);
        location_restaurant = (TextView)findViewById(R.id.location_restaurant);
        information_1_restaurant = (TextView)findViewById(R.id.information_1_restaurant);
        information_2_restaurant = (TextView)findViewById(R.id.information_2_restaurant);
//        image_restaurant=(ImageView)findViewById(R.id.image_restaurant);


        if(getIntent() !=null)
            restaurantId = getIntent().getStringExtra("MenuId");
        if(!restaurantId.isEmpty())
        {
            getDetailRestaurant(restaurantId);
            getRatingFood(restaurantId);
        }
    }

    private void showRatingDialog(){
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not Good", "Good", "Very Good", "Excellent" ))
                .setDefaultRating(1)
                .setTitle("Restaurant Rate")
                .setDescription("Please Rate This Restaurant and Give Some Feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please give your feedback here")
                .setHintTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(RestaurantDetails.this)
                .show();
    }

    private void getRatingFood(String restaurantId){

        //com.google.firebase.database.Query restaurantRating = ratingDB.orderByChild("restaurantId").equalTo(restaurantId);
        Query restaurantRating = ratingDB.orderByChild("restaurantId").equalTo(restaurantId);
        restaurantRating.addValueEventListener(new ValueEventListener() {
            int count = 0, sum = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRateValue());
                    count++;
                }
                if (count != 0)
                {
                    float average = sum/count;
                    ratingBar.setRating(average);
                }
                else
                {
                    ratingBar.setRating(sum);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getDetailRestaurant(String restaurantId) {

        restaurantList.child(restaurantId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Restaurants restaurants=dataSnapshot.getValue(Restaurants.class);

//                Picasso.with(getBaseContext()).load(restaurants.getImage())
//                        .into();

                description_restaurant.setText(restaurants.getDescription());

                location_restaurant.setText(restaurants.getLocation());

                information_1_restaurant.setText(restaurants.getInformation_1());

                information_2_restaurant.setText(restaurants.getInformation_2());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        //Uploading rating to FirebaseDB
         final Rating rating = new Rating(Common.currentUser.getName(),
                restaurantId,
                String.valueOf(i),
                s);
         ratingDB.child(Common.currentUser.getName()).child(restaurantId).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.child(Common.currentUser.getName()).child(restaurantId).exists())
                 {
                     //remove
                     ratingDB.child(Common.currentUser.getName()).child(restaurantId).removeValue();
                     //update
                     ratingDB.child(Common.currentUser.getName()).child(restaurantId).setValue(rating);
                     finish();
                 }
                 else
                 {
                     //upd
                     ratingDB.child(Common.currentUser.getName()).child(restaurantId).setValue(rating);
                     finish();
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


    }
}
