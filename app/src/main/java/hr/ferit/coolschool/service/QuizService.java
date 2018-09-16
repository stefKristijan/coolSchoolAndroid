package hr.ferit.coolschool.service;

import java.util.List;

import hr.ferit.coolschool.model.Quiz;
import hr.ferit.coolschool.model.SchoolType;
import hr.ferit.coolschool.model.Subject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface QuizService {

    @GET("api/quizzes")
    Call<List<Quiz>> listQuizzes(@Header("Cookie") String cookie,
                                 @Query("difficulty") Integer difficulty,
                                 @Query("enabled") boolean enabled,
                                 @Query("classNum") Integer classNum,
                                 @Query("schoolType") SchoolType schoolType,
                                 @Query("subject") Subject subject);
}
