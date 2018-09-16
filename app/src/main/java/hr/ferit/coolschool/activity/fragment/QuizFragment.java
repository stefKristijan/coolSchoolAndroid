package hr.ferit.coolschool.activity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Quiz;
import hr.ferit.coolschool.model.Role;
import hr.ferit.coolschool.model.SchoolType;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.view.QuizAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.DEFAULT_ERROR;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;

public class QuizFragment extends Fragment {

    //    private OnFragmentInteractionListener mListener;
    private User mAuthUser;
    private String mCookie;
    private List<Quiz> mQuizzes;

    private RecyclerView rvQuizzes;
    private QuizAdapter mQuizAdapter;
    private LayoutManager mLayoutManager;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_quiz, container, false);
        setUpUI(layout);
        return layout;
    }

    private void setUpUI(View layout) {
        rvQuizzes = layout.findViewById(R.id.quizfr_rv);

        fetchQuizList();
    }

    private void fetchQuizList() {
        boolean enabled = mAuthUser.getRole().equals(Role.ROLE_STUDENT);
        Call<List<Quiz>> call = RetrofitImpl.getQuizService().listQuizzes(
                mCookie, null, enabled, null, SchoolType.ELEMENTARY_SCHOOL, null);

        call.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                if (response.isSuccessful()) {
                    mQuizzes = response.body();
                    mQuizAdapter = new QuizAdapter(mQuizzes, getActivity());
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    rvQuizzes.setLayoutManager(mLayoutManager);
                    rvQuizzes.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    rvQuizzes.setItemAnimator(new DefaultItemAnimator());
                    rvQuizzes.setAdapter(mQuizAdapter);
                    mQuizAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ERROR", response.toString());
                    // TODO - add toast od something (or not because there will always be a response)
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
