package kr.ac.pnu.cse.menumapproject.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public static String baseUrl = "https://6595d228.ngrok.io";

    private RetrofitUtil() {}

    static Retrofit retrofit;
    static RetrofitService retrofitService;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitService getRetrofitService() {
        if(retrofitService == null) {
            retrofitService = getRetrofit().create(RetrofitService.class);
        }
        return retrofitService;
    }
}
