package hr.ferit.coolschool.activity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Rank;
import hr.ferit.coolschool.model.School;
import hr.ferit.coolschool.model.Subject;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.view.RankAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.DEFAULT_ERROR;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;
import static hr.ferit.coolschool.utils.Constants.getSpinnerCities;
import static hr.ferit.coolschool.utils.Constants.getSpinnerStates;
import static hr.ferit.coolschool.utils.Constants.getSpinnerSubjects;

public class RankingFragment extends Fragment {

    private User mAuthUser;
    private String mCookie;

    private Spinner spSchool, spSubject, spCity, spCounty;
    private Button btnSearch;
    private List<Rank> mRanks;
    private RecyclerView rvRanks;
    private RecyclerView.LayoutManager mLayoutManager;
    private RankAdapter mRankAdapter;
    private TextView tvCurrentPosition;
    private List<School> mSchools = new ArrayList<>();

    public RankingFragment() {
    }

    public static RankingFragment newInstance(User authUser, String cookie) {
        RankingFragment fragment = new RankingFragment();
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
        mRanks = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_ranking, container, false);
        setUpUI(layout);
        fetchRankingList(null, null, null, null);
        return layout;
    }

    private void setUpUI(View layout) {
        rvRanks = layout.findViewById(R.id.rankingfr_rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvRanks.setLayoutManager(mLayoutManager);
        rvRanks.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvRanks.setItemAnimator(new DefaultItemAnimator());
        mRankAdapter = new RankAdapter(mRanks, getContext());
        rvRanks.setAdapter(mRankAdapter);
        spSchool = layout.findViewById(R.id.rankingfr_sp_school);
        btnSearch = layout.findViewById(R.id.rankingfr_btn_search);
        fetchSchoolNamesList();
        spCity = layout.findViewById(R.id.rankingfr_sp_city);
        ArrayAdapter<String> cities = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, getSpinnerCities());
        spCity.setAdapter(cities);

        spSubject = layout.findViewById(R.id.rankingfr_sp_subject);
        ArrayAdapter<String> subjects = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, getSpinnerSubjects());
        spSubject.setAdapter(subjects);

        spCounty = layout.findViewById(R.id.rankingfr_sp_county);
        ArrayAdapter<String> counties = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, getSpinnerStates());
        spCounty.setAdapter(counties);

        tvCurrentPosition = layout.findViewById(R.id.rankingfr_current_position);

        btnSearch.setOnClickListener(v -> {
            Integer school = null;
            String city = null, state = null;
            Subject subject = null;
            if (spSchool.getSelectedItemPosition() > 0) {
                school = mSchools.get(spSchool.getSelectedItemPosition() - 1).getSchoolId();
            }
            if (spCity.getSelectedItemPosition() > 0) {
                city = spCity.getSelectedItem().toString();
            }
            if (spCounty.getSelectedItemPosition() > 0) {
                state = spCounty.getSelectedItem().toString();
            }
            if (spSubject.getSelectedItemPosition() > 0) {
                subject = Subject.values()[spSubject.getSelectedItemPosition() - 1];
            }
            fetchRankingList(school, city, state, subject);
        });
    }

    private void fetchRankingList(Integer school, String city, String state, Subject subject) {
        Call<List<Rank>> call = RetrofitImpl.getRankService().listRanks(mCookie, school, city, state, subject);

        call.enqueue(new Callback<List<Rank>>() {
            @Override
            public void onResponse(Call<List<Rank>> call, Response<List<Rank>> response) {
                if (response.isSuccessful()) {
                    mRanks.clear();
                    mRanks.addAll(response.body());
                    mRankAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ERROR", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Rank>> call, Throwable t) {
                Log.e("ERROR", t.toString());
                Toast.makeText(getContext(), DEFAULT_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSchoolNamesList() {

        List<String> schoolNames = new ArrayList<>();
        Call<List<School>> call = RetrofitImpl.getSchoolService().listSchools();

        call.enqueue(new Callback<List<School>>()

        {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                if (response.isSuccessful()) {
                    schoolNames.add(" ");
                    if (response.body().size() > 0) {
                        for (School school : response.body()) {
                            mSchools.add(school);
                            schoolNames.add(
                                    String.format("%s - %s", school.getName(), school.getCity())
                            );
                        }
                    }
                    ArrayAdapter<String> schools = new ArrayAdapter<>(
                            getActivity(), android.R.layout.simple_spinner_dropdown_item, schoolNames);
                    spSchool.setAdapter(schools);
                } else {
                    Log.e("ERROR", response.toString());
                    // TODO - add toast od something (or not because there will always be a response)
                }
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                Log.e("ERROR", t.toString());
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
