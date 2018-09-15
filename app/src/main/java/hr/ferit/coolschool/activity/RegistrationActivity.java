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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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

import static hr.ferit.coolschool.utils.Constants.EMAIL_REGEX;
import static hr.ferit.coolschool.utils.Constants.NAME_REGEX;
import static hr.ferit.coolschool.utils.Constants.REGISTRATION_ACTY_BOOL_EXTRA;
import static hr.ferit.coolschool.utils.Constants.USERNAME_REGEX;

public class RegistrationActivity extends AppCompatActivity {

    //RecyclerView
    private RecyclerView rvSchools;
    private SchoolsAdapter mSchoolsAdapter;
    private LayoutManager mLayoutManager;

    private ScrollView scrollView;
    private TextView tvHeading;
    private TextInputLayout tilClass, tilUsername, tilName, tilSurname,
            tilEmail, tilPassword, tilRetypedPass, tilSchool;
    private TextInputEditText etName, etSurname, etUsername, etPassword,
            etRetypedPass, etEmail, etClass;
    private Spinner spSubjects;
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
        this.spSubjects = findViewById(R.id.reg_sp_subjects);
        this.etEmail = findViewById(R.id.reg_et_email);
        this.etName = findViewById(R.id.reg_et_name);
        this.etSurname = findViewById(R.id.reg_et_surname);
        this.etUsername = findViewById(R.id.reg_et_username);
        this.etPassword = findViewById(R.id.reg_et_password);
        this.etRetypedPass = findViewById(R.id.reg_et_retype_pass);
        this.etClass = findViewById(R.id.reg_et_class_num);
        this.tilEmail = findViewById(R.id.reg_til_email);
        this.tilName = findViewById(R.id.reg_til_name);
        this.tilSurname = findViewById(R.id.reg_til_surname);
        this.tilUsername = findViewById(R.id.reg_til_username);
        this.tilPassword = findViewById(R.id.reg_til_password);
        this.tilRetypedPass = findViewById(R.id.reg_til_retype_pass);
        this.tilClass = findViewById(R.id.reg_til_class);
        this.tilSchool = findViewById(R.id.reg_til_school);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, fetchSchoolNamesList()
        );
        this.spSubjects.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Subject.values()));

        this.actvSchools.setAdapter(adapter);
        this.scrollView = findViewById(R.id.register_main_layout);
        this.tvHeading = findViewById(R.id.reg_tv_heading);
        this.btnAddSchool = findViewById(R.id.reg_btn_add_school);
        btnAddSchool.setOnClickListener(v -> {
            checkDataForSchoolAdd();
        });
        this.btnRegister = findViewById(R.id.reg_btn_register);
        this.btnRegister.setOnClickListener(v -> registerUser(getUserFromFields()));

        rvSchools = findViewById(R.id.reg_school_rv);
        mSchoolsAdapter = new SchoolsAdapter(mUserSchools, true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSchools.setLayoutManager(mLayoutManager);
        rvSchools.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvSchools.setItemAnimator(new DefaultItemAnimator());
        rvSchools.setAdapter(mSchoolsAdapter);
    }

    private void checkDataForSchoolAdd() {
        tilSchool.setErrorEnabled(false);
        tilClass.setErrorEnabled(false);
        Integer classNum = null;
        if (isStudent) {
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
                    .equals(String.format("%s - %s",school.getName(), school.getCity()))) {
                found = true;
                UserSchool userSchool = new UserSchool(null, school,
                        isStudent ? classNum : null,
                        isStudent ? null : (Subject) spSubjects.getSelectedItem());
                if (!mUserSchools.contains(userSchool)) {
                    mUserSchools.add(userSchool);
                    mSchoolsAdapter.notifyDataSetChanged();
                    actvSchools.setText("");
                    etClass.setText("");
                } else {
                    tilSchool.setError("Škola je već dodana");
                }
            }
        }
        if(!found){
            tilSchool.setError("Odaberite školu s popisa");
        }
    }

    private void registerUser(User userFromFields) {
        if (userFromFields != null) {
            Call<User> call = RetrofitImpl.getUserService().registration(userFromFields);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Uspješna registracija", Toast.LENGTH_SHORT).show();
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
    }

    private User getUserFromFields() {
        String firstName = etName.getText().toString();
        String lastName = etSurname.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        if (checkData(firstName, lastName, username, password, email)) {
            if(mUserSchools.size() > 0) {
                User user = new User(username, password, email, firstName, lastName,
                        isStudent ? Role.ROLE_STUDENT : Role.ROLE_TEACHER);
                user.setUserSchools(new HashSet<>(mUserSchools));

                return user;
            }else{
                Toast.makeText(getApplicationContext(), "Dodajte barem jednu školu", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    boolean isValid = true;

    private boolean checkData(String firstName, String lastName, String username, String password, String email) {
        refreshTilErrors();
        isValid = true;
        checkStringAndDisplayError(firstName, tilName, NAME_REGEX,
                "vaše ime", "Može sadržavati samo slova");

        checkStringAndDisplayError(lastName, tilSurname, NAME_REGEX,
                "vaše prezime", "Može sadržavati samo slova");

        checkStringAndDisplayError(username, tilUsername,
                USERNAME_REGEX, "korisničko ime",
                "Neispravan format");

        checkStringAndDisplayError(email, tilEmail,
                EMAIL_REGEX,
                "vašu e-mail adresu", "Neispravan format");

        if (password == null) {
            isValid = false;
            tilPassword.setError("Unesite lozinku");
        } else {
            if (password.trim().isEmpty()) {
                isValid = false;
                tilPassword.setError("Unesite lozinku");
            } else if (password.length() < 5) {
                isValid = false;
                tilPassword.setError("Unesite 5 ili više znakova");
            } else if (!password.equals(etRetypedPass.getText().toString())) {
                isValid = false;
                tilRetypedPass.setError("Lozinke ne odgovaraju");
            }
        }

        return isValid;
    }

    private void checkStringAndDisplayError(String stringToCheck, TextInputLayout til, String regex,
                                            String nullMsg, String regexMsg) {
        if (stringToCheck == null) {
            isValid = false;
            til.setError(String.format("Unesite %s", nullMsg));
        } else {
            if (stringToCheck.trim().isEmpty()) {
                isValid = false;
                til.setError(String.format("Unesite %s", nullMsg));
            } else if (!stringToCheck.matches(regex)) {
                isValid = false;
                til.setError(regexMsg);
            }
        }
    }

    private void refreshTilErrors() {
        tilClass.setErrorEnabled(false);
        tilEmail.setErrorEnabled(false);
        tilName.setErrorEnabled(false);
        tilSurname.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        tilRetypedPass.setErrorEnabled(false);
        tilUsername.setErrorEnabled(false);
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
        scrollView.setBackgroundResource(R.mipmap.student);
        tvHeading.setText(R.string.tv_student_reg);
        spSubjects.setVisibility(View.GONE);
    }

    private void setTeacherRegistrationUI() {
        scrollView.setBackgroundResource(R.mipmap.teacher);
        tvHeading.setText(R.string.tv_teacher_reg);
        tilClass.setVisibility(View.GONE);
    }
}

