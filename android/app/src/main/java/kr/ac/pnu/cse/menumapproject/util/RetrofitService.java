package kr.ac.pnu.cse.menumapproject.util;

import java.util.List;

import kr.ac.pnu.cse.menumapproject.model.Food;
import kr.ac.pnu.cse.menumapproject.model.Review;
import kr.ac.pnu.cse.menumapproject.model.Star;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/param/args")
    Call<List<Food>> getMenu(@Query("dbname") String dbname, @Query("write") String write);

    @GET("/param/args")
    Call<List<Review>> getReview(@Query("dbname") String dbname, @Query("write") String write);

    @GET("/param/args")
    Call<List<Star>> getStar(@Query("dbname") String dbname, @Query("write") String write);

    @GET("/param/args")
    Call<String> postStar(@Query("dbname") String dbname, @Query("write") String value, @Query("uid") String userId, @Query("restname") String restname);

    @GET("/param/args")
    Call<String> postReview(@Query("dbname") String dbname, @Query("write") String reviewStr, @Query("uid") String userId, @Query("restname") String restname);
}
