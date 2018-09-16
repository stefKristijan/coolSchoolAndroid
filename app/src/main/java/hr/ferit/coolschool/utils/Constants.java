package hr.ferit.coolschool.utils;

import java.util.ArrayList;
import java.util.List;

import hr.ferit.coolschool.model.City;
import hr.ferit.coolschool.model.State;
import hr.ferit.coolschool.model.Subject;

public class Constants {

    public static final String REGISTRATION_ACTY_BOOL_EXTRA = "isStudent";
    public static final String DEFAULT_ERROR = "Došlo je do pogreške. Pokušajte ponovno kasnije.";
    public static final String USER_KEY = "user";
    public static final String COOKIE_KEY = "cookie";
    public static final String LOGIN_FAIL = "Pogrešno korisničko ime i/ili lozinka";
    public static final String NAME_REGEX = "^[a-zA-ZŠšČčĆćŽžĐđ ]+$";
    public static final String USERNAME_REGEX = "^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$";
    public static final String EMAIL_REGEX = "^([A-Za-z0-9]+(?:[\\._-][A-Za-z0-9]+)*)@([A-Za-z0-9]+(?:[\\._-][A-Za-z0-9]+)*)\\.[a-z]{2,4}$";

    public static List<String> getSpinnerSubjects(){
        List<String> subjects = new ArrayList<>();
        subjects.add("-");
        for(Subject subject : Subject.values()){
            subjects.add(subject.toString());
        }
        return subjects;
    }

    public static List<String> getSpinnerStates(){
        List<String> subjects = new ArrayList<>();
        subjects.add("-");
        for(State state : State.values()){
            subjects.add(state.getValue());
        }
        return subjects;
    }

    public static List<String> getSpinnerCities(){
        List<String> cities = new ArrayList<>();
        cities.add("-");
        for(City city : City.values()){
            cities.add(city.toString());
        }
        return cities;
    }
}
