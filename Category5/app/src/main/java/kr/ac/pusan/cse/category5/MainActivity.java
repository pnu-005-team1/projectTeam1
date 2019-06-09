package kr.ac.pusan.cse.category5;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.paperdb.Paper;

import kr.ac.pusan.cse.category5.Helpers.LocHelper;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocHelper.onAttach(newBase, "en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        Paper.init(this);

        //Def lang is Eng
        String language = Paper.book().read("language");
        if (language == null)
            Paper.book().write("language", "en");

        updateView((String) Paper.book().read("language"));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUp);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, SignIn.class);
                startActivity(signIn);
            }
        });
    }

    private void updateView(String lang) {
        Context context = LocHelper.setLocale(this, lang);
        Resources resources = context.getResources();

        btnSignIn.setText(resources.getString(R.string.SignIn));
        btnSignUp.setText(resources.getString(R.string.SignOut));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
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
