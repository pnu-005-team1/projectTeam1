package kr.ac.pnu.cse.menumapproject;

import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import kr.ac.pnu.cse.menumapproject.db.DbHelper;
import kr.ac.pnu.cse.menumapproject.model.Review;
import kr.ac.pnu.cse.menumapproject.model.Star;
import kr.ac.pnu.cse.menumapproject.util.RetrofitUtil;
import kr.ac.pnu.cse.menumapproject.util.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static String REST_NAME_KEY = "REST_NAME_KEY";
    public static String MENU_KEY = "MENU_KEY";
    public static String LAT_KEY = "LAT_KEY";
    public static String LNG_KEY = "LNG_KEY";
    public static String IS_FAVORITE_KEY = "FAVORITE_KEY";

    private String restname;
    private String menus;
    private float lat;
    private float lng;
    private boolean isFavorite;

    RatingBar ratingBar;
    ImageView addRatingImageView;
    RecyclerView reviewRecyclerView;
    ReviewRecyclerAdapter reviewRecyclerAdapter;
    ImageView addReviewImageView;
    View addFavorite;
    View removeFavorite;

    EditText reviewIdEditText;
    EditText reviewValueEditText;
    TextView menuTextView;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int themeNumFromPreference = SharedPreferenceUtil.getThemeResId(getApplicationContext());
        setTheme(themeNumFromPreference);

        setContentView(R.layout.activity_detail);

        dbHelper = new DbHelper(this, "MENU_DB", null, 1);

        addFavorite = findViewById(R.id.detail_add_favorite);
        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addMenu(restname, lat, lng);
                Snackbar.make(findViewById(android.R.id.content), "즐겨찾기에 등록되었습니다.", Snackbar.LENGTH_LONG).show();
            }
        });

        removeFavorite = findViewById(R.id.detail_remove_favorite);
        removeFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.removeMenu(restname);
                finish();
            }
        });

        restname = getIntent().getStringExtra(REST_NAME_KEY);
        menus = getIntent().getStringExtra(MENU_KEY);
        lat = getIntent().getFloatExtra(LAT_KEY, 0f);
        lng = getIntent().getFloatExtra(LNG_KEY, 0f);
        isFavorite = getIntent().getBooleanExtra(IS_FAVORITE_KEY, false);

        if(isFavorite){
            removeFavorite.setVisibility(View.VISIBLE);
            addFavorite.setVisibility(View.GONE);
        } else {
            removeFavorite.setVisibility(View.GONE);
            addFavorite.setVisibility(View.VISIBLE);
        }

        Log.d("LOG_TAG", "menus : " + menus);
        Log.d("LOG_TAG", "rest name : " + restname);

        reviewIdEditText = findViewById(R.id.review_id_edit_view);
        reviewValueEditText = findViewById(R.id.review_value_edit_view);

        ratingBar = findViewById(R.id.detail_review_rating_bar);
        ratingBar.setMax(5);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1);

        addRatingImageView = findViewById(R.id.detail_rating_add_buttom);
        addRatingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = (int) ratingBar.getRating();
                postStar(rating);
            }
        });

        reviewRecyclerAdapter = new ReviewRecyclerAdapter(new ArrayList<Review>());
        reviewRecyclerView = findViewById(R.id.detail_review_recycler_view);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewRecyclerView.setAdapter(reviewRecyclerAdapter);
        reviewRecyclerView.setHasFixedSize(true);

        addReviewImageView = findViewById(R.id.detail_review_add_buttom);
        addReviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReview();
            }
        });

        menuTextView = findViewById(R.id.detail_menu_text_view);
        String prettyMenu = menuPretty(menus);
        Log.d("LOG_TAG", "prettyMenu" + prettyMenu);
        menuTextView.setText(prettyMenu);


        getStar();
        getReview();
    }

    private void postStar(int rating) {
        RetrofitUtil.getRetrofitService().postStar("star", String.valueOf(rating), "sampleId", restname).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("LOG_TAG", "response : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("LOG_TAG", "fail : " + t.getMessage());
            }
        });
        ratingBar.setRating(0);
        Snackbar.make(findViewById(android.R.id.content), "별점이 등록되었습니다.", Snackbar.LENGTH_LONG).show();
    }

    private void postReview() {
        String userId = reviewIdEditText.getText().toString();
        String reviewValue = reviewValueEditText.getText().toString();
        RetrofitUtil.getRetrofitService().postReview("review", reviewValue, userId, restname).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("LOG_TAG", "response");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("LOG_TAG", "fail : " + t.getMessage());
            }
        });
        reviewValueEditText.setText("");
        reviewIdEditText.setText("");
        reviewRecyclerAdapter.addReview(new Review(userId, restname, reviewValue));
        Snackbar.make(findViewById(android.R.id.content), "리뷰가 등록되었습니다.", Snackbar.LENGTH_LONG).show();
    }

    private void getStar() {
        RetrofitUtil.getRetrofitService().getStar("star", "r").enqueue(new Callback<List<Star>>() {
            @Override
            public void onResponse(Call<List<Star>> call, Response<List<Star>> response) {
                List<Star> starList = response.body();
                int ratingSum = 0;
                int ratingSize = 0;

                if (starList != null && starList.size() != 0) {
                    for (Star star : starList) {
                        if (star.restname.equals(restname)) {
                            ratingSize++;
                            ratingSum += star.value;
                        }
                    }
                }
                if (ratingSize == 0) {
                    ratingBar.setRating(0);
                } else {
                    ratingBar.setRating((float) ratingSum / (float) ratingSize);
                }
            }

            @Override
            public void onFailure(Call<List<Star>> call, Throwable t) {
                Log.d("LOG_TAG", "fail : " + t.getMessage());
            }
        });
    }

    public void getReview() {
        RetrofitUtil.getRetrofitService().getReview("review", "r").enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                final List<Review> reviewList = new ArrayList<>();
                for (Review review : response.body()) {
                    if (review.restname.equals(restname)) {
                        reviewList.add(review);
                    }
                }
                reviewRecyclerAdapter.setReviewList(reviewList);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.d("LOG_TAG", "fail : " + t.getMessage());
            }
        });
    }

    private String menuPretty(String menu) {
        ArrayList<Pair<String, String>> menuPairList = new ArrayList<>();

        StringTokenizer stringTokenizer = new StringTokenizer(menu, "#");
        Log.d("LOG_TAG", "token size " + stringTokenizer.countTokens());
        while (stringTokenizer.hasMoreTokens()) {
            String menuName = stringTokenizer.nextToken();
            if (!stringTokenizer.hasMoreTokens()) break;
            String cost = stringTokenizer.nextToken();
            Pair<String, String> eachMenu = new Pair<>(menuName, cost);
            menuPairList.add(eachMenu);
        }

        StringBuilder result = new StringBuilder();

        for(Pair<String, String> eachMenu : menuPairList) {
            if(eachMenu.first.trim().equals("원")) continue;

            result.append(eachMenu.first);
            result.append(" : ");
            result.append(eachMenu.second);
            result.append("\n");
        }

        return result.toString();
    }
}