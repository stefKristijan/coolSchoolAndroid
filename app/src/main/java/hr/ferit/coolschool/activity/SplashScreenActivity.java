package hr.ferit.coolschool.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

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
import static hr.ferit.coolschool.utils.Constants.USER_KEY;

public class SplashScreenActivity extends AwesomeSplash {

    private SharedPrefsHelper mSharedPrefs;

    @Override
    public void initSplash(ConfigSplash configSplash) {

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.white); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(5); //TODO - change duration
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.logo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //TODO - change duration
        configSplash.setAnimLogoSplashTechnique(Techniques.Tada);

        //Customize Path
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(5); //TODO - change duration
        configSplash.setPathSplashStrokeSize(1); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(5); //TODO - change duration
        configSplash.setPathSplashFillColor(R.color.colorPrimary); //path object filling color

    }

    @Override
    public void animationsFinished() {
        mSharedPrefs = new SharedPrefsHelper(this);
        if (mSharedPrefs.getAuthenticatedUserInfo() == null) {
            startLoginActivity();
        }else{
            obtainCookieByLogin(mSharedPrefs.getAuthenticatedUserInfo());
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(USER_KEY, mSharedPrefs.getAuthenticatedUserInfo());
            intent.putExtra(COOKIE_KEY, mSharedPrefs.getCookie());
            startActivity(intent);
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void obtainCookieByLogin(User authenticatedUserInfo) {
        Call<ResponseBody> call = RetrofitImpl.getUserService()
                .login(authenticatedUserInfo.getUsername(), authenticatedUserInfo.getPassword());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("LOGIN RESPONSE", String.valueOf(response.code()));
                if (response.code() == 200) {
                    String cookie = response.headers().get("Set-Cookie");
                    mSharedPrefs.setCookie(cookie);
                } else if (response.code() == 401) {
                    Log.e("LOGIN ERROR", "Unauthorized");
                    startLoginActivity();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", t.toString());
                showDefaultError();
            }
        });
    }

    private void showDefaultError() {
        Toast.makeText(getApplicationContext(), DEFAULT_ERROR, Toast.LENGTH_SHORT).show();
    }

}