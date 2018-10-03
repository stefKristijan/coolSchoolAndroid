package hr.ferit.coolschool.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.activity.QuizInsertActivity;
import hr.ferit.coolschool.model.Quiz;
import hr.ferit.coolschool.model.Role;
import hr.ferit.coolschool.model.SchoolType;
import hr.ferit.coolschool.model.Subject;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.model.UserSchool;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.view.QuizAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.DEFAULT_ERROR;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;
import static hr.ferit.coolschool.utils.Constants.getClassList;
import static hr.ferit.coolschool.utils.Constants.getDifficulties;
import static hr.ferit.coolschool.utils.Constants.getSpinnerSubjects;

public class QuizFragment extends Fragment {

    private User mAuthUser;
    private boolean isStudent;
    private String mCookie;
    private List<Quiz> mQuizzes;

    private RecyclerView rvQuizzes;
    private QuizAdapter mQuizAdapter;
    private LayoutManager mLayoutManager;

    private Spinner spDifficulty, spSubject, spClass;
    private Button btnSearch;
    private FloatingActionButton fabNewQuiz;

    public QuizFragment() {
    }

    public static QuizFragment newInstance(User authUser, String cookie) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();

        args.putString(COOKIE_KEY, cookie);
        args.putSerializable(USER_KEY, authUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCookie = getArguments().getString(COOKIE_KEY);
            mAuthUser = (User) getArguments().getSerializable(USER_KEY);
        }
        mQuizzes = new ArrayList<>();
        isStudent = mAuthUser.getRole().equals(Role.ROLE_STUDENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_quiz, container, false);
        setUpUI(layout);
        return layout;
    }

    //TODO - on first fetch (if student - fetch only for his class)
    @SuppressLint("RestrictedApi")
    private void setUpUI(View layout) {
        rvQuizzes = layout.findViewById(R.id.quizfr_rv);
        fabNewQuiz = layout.findViewById(R.id.quizfr_fab_add);
        ArrayAdapter<String> subjects;
        Subject mainSubject = null;
        if (isStudent) {
            fabNewQuiz.setVisibility(View.GONE);
            subjects = new ArrayAdapter<>(
                    getActivity(), android.R.layout.simple_spinner_dropdown_item, getSpinnerSubjects());
        } else {
            List<String> subjectsStrings = new ArrayList<>();
            for (UserSchool userSchool : mAuthUser.getUserSchools()) {
                subjectsStrings.add(userSchool.getSubject().toString());
                mainSubject = userSchool.getSubject();
            }
            subjects = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subjectsStrings);
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvQuizzes.setLayoutManager(mLayoutManager);
        rvQuizzes.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvQuizzes.setItemAnimator(new DefaultItemAnimator());
        mQuizAdapter = new QuizAdapter(mQuizzes, getActivity());
        rvQuizzes.setAdapter(mQuizAdapter);
        spClass = layout.findViewById(R.id.quizfr_sp_class);
        btnSearch = layout.findViewById(R.id.quizfr_btn_search);
        fetchQuizList(null, null,
                isStudent ? null : mainSubject);
        ArrayAdapter<String> classes = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, getClassList());
        spClass.setAdapter(classes);

        spDifficulty = layout.findViewById(R.id.quizfr_sp_difficulty);
        ArrayAdapter<String> difficulties = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, getDifficulties());
        spDifficulty.setAdapter(difficulties);

        spSubject = layout.findViewById(R.id.quizfr_sp_subject);

        spSubject.setAdapter(subjects);

        fabNewQuiz.setOnClickListener(v -> {
            startInsertAndUpdateActivity();
        });
        btnSearch.setOnClickListener(v -> {
            Integer difficulty = null, classNum = null;
            Subject subject = null;
            if (spDifficulty.getSelectedItemPosition() > 0) {
                difficulty = spDifficulty.getSelectedItemPosition();
            }
            if (spClass.getSelectedItemPosition() > 0) {
                classNum = spClass.getSelectedItemPosition();
            }
            if ((spSubject.getSelectedItemPosition() > 0 && isStudent) || !isStudent) {
                for (Subject subject1 : Subject.values()) {
                    if (subject1.equals(Subject.valueOf((String) spSubject.getSelectedItem()))) {
                        subject = subject1;
                        break;
                    }
                }
            }
            fetchQuizList(difficulty, classNum, subject);
        });
    }

    private void startInsertAndUpdateActivity() {
        Intent intent = new Intent(getActivity(), QuizInsertActivity.class);
        intent.putExtra(USER_KEY, mAuthUser);
        intent.putExtra(COOKIE_KEY, mCookie);
        getActivity().startActivityForResult(intent, 100);
    }



    private void fetchQuizList(Integer difficulty, Integer classNum, Subject subject) {
        boolean enabled = mAuthUser.getRole().equals(Role.ROLE_STUDENT);
        Call<List<Quiz>> call = RetrofitImpl.getQuizService().listQuizzes(
                mCookie, difficulty, enabled, classNum, SchoolType.ELEMENTARY_SCHOOL, subject);

        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                if (response.isSuccessful()) {
                    mQuizzes.clear();
                    mQuizzes.addAll(response.body());
                    mQuizAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Log.e("ERROR", t.toString());
                Toast.makeText(getContext(), DEFAULT_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
