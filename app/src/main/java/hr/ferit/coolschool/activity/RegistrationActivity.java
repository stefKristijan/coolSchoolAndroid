package hr.ferit.coolschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.Role;
import hr.ferit.coolschool.model.School;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.model.UserSchool;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.view.SchoolsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.REGISTRATION_ACTY_BOOL_EXTRA;

public class RegistrationActivity extends AppCompatActivity {

    //RecyclerView
    private RecyclerView rvSchools;
    private SchoolsAdapter mSchoolsAdapter;
    private LayoutManager mLayoutManager;

    private ConstraintLayout constraintLayout;
    private TextView tvHeading;
    private TextInputLayout tilClass, tilUsername, tilName, tilSurname,
            tilEmail, tilPassword, tilRetypedPass;
    private TextInputEditText etName, etSurname, etUsername, etPassword, etRetypedPass, etEmail, etClass;
    private Button btnAddSchool, btnRegister;
    private AutoCompleteTextView actvSchools;
    private List<UserSchool> mUserSchools = new ArrayList<>();
    private List<School> mSchools = new ArrayList<>();

    private boolean isStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        isStudent = Objects.requireNonNull(
                getIntent().getExtras()).getBoolean(REGISTRATION_ACTY_BOOL_EXTRA);

        setUpUI();

        if (isStudent) {
            setStudentRegistrationUI();
        } else {
            setTeacherRegistrationUI();
        }
    }

    private void setUpUI() {
        this.actvSchools = findViewById(R.id.reg_actv_schools);
        this.etEmail = findViewById(R.id.reg_et_email);
        this.etName = findViewById(R.id.reg_et_name);
        this.etSurname = findViewById(R.id.reg_et_surname);
        this.etUsername = findViewById(R.id.reg_et_username);
        this.etPassword = findViewById(R.id.reg_et_password);
        this.etRetypedPass = findViewById(R.id.reg_et_retype_pass);
        this.etClass = findViewById(R.id.reg_et_class_num);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, fetchSchoolNamesList()
        );
        this.actvSchools.setAdapter(adapter);
        this.constraintLayout = findViewById(R.id.register_main_layout);
        this.tvHeading = findViewById(R.id.reg_tv_heading);
        this.tilClass = findViewById(R.id.reg_til_class);
        this.btnAddSchool = findViewById(R.id.reg_btn_add_school);
        btnAddSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (School school : mSchools) {
                    if (actvSchools.getText().toString().startsWith(school.getName())) {
                        mUserSchools.add(new UserSchool(null, school,
                                isStudent ? Integer.valueOf(etClass.getText().toString()) : null));
                        mSchoolsAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        this.btnRegister = findViewById(R.id.reg_btn_register);
        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(getUserFromFields());
            }
        });

        rvSchools = findViewById(R.id.reg_school_rv);
        mSchoolsAdapter = new SchoolsAdapter(mUserSchools);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSchools.setLayoutManager(mLayoutManager);
        rvSchools.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvSchools.setItemAnimator(new DefaultItemAnimator());
        rvSchools.setAdapter(mSchoolsAdapter);
    }

    private void registerUser(User userFromFields) {
        Log.d("REGISTRATION", userFromFields.toString());
        Call<User> call = RetrofitImpl.getUserService().registration(userFromFields);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Uspješna registracija", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("ERROR", response.toString());
                    // TODO - add toast od something (or not because there will always be a response)
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }
        });
    }

    private User getUserFromFields() {
        String firstName = etName.getText().toString();
        String lastName = etSurname.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        User user = new User(username, password, email, firstName, lastName,
                isStudent ? Role.ROLE_STUDENT : Role.ROLE_TEACHER);
        user.setUserSchools(new HashSet<>(mUserSchools));

        return user;
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

    private void setStudentRegistrationUI() {
        constraintLayout.setBackgroundResource(R.mipmap.student);
        tvHeading.setText(R.string.tv_student_reg);
    }

    private void setTeacherRegistrationUI() {
        constraintLayout.setBackgroundResource(R.mipmap.teacher);
        tvHeading.setText(R.string.tv_teacher_reg);
        tilClass.setVisibility(View.GONE);
    }
}

