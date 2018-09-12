package hr.ferit.coolschool.utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitImpl {

    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
