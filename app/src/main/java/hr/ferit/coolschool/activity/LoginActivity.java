package hr.ferit.coolschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    //TODO - put link in About or somewhere

    public static final String DEFAULT_ERROR = "Došlo je do pogreške. Pokušajte ponovno kasnije.";
    private static final String EMPTY_FIELDS = "Unesite podatke za prijavu";
    private static final String LOGIN_FAIL = "Pogrešno korisničko ime i/ili lozinka";
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
        this.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), EMPTY_FIELDS, Toast.LENGTH_SHORT).show();
                } else {
                    login(username, password);
                }
            }
        });
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
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", authenticatedUser);
        intent.putExtra("cookie", cookie);
        startActivity(intent);
    }

    private void showDefaultError() {
        Toast.makeText(getApplicationContext(), DEFAULT_ERROR, Toast.LENGTH_SHORT).show();
    }

}
