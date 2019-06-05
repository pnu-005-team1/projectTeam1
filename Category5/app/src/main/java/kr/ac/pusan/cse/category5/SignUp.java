package kr.ac.pusan.cse.category5;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import kr.ac.pusan.cse.category5.Helpers.LocHelper;
import kr.ac.pusan.cse.category5.UserModel.User;

public class SignUp extends AppCompatActivity {

    EditText edtPhone, edtName, edtPassword;
    Button btnSignup;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocHelper.onAttach(newBase,"en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (EditText)findViewById(R.id.edtName);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtPhone=(EditText)findViewById(R.id.edtPhone);

        btnSignup = (Button)findViewById(R.id.btnSignUp);

        Paper.init(this);

        //Def lang is Eng
        String language = Paper.book().read("language");
        if (language==null)
            Paper.book().write("language","en");

        updateView((String)Paper.book().read("language"));

        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number already register", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


    }
    private void updateView(String lang){
        Context context = LocHelper.setLocale(this,lang);
        Resources resources = context.getResources();

        btnSignup.setText(resources.getString(R.string.SignOut));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.language_en)
        {
            Paper.book().write("language","en");
            updateView((String)Paper.book().read("language"));
        }
        else if (item.getItemId()==R.id.language_rus)
        {
            Paper.book().write("language","ru");
            updateView((String)Paper.book().read("language"));
        }
        if (item.getItemId()==R.id.language_kor)
        {
            Paper.book().write("language","ko");
            updateView((String)Paper.book().read("language"));
        }
        return true;
    }
}
