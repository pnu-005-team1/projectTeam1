package kr.ac.pnu.cse.menumapproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import kr.ac.pnu.cse.menumapproject.util.SharedPreferenceUtil;

public class ConfigActivity extends AppCompatActivity {

    RadioGroup themeRadioGroup;
    ImageView themeOkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int themeNumFromPreference = SharedPreferenceUtil.getThemeResId(getApplicationContext());
        setTheme(themeNumFromPreference);

        setContentView(R.layout.activity_config);

        themeRadioGroup = findViewById(R.id.config_theme_radio_group);
        themeOkImageView = findViewById(R.id.config_theme_ok_button);

        themeOkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int themeNum = 1;
                switch (themeRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.config_theme_radio_1:
                        themeNum = 1;
                        break;
                    case R.id.config_theme_radio_2:
                        themeNum = 2;
                        break;
                    case R.id.config_theme_radio_3:
                        themeNum = 3;
                        break;
                    case R.id.config_theme_radio_4:
                        themeNum = 4;
                        break;
                    case R.id.config_theme_radio_5:
                        themeNum = 5;
                        break;
                }
                SharedPreferenceUtil.setTheme(getApplicationContext(), themeNum);

                finish();
            }
        });
    }
}