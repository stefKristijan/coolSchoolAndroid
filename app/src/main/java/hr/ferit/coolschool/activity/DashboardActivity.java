package hr.ferit.coolschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.activity.fragment.QuizFragment;
import hr.ferit.coolschool.activity.fragment.RankingFragment;
import hr.ferit.coolschool.activity.fragment.SettingsFragment;
import hr.ferit.coolschool.model.User;
import hr.ferit.coolschool.utils.SharedPrefsHelper;

import static hr.ferit.coolschool.utils.Constants.COOKIE_KEY;
import static hr.ferit.coolschool.utils.Constants.USER_KEY;

public class DashboardActivity extends AppCompatActivity
        implements SettingsFragment.OnLogoutListener,
        SettingsFragment.OnUserUpdateListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private int[] tabIcons = {
            R.drawable.ic_rank,
            R.drawable.ic_quiz,
            R.drawable.ic_settings,
    };
    private User mAuthdUser;
    private String mCookie;
    private SharedPrefsHelper mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuthdUser = (User) getIntent().getExtras().get(USER_KEY);
        mCookie = (String) getIntent().getExtras().get(COOKIE_KEY);
        mSharedPrefs = new SharedPrefsHelper(this);
        setActivity();

    }

    private void setActivity() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setUpViewPager();

        tabs = findViewById(R.id.tabs);
        setUpTabIcons();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    private void setUpViewPager() {
        Fragment rankingFragment = RankingFragment.newInstance(mAuthdUser, mCookie);
        Fragment quizFragment = QuizFragment.newInstance(mAuthdUser, mCookie);
        Fragment settingsFragment = SettingsFragment.newInstance(mAuthdUser, mCookie);
        mSectionsPagerAdapter.addFragment(rankingFragment);
        mSectionsPagerAdapter.addFragment(quizFragment);
        mSectionsPagerAdapter.addFragment(settingsFragment);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void setUpTabIcons() {
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabs.getTabAt(2)).setIcon(tabIcons[2]);
    }

    @Override
    public void onLogoutListener() {
        mSharedPrefs.setCookie(null);
        mSharedPrefs.setAuthenticatedUserInfo(null);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onUserUpdateListener(User updatedUser) {
        mAuthdUser = updatedUser;
        mSharedPrefs.setAuthenticatedUserInfo(mAuthdUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Kviz je uspješno dodan, osvježite podatke",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        void addFragment(Fragment fragment) {
            this.fragments.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
