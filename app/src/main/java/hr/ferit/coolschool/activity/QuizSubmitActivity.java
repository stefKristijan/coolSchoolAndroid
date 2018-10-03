package hr.ferit.coolschool.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abdularis.civ.CircleImageView;

import java.util.ArrayList;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Quiz;
import hr.ferit.coolschool.model.QuizReport;
import hr.ferit.coolschool.model.Subject;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.view.SubmitQuestionAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.QUIZ_KEY;

public class QuizSubmitActivity extends AppCompatActivity {

    private Quiz mQuiz;
    private String mCookie;
    private CircleImageView civSubject;
    private TextView tvTitle, tvDescription, tvPoints, tvDifficulty, tvTimeLeft, tvTimePoints;
    private Button btnSubmit;

    private RecyclerView rvQuestions;
    private SubmitQuestionAdapter mAdapter;
    private LayoutManager mLayoutManager;

    private long fullTime = 0;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_submit);

        mQuiz = (Quiz) getIntent().getExtras().getSerializable(QUIZ_KEY);
        mCookie = getIntent().getExtras().getString(COOKIE_KEY);
        Log.d("COOKIE", mCookie);
        setUpUI();

    }

    private void setUpUI() {
        rvQuestions = findViewById(R.id.qs_rv);
        mLayoutManager = new LinearLayoutManager(this);
        rvQuestions.setLayoutManager(mLayoutManager);
        rvQuestions.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvQuestions.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SubmitQuestionAdapter(new ArrayList<>(mQuiz.getQuestions()), this);
        rvQuestions.setAdapter(mAdapter);
        civSubject = findViewById(R.id.qs_image);
        tvDescription = findViewById(R.id.qs_tv_description);
        tvDifficulty = findViewById(R.id.qs_tv_difficulty);
        tvPoints = findViewById(R.id.qs_tv_points);
        tvTitle = findViewById(R.id.qs_tv_title);
        tvTimeLeft = findViewById(R.id.qs_tv_time_left);
        tvTimePoints = findViewById(R.id.qs_tv_time_points);
        tvTitle.setText(mQuiz.getName());
        tvDescription.setText(mQuiz.getDescription());
        tvDifficulty.setText(mQuiz.getDifficultyText());
        setSubjectPicture(mQuiz.getSubject());
        setDifficultyColor(mQuiz.getDifficulty());

        setUpTimer();

        btnSubmit = findViewById(R.id.qs_btn_submit);
        btnSubmit.setOnClickListener(v -> {
            submitAnswersForQuizToServer();
            timer.cancel();
        });
    }

    private void setSubjectPicture(Subject subject) {
        switch (subject) {
            case Biologija:
                civSubject.setImageResource(R.mipmap.biology);
                break;
            case Fizika:
                civSubject.setImageResource(R.mipmap.physics);
                break;
            case Matematika:
                civSubject.setImageResource(R.mipmap.math);
                break;
            case Hrvatski:
                civSubject.setImageResource(R.mipmap.croatian);
                break;
            case Geografija:
                civSubject.setImageResource(R.mipmap.geography);
                break;
            case Glazbeni:
                civSubject.setImageResource(R.mipmap.music);
                break;
            case Kemija:
                civSubject.setImageResource(R.mipmap.chemistry);
                break;
        }
    }

    private void setDifficultyColor(int difficulty) {
        switch (difficulty) {
            case 1:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_1));
                break;
            case 2:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_2));
                break;
            case 3:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_3));
                break;
            case 4:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_4));
                break;
            case 5:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_5));
                break;
            case 6:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_6));
                break;
            case 7:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_7));
                break;
            case 8:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_8));
                break;
            case 9:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_9));
                break;
            case 10:
                tvDifficulty.setTextColor(
                        getResources().getColor(R.color.difficulty_10));
                break;
        }
    }

    private void setUpTimer() {
        long time = mQuiz.getQuestions().size() * 10 * mQuiz.getDifficulty() * 1000;
        timer = new CountDownTimer(time, 1000) {

            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {
                fullTime += 1;
                if (millisUntilFinished > (60 * 1000)) {
                    tvTimeLeft.setText(
                            String.format("%02d:%02d",
                                    ((millisUntilFinished / (1000 * 60)) % 60),
                                    (millisUntilFinished / 1000) % 60
                            )
                    );
                } else {
                    tvTimeLeft.setTextColor(getResources().getColor(R.color.red));
                    tvTimeLeft.setText(String.format("%02d sekundi", millisUntilFinished / 1000));
                }
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                tvTimeLeft.setText("Vrijeme je isteklo!");
                submitAnswersForQuizToServer();
            }
        }.start();
    }

    private void submitAnswersForQuizToServer() {
        Call<QuizReport> call = RetrofitImpl.getQuizService().submitAnswersForQuiz(
                mCookie, mQuiz.getQuizId(), mAdapter.getmSolutions(), fullTime
        );

        call.enqueue(new Callback<QuizReport>() {
            @Override
            public void onResponse(Call<QuizReport> call, Response<QuizReport> response) {
                if (response.isSuccessful()) {
                    QuizReport quizReport = response.body();
                    mAdapter.setmSolutions(quizReport.getSolutions());
                    tvTimeLeft.setText(String.valueOf(quizReport.getPoints()));
                    btnSubmit.setVisibility(View.GONE);
                    tvTimePoints.setText("Ostvareni bodovi:");
                } else if(response.code() == 500){
                    Log.e("ERROR", response.toString());
                    Toast.makeText(getApplicationContext(), "Ovaj test ste već rješavali!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    finish();
                }
            }

            @Override
            public void onFailure(Call<QuizReport> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }

}
