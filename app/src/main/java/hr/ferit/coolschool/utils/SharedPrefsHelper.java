package hr.ferit.coolschool.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import hr.ferit.coolschool.model.User;

import static android.content.Context.MODE_PRIVATE;
import static android.content.SharedPreferences.Editor;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;

public class SharedPrefsHelper {

    private SharedPreferences sharedPreferences;
    private Editor editor;
    private Context mContext;

    private static final String PREF_NAME = "SessionControl";
    private static final String KEY_COOKIE = "cookie";
    private Gson mGson;

    public SharedPrefsHelper(Context mContext) {
        mGson = new Gson();
        this.mContext = mContext;
        this.sharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    public void setAuthenticatedUserInfo(User user) {
        editor = sharedPreferences.edit();
        String userJson = mGson.toJson(user);
        editor.putString(USER_KEY, userJson);
        editor.apply();
    }

    public User getAuthenticatedUserInfo() {
        User user;
        String userJson = sharedPreferences.getString(USER_KEY, null);
        user = mGson.fromJson(userJson, User.class);
        return user;
    }

    public String getCookie(){
        return sharedPreferences.getString(KEY_COOKIE, null);
    }

    public void setCookie(String cookie) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_COOKIE, cookie);
        editor.apply();
    }
}
