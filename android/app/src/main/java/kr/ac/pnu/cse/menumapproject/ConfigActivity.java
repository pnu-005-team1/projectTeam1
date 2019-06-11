package kr.ac.pnu.cse.menumapproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfigActivity extends AppCompatActivity {

    public static int ThemeNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);



        Button theme1 = (Button)findViewById(R.id.theme1);
        Button theme2 = (Button)findViewById(R.id.theme2);
        Button theme3 = (Button)findViewById(R.id.theme3);
        theme1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeNum = 0;

            }
        }) ;
        theme2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeNum = 1;


            }
        }) ;
        theme3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemeNum = 2;


            }
        }) ;
    }
}