package hr.ferit.coolschool.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Objects;

import hr.ferit.coolschool.R;
import hr.ferit.coolschool.activity.fragment.StudentRegistrationFragment;
import hr.ferit.coolschool.activity.fragment.TeacherRegistrationFragment;

import static hr.ferit.coolschool.utils.Constants.REGISTRATION_ACTY_BOOL_EXTRA;

public class RegistrationActivity extends FragmentActivity
        implements StudentRegistrationFragment.OnFragmentInteractionListener,
        TeacherRegistrationFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        boolean isStudent = Objects.requireNonNull(
                getIntent().getExtras()).getBoolean(REGISTRATION_ACTY_BOOL_EXTRA);

        if (isStudent) {
            setStudentRegistrationFragment();
        } else {
            setTeacherRegistrationFragment();
        }
    }

    private void setStudentRegistrationFragment() {
        StudentRegistrationFragment studentRegistrationFragment = new StudentRegistrationFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_registration, studentRegistrationFragment)
                .commit();
    }

    private void setTeacherRegistrationFragment() {
        TeacherRegistrationFragment teacherRegistrationFragment = new TeacherRegistrationFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_registration, teacherRegistrationFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

