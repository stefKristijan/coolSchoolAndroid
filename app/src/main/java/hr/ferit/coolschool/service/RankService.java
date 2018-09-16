package hr.ferit.coolschool.service;

import java.util.List;

import hr.ferit.coolschool.model.Rank;
import hr.ferit.coolschool.model.Subject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RankService {

    @GET("api/rankings")
    Call<List<Rank>> listRanks(@Header("Cookie") String cookie,
                               @Query("schoolId") Integer schoolId,
                               @Query("city") String city,
                               @Query("state") String state,
                               @Query("Subject") Subject subject);
}
