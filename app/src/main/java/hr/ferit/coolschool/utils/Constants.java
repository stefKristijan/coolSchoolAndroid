package hr.ferit.coolschool.utils;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.model.Subject;

import static java.util.Arrays.asList;

public class Constants {

    public static final String REGISTRATION_ACTY_BOOL_EXTRA = "isStudent";
    public static final String DEFAULT_ERROR = "Došlo je do pogreške. Pokušajte ponovno kasnije.";
    public static final String USER_KEY = "user";
    public static final String COOKIE_KEY = "cookie";
    public static final String QUIZ_KEY = "quiz";
    public static final String LOGIN_FAIL = "Pogrešno korisničko ime i/ili lozinka";
    public static final String NAME_REGEX = "^[a-zA-ZŠšČčĆćŽžĐđ ]+$";
    public static final String USERNAME_REGEX = "^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$";
    public static final String EMAIL_REGEX = "^([A-Za-z0-9]+(?:[\\._-][A-Za-z0-9]+)*)@([A-Za-z0-9]+(?:[\\._-][A-Za-z0-9]+)*)\\.[a-z]{2,4}$";

    public static List<String> getClassList() {
        return asList(
                "-",
                "1. razred",
                "2. razred",
                "3. razred",
                "4. razred",
                "5. razred",
                "6. razred",
                "7. razred",
                "8. razred"
        );
    }

    public static List<String> getDifficulties() {
        return asList(
                "-",
                "Najjednostavnija (1)",
                "Vrlo jednostavna (2)",
                "Jednostavna (3)",
                "Umjerena (4)",
                "Srednja (5)",
                "Teška (6)",
                "Vrlo teška (7)",
                "Stručna (8)",
                "Profesionalna (9)",
                "Doktorska (10)"
        );
    }
    public static List<String> getSpinnerSubjects(){
        List<String> subjects = new ArrayList<>();
        subjects.add("-");
        for(Subject subject : Subject.values()){
            subjects.add(subject.toString());
        }
        return subjects;
    }
}
