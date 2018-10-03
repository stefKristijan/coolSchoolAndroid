package hr.ferit.coolschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.utils.RetrofitImpl;
import hr.ferit.coolschool.utils.SharedPrefsHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.DEFAULT_ERROR;
import static hr.ferit.coolschool.utils.Constants.LOGIN_FAIL;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;

public class LoginActivity extends AppCompatActivity {

    private static final String EMPTY_FIELDS = "Unesite podatke za prijavu";
    private Button btnLogin, btnStudentReg, btnTeacherReg;
    private EditText etUsername, etPassword;
    private User authenticatedUser;
    private SharedPrefsHelper sharedPrefsHelper;
    private String password, cookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefsHelper = new SharedPrefsHelper(this);
        setUI();
    }

    private void setUI() {
        this.etUsername = findViewById(R.id.login_et_username);
        this.etPassword = findViewById(R.id.login_et_password);
        this.btnLogin = findViewById(R.id.login_btn_login);
        this.btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), EMPTY_FIELDS, Toast.LENGTH_SHORT).show();
            } else {
                login(username, password);
            }
        });
        this.btnStudentReg = findViewById(R.id.login_btn_student_reg);
        this.btnStudentReg.setOnClickListener(v -> startRegistrationActivity(true));
        this.btnTeacherReg = findViewById(R.id.login_btn_teacher_reg);
        this.btnTeacherReg.setOnClickListener(v -> startRegistrationActivity(false));
    }

    private void startRegistrationActivity(boolean isStudent) {
        Intent registerIntent = new Intent(this, RegistrationActivity.class);
        registerIntent.putExtra("isStudent", isStudent);
        startActivity(registerIntent);
    }

    private void login(String username, String password) {
        Call<ResponseBody> call = RetrofitImpl.getUserService().login(username, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("LOGIN RESPONSE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    cookie = response.headers().get("Set-Cookie");
                    sharedPrefsHelper.setCookie(cookie);
                    fetchUserInfo(cookie);
                } else if (response.code() == 401) {
                    Log.e("LOGIN ERROR", "Unauthorized");
                    Toast.makeText(getApplicationContext(), LOGIN_FAIL, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", t.toString());
                showDefaultError();
            }
        });
    }

    private void fetchUserInfo(String cookie) {
        Call<User> call = RetrofitImpl.getUserService().authenticatedUser(cookie);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    authenticatedUser = response.body();
                    saveAuthenticatedUserToSharedPrefs();
                    startMainActivity();
                } else {
                    Log.e("ERROR", response.toString());
                    // TODO - add toast od something (or not because there will always be a response)
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR", t.toString());
                showDefaultError();
            }
        });
    }

    private void saveAuthenticatedUserToSharedPrefs() {
        authenticatedUser.setPassword(password);
        sharedPrefsHelper.setAuthenticatedUserInfo(authenticatedUser);
        sharedPrefsHelper.setCookie(cookie);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(USER_KEY, authenticatedUser);
        intent.putExtra(COOKIE_KEY, cookie);
        startActivity(intent);
    }

    private void showDefaultError() {
        Toast.makeText(getApplicationContext(), DEFAULT_ERROR, Toast.LENGTH_SHORT).show();
    }

}
