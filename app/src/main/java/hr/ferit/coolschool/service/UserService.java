package hr.ferit.coolschool.service;

import hr.ferit.coolschool.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("/login")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    @GET("/auth")
    Call<User> authenticatedUser(@Header("Cookie") String cookie);
}
