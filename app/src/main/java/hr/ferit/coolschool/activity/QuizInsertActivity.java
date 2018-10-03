package hr.ferit.coolschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Answer;
import hr.ferit.coolschool.model.Question;
import hr.ferit.coolschool.model.Quiz;
import hr.ferit.coolschool.model.Subject;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.model.UserSchool;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.view.QuestionAnswerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.DEFAULT_ERROR;
import static hr.ferit.coolschool.utils.Constants.QUIZ_KEY;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;
import static hr.ferit.coolschool.utils.Constants.getClassList;
import static hr.ferit.coolschool.utils.Constants.getDifficulties;

public class QuizInsertActivity extends AppCompatActivity {

    private List<Question> mQuestions;
    private Quiz mQuiz;
    private User mAuthUser;
    private String mCookie;

    private RecyclerView rvQuestions;
    private QuestionAnswerAdapter mQAAdapter;
    private LayoutManager mLayoutManager;
    private TextInputEditText etTitle, etDescription, etSumPoints;
    private TextInputLayout tilTitle, tilDescription, tilSumPoints;
    private EditText etQuestion, etAnswer1, etAnswer2, etAnswer3, etAnswer4, etPoints;
    private RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;
    private Button btnSave, btnCancel, btnAddQuestion;
    private TextView tvSumPoints;
    private Spinner spDifficulty, spSubject, spClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_insert_update);

        if (getIntent().hasExtra(QUIZ_KEY)) {
            mQuiz = (Quiz) getIntent().getExtras().getSerializable(QUIZ_KEY);
            mQuestions = new ArrayList<>(mQuiz.getQuestions());
        } else {
            mQuestions = new ArrayList<>();
        }
        mAuthUser = (User) getIntent().getExtras().getSerializable(USER_KEY);
        mCookie = getIntent().getExtras().getString(COOKIE_KEY);
        setUpUI();

    }

    private void setUpUI() {
        rvQuestions = findViewById(R.id.qie_rv);
        mLayoutManager = new LinearLayoutManager(this);
        rvQuestions.setLayoutManager(mLayoutManager);
        rvQuestions.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvQuestions.setItemAnimator(new DefaultItemAnimator());
        mQAAdapter = new QuestionAnswerAdapter(mQuestions);
        rvQuestions.setAdapter(mQAAdapter);
        setUpSpinners();
        btnCancel = findViewById(R.id.qie_btn_cancel);
        btnCancel.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
        });
        btnSave = findViewById(R.id.qie_btn_save);
        btnSave.setOnClickListener(v -> {
            //TODO - validate fields and everything
            Subject subject = null;
            for (Subject subject1 : Subject.values()) {
                if (subject1.equals(Subject.valueOf((String) spSubject.getSelectedItem()))) {
                    subject = subject1;
                    break;
                }
            }
            Quiz newQuiz = new Quiz(etTitle.getText().toString(), etDescription.getText().toString(),
                    spClass.getSelectedItemPosition()+1, subject,
                    Float.valueOf(tvSumPoints.getText().toString()),
                    spDifficulty.getSelectedItemPosition()+1,
                    new HashSet<>(mQuestions));
            saveQuizToServer(newQuiz);
        });

        etQuestion = findViewById(R.id.qie_et_question);
        etAnswer1 = findViewById(R.id.qie_et_answer_1);
        etAnswer2 = findViewById(R.id.qie_et_answer_2);
        etAnswer3 = findViewById(R.id.qie_et_answer_3);
        etAnswer4 = findViewById(R.id.qie_et_answer_4);
        etPoints = findViewById(R.id.qie_et_points);
        rbAnswer1 = findViewById(R.id.qie_rb_answer_1);
        rbAnswer2 = findViewById(R.id.qie_rb_answer_2);
        rbAnswer3 = findViewById(R.id.qie_rb_answer_3);
        rbAnswer4 = findViewById(R.id.qie_rb_answer_4);
        btnAddQuestion = findViewById(R.id.qie_btn_save_question);
        btnAddQuestion.setOnClickListener(v ->{
            Question question = new Question(etQuestion.getText().toString(), null, null);
            Set<Answer> answers = new HashSet<>();
            float points = Float.valueOf(etPoints.getText().toString());
            answers.add(newAnswer(etAnswer1,rbAnswer1,points));
            answers.add(newAnswer(etAnswer2,rbAnswer2,points));
            answers.add(newAnswer(etAnswer3,rbAnswer3,points));
            answers.add(newAnswer(etAnswer4,rbAnswer4,points));
            question.setAnswers(answers);
            mQuestions.add(question);
            mQAAdapter.notifyDataSetChanged();
        });

        tilTitle = findViewById(R.id.qie_til_title);
        tilDescription = findViewById(R.id.qie_til_description);
        tilSumPoints = findViewById(R.id.qie_til_sum_points);
        etDescription = findViewById(R.id.qie_et_description);
        etTitle = findViewById(R.id.qie_et_title);
        etSumPoints = findViewById(R.id.qie_et_sum_points);
        etSumPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tilSumPoints.setErrorEnabled(false);
                try {
                    if (Float.valueOf(s.toString()) <= 0) {
                        tilSumPoints.setError("Unesite broj veći od 0");
                    } else {
                        tvSumPoints.setText(String.valueOf(
                                Float.valueOf(s.toString()) * (spDifficulty.getSelectedItemPosition() + 1))
                        );
                    }
                } catch (Exception e) {
                    tilSumPoints.setError("Unesite broj");
                }

            }
        });
        tvSumPoints = findViewById(R.id.qie_tv_max_points);
    }

    private void saveQuizToServer(Quiz newQuiz) {
        Call<Quiz> call = RetrofitImpl.getQuizService().insertQuiz(mCookie, newQuiz);

        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Log.e("ERROR", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                Toast.makeText(getApplicationContext(), DEFAULT_ERROR, Toast.LENGTH_SHORT).show();
                Log.e("ERROR", t.toString());
            }
        });
    }

    private Answer newAnswer(EditText etAnswer, RadioButton rbAnswer, float points) {
        return new Answer(etAnswer.getText().toString(), rbAnswer.isChecked(),
                rbAnswer.isChecked() ? points : 0, null);
    }

    private void setUpSpinners() {
        List<String> subjectsStrings = new ArrayList<>();
        for (UserSchool userSchool : mAuthUser.getUserSchools()) {
            subjectsStrings.add(userSchool.getSubject().toString());
        }
        spSubject = findViewById(R.id.qie_sp_subject);
        ArrayAdapter<String> subjects = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, subjectsStrings);
        spSubject.setAdapter(subjects);

        spClass = findViewById(R.id.qie_sp_class);
        List<String> classesList = new ArrayList<>(getClassList());
        classesList.remove(0);
        ArrayAdapter<String> classes = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, classesList);
        spClass.setAdapter(classes);

        List<String> difficultiesList = new ArrayList<>(getDifficulties());
        difficultiesList.remove(0);
        spDifficulty = findViewById(R.id.qie_sp_difficulty);
        ArrayAdapter<String> difficulties = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, difficultiesList);
        spDifficulty.setAdapter(difficulties);
    }
}
