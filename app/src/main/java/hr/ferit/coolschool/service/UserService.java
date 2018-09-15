package hr.ferit.coolschool.service;

import hr.ferit.coolschool.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @FormUrlEncoded
    @POST("/login")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    @GET("/auth")
    Call<User> authenticatedUser(@Header("Cookie") String cookie);

    @POST("/api/users/registration")
    Call<User> registration(@Body User user);

    @PUT("api/users/{id}")
    Call<User> updateUser(@Header("Cookie") String cookie,
                          @Path("id") Long userId, @Body User user);

    @PUT("api/users/{id}/update-password")
    Call<Boolean> updateUserPassword(@Header("Cookie") String cookie,
                                     @Path("id") Long userId, @Query("oldPassword") String oldPassword,
                                     @Query("newPassword") String newPassword);
}
