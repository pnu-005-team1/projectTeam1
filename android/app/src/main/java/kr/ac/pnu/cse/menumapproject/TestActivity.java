package kr.ac.pnu.cse.menumapproject;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import kr.ac.pnu.cse.menumapproject.model.Food;
import kr.ac.pnu.cse.menumapproject.util.GeocoderUtil;
import kr.ac.pnu.cse.menumapproject.util.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    Geocoder coder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        coder = new Geocoder(this);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitUtil.getRetrofitService().getMenu("cho", "r").enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        for (Food food : response.body()) {
                            Log.d("LOG_TAG", "response: " + food.name);
                            //GeocoderUtil.getLatLngFromAddress(food.address, getApplicationContext());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {
                        Log.d("LOG_TAG", "fail : " + t.getMessage());
                    }
                });
            }
        });
    }
}