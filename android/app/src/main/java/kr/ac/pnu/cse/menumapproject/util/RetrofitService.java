package kr.ac.pnu.cse.menumapproject.util;

import java.util.List;

import kr.ac.pnu.cse.menumapproject.model.Food;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/param/args")
    Call<List<Food>> getMenu(@Query("dbname") String dbname, @Query("write") String write);
}
