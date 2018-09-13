package hr.ferit.coolschool.service;

import java.util.List;

import hr.ferit.coolschool.model.School;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SchoolService {

    @GET("/api/schools")
    Call<List<School>> listSchools();
}
