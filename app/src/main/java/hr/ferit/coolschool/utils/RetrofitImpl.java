package hr.ferit.coolschool.utils;

import hr.ferit.coolschool.service.UserService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitImpl {

    private static final String BASE_URL = "http://192.168.1.105:8080";
    private static Retrofit retrofit;
    private static UserService userService;

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

    public static UserService getUserService() {
        if (userService == null) {
            userService = getRetrofitInstance().create(UserService.class);
        }
        return userService;
    }
}
