package kr.ac.pusan.cse.category5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.ac.pusan.cse.category5.HolderV.RestaurantViewHolder;
import kr.ac.pusan.cse.category5.HolderV.UserViewHolder;
import kr.ac.pusan.cse.category5.UserModel.Restaurants;
import kr.ac.pusan.cse.category5.UserModel.User;

public class UserList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference userList;

//    String phoneNumber = "";

    FirebaseRecyclerAdapter<User, UserViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        database = FirebaseDatabase.getInstance();
        userList = database.getReference("User");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_user);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadListUser();

        //Intent
//        if (getIntent() != null)
//            phoneNumber = getIntent().getStringExtra("PhoneNumber");
//        if (!phoneNumber.isEmpty()) {
//            loadListUser(phoneNumber);
//        }
//
    }

    private void loadListUser() {
        adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(User.class, R.layout.user_item, UserViewHolder.class,userList) {
            @Override
            protected void populateViewHolder(final UserViewHolder viewHolder, final User model, final int position) {
                viewHolder.name_user.setText(model.getName());
//                viewHolder.phone_user.setText(phoneNumber);

            }
        };
        recyclerView.setAdapter(adapter);
    }
}
