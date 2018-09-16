package hr.ferit.coolschool.utils;

import hr.ferit.coolschool.service.RankService;
import hr.ferit.coolschool.service.SchoolService;
import hr.ferit.coolschool.service.UserService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitImpl {

    private static final String BASE_URL = "http://192.168.0.101:8080";
    private static Retrofit retrofit;
    private static UserService userService;
    private static SchoolService schoolService;
    private static RankService rankService;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
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

    public static SchoolService getSchoolService() {
        if (schoolService == null) {
            schoolService = getRetrofitInstance().create(SchoolService.class);
        }
        return schoolService;
    }

    public static RankService getRankService() {
        if (rankService == null) {
            rankService = getRetrofitInstance().create(RankService.class);
        }
        return rankService;
    }
}
