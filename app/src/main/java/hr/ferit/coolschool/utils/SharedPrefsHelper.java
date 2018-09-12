package hr.ferit.coolschool.utils;

import android.content.Context;
import android.content.SharedPreferences;

import hr.ferit.coolschool.model.User;

import static android.content.Context.MODE_PRIVATE;
import static android.content.SharedPreferences.Editor;

public class SharedPrefsHelper {

    private SharedPreferences sharedPreferences;
    private Editor editor;
    private Context mContext;

    private static final String PREF_NAME = "SessionControl";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_COOKIE = "cookie";

    public SharedPrefsHelper(Context mContext) {
        this.mContext = mContext;
        this.sharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }

    public void setAuthenticatedUserInfo(User user) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.apply();
    }

    public User getAuthenticatedUserInfo() {
        User user = new User();
        user.setPassword(sharedPreferences.getString(KEY_PASSWORD, null));
        user.setUsername(sharedPreferences.getString(KEY_USERNAME, null));
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
