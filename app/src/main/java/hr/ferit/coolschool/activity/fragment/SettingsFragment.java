package hr.ferit.coolschool.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Role;
import hr.ferit.coolschool.model.School;
import hr.ferit.coolschool.model.Subject;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.model.UserSchool;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.view.SchoolsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private OnLogoutListener mLogoutListener;
    private User mAuthUser;
    private List<UserSchool> mUserSchools;
    private List<School> mSchools = new ArrayList<>();
    private String mCookie;

    private Button btnLogout, btnEdit, btnSave, btnAddSchool, btnCancel;
    private TextView tvUser, tvUsername;
    private RecyclerView rvSchools;
    private SchoolsAdapter mSchoolsAdapter;
    private LayoutManager mLayoutManager;
    private AutoCompleteTextView actvSchools;
    private TextInputLayout tilClass, tilUsername, tilName, tilSurname,
            tilEmail, tilPassword, tilNewPassword, tilSchool;
    private TextInputEditText etName, etSurname, etUsername, etPassword,
            etNewPass, etEmail, etClass;
    private Spinner spSubjects;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(User authUser, String cookie) {
        SettingsFragment fragment = new SettingsFragment();
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
        mUserSchools = new ArrayList<>();
        mUserSchools.addAll(mAuthUser.getUserSchools());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_settings, container, false);
        setUpUI(layout);
        return layout;
    }

    private void setUpUI(View layout) {
        //Buttons
        btnLogout = layout.findViewById(R.id.setfr_btn_logout);
        btnLogout.setOnClickListener(this);
        btnEdit = layout.findViewById(R.id.setfr_btn_edit);
        btnEdit.setOnClickListener(this);
        btnAddSchool = layout.findViewById(R.id.setfr_btn_add_school);
        btnAddSchool.setOnClickListener(this);
        btnSave = layout.findViewById(R.id.setfr_btn_save);
        btnSave.setOnClickListener(this);
        btnCancel = layout.findViewById(R.id.setfr_btn_cancel);
        btnCancel.setOnClickListener(this);


        //TextViews (EditTexts) and data
        tvUser = layout.findViewById(R.id.setfr_tv_user);
        tvUsername = layout.findViewById(R.id.setfr_tv_username);
        etEmail = layout.findViewById(R.id.setfr_et_email);
        etName = layout.findViewById(R.id.setfr_et_name);
        etSurname = layout.findViewById(R.id.setfr_et_surname);
        etUsername = layout.findViewById(R.id.setfr_et_username);
        etPassword = layout.findViewById(R.id.setfr_et_password);
        etNewPass = layout.findViewById(R.id.setfr_et_password_new);
        etClass = layout.findViewById(R.id.setfr_et_class);
        actvSchools = layout.findViewById(R.id.setfr_actv_school);
        setTextValues();

        //TextInputLayouts
        tilEmail = layout.findViewById(R.id.setfr_til_email);
        tilName = layout.findViewById(R.id.setfr_til_name);
        tilSurname = layout.findViewById(R.id.setfr_til_surname);
        tilUsername = layout.findViewById(R.id.setfr_til_username);
        tilPassword = layout.findViewById(R.id.setfr_til_password);
        tilNewPassword = layout.findViewById(R.id.setfr_til_password_new);
        tilClass = layout.findViewById(R.id.setfr_til_class);
        tilSchool = layout.findViewById(R.id.setfr_til_school);

        spSubjects = layout.findViewById(R.id.setfr_sp_subjects);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, fetchSchoolNamesList()
        );
        spSubjects.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Subject.values()));

        actvSchools.setAdapter(adapter);

        //RecyclerView setup
        rvSchools = layout.findViewById(R.id.setfr_rv_schools);
        mSchoolsAdapter = new SchoolsAdapter(mUserSchools, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvSchools.setLayoutManager(mLayoutManager);
        rvSchools.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvSchools.setItemAnimator(new DefaultItemAnimator());
        rvSchools.setAdapter(mSchoolsAdapter);

    }

    private void setTextValues() {
        tvUser.setText(String.format("%s %s", mAuthUser.getFirstName(), mAuthUser.getLastName()));
        tvUsername.setText(mAuthUser.getUsername());
        etEmail.setText(mAuthUser.getEmail());
        etName.setText(mAuthUser.getFirstName());
        etSurname.setText(mAuthUser.getLastName());
        etUsername.setText(mAuthUser.getUsername());
        etPassword.setText("");
        etNewPass.setText("");
        etClass.setText("");
        actvSchools.setText("");
    }

    private List<String> fetchSchoolNamesList() {
        List<String> schoolNames = new ArrayList<>();
        Call<List<School>> call = RetrofitImpl.getSchoolService().listSchools();

        call.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        mSchools.addAll(response.body());
                        for (School school : response.body()) {
                            schoolNames.add(
                                    String.format("%s - %s", school.getName(), school.getCity())
                            );
                        }
                    }
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

        return schoolNames;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogoutListener) {
            mLogoutListener = (OnLogoutListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLogoutListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setfr_btn_logout:
                mLogoutListener.onLogoutListener();
                break;
            case R.id.setfr_btn_edit:
                setUpEditUI();
                break;
            case R.id.setfr_btn_add_school:
                checkDataForSchoolAdd();
                break;
            case R.id.setfr_btn_cancel:
                setTextValues();
                btnEdit.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                btnAddSchool.setVisibility(View.GONE);
                btnLogout.setVisibility(View.VISIBLE);
                setEtEnabled(false, etName, etSurname, etEmail, etUsername, etPassword);
                tilNewPassword.setVisibility(View.GONE);
                tilPassword.setVisibility(View.GONE);
                tilSchool.setVisibility(View.GONE);
                spSubjects.setVisibility(View.GONE);
                tilClass.setVisibility(View.GONE);
                mUserSchools.clear();
                mUserSchools.addAll(mAuthUser.getUserSchools());
                mSchoolsAdapter.setmShowDeleteBtn(false);
                break;
            case R.id.setfr_btn_save:

                break;
        }
    }

    private void setUpEditUI() {
        btnEdit.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
        btnLogout.setVisibility(View.GONE);
        btnCancel.setVisibility(View.VISIBLE);
        btnAddSchool.setVisibility(View.VISIBLE);
        if (mAuthUser.getRole().equals(Role.ROLE_STUDENT)) {
            tilClass.setEnabled(true);
            tilClass.setVisibility(View.VISIBLE);
        } else {
            spSubjects.setVisibility(View.VISIBLE);
        }
        setEtEnabled(true, etName, etSurname, etEmail, etUsername, etPassword);
        mSchoolsAdapter.setmShowDeleteBtn(true);
        tilNewPassword.setVisibility(View.VISIBLE);
        tilPassword.setVisibility(View.VISIBLE);
        tilSchool.setVisibility(View.VISIBLE);
    }

    private void setEtEnabled(boolean enabled, EditText... ets) {
        for (EditText et : ets) {
            et.setEnabled(enabled);
        }
    }

    private void checkDataForSchoolAdd() {
        tilSchool.setErrorEnabled(false);
        tilClass.setErrorEnabled(false);
        Integer classNum = null;
        if (mAuthUser.getRole().equals(Role.ROLE_STUDENT)) {
            if (etClass.getText() != null) {
                if (!etClass.getText().toString().isEmpty() &&
                        Integer.valueOf(etClass.getText().toString()) <= 8 &&
                        Integer.valueOf(etClass.getText().toString()) > 0) {
                    classNum = Integer.valueOf(etClass.getText().toString());
                    addUserSchoolToList(classNum);
                } else {
                    tilClass.setError("Unesite razred");
                }
            } else {
                tilClass.setError("Unesite razred");
            }
        } else {
            addUserSchoolToList(null);
        }
    }

    private void addUserSchoolToList(Integer classNum) {
        boolean found = false;
        for (School school : mSchools) {
            if (actvSchools.getText().toString()
                    .equals(String.format("%s - %s", school.getName(), school.getCity()))) {
                found = true;
                UserSchool userSchool = new UserSchool(null, school,
                        mAuthUser.getRole().equals(Role.ROLE_STUDENT) ? classNum : null,
                        mAuthUser.getRole().equals(Role.ROLE_STUDENT) ? null : (Subject) spSubjects.getSelectedItem());
                if (!mUserSchools.contains(userSchool)) {
                    mUserSchools.add(userSchool);
                    mSchoolsAdapter.notifyDataSetChanged();
                    actvSchools.setText("");
                } else {
                    tilSchool.setError("Škola je već dodana");
                }
            }
        }
        if (!found) {
            tilSchool.setError("Odaberite školu s popisa");
        }
    }

    public interface OnLogoutListener {
        void onLogoutListener();
    }
}
