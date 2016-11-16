package com.socialnetwork.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vasiliy Bobkov on 16.11.2016.
 */
public abstract class Validator {

    @RequiredArgsConstructor
    public enum ValidCode {
        INVALID_EMAIL("error.invalid.email"),
        INVALID_PASSWORD("error.invalid.password"),
        INVALID_FIRST_NAME("error.invalid.firstName"),
        INVALID_LAST_NAME("error.invalid.lastName"),
        INVALID_PASSWORD_CONFIRM("error.invalid.password.confirm"),
        SUCCESS("error.invalid.success");
        @Getter
        private final String propertyName;
    }

    public static ValidCode validateLoginForm(String email, String password) {
        if (!isValidEmail(email)) return ValidCode.INVALID_EMAIL;
        if (!isValidPassword(password)) return ValidCode.INVALID_PASSWORD;
        return ValidCode.SUCCESS;
    }

    private static boolean isValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static boolean isValidPassword(String password) {
//        ^                 # start-of-string
//        (?=.*[0-9])       # a digit must occur at least once
//        (?=.*[a-z])       # a lower case letter must occur at least once
//        (?=.*[A-Z])       # an upper case letter must occur at least once
//        (?=\S+$)          # no whitespace allowed in the entire string
//        .{8,16}             # anything, at least eight places though
//        $                 # end-of-string
        String pPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$";
        Pattern p = Pattern.compile(pPattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static ValidCode validateRegistration(String firstName, String lastName, String email, String password, String passwordConfirm) {
        if (!isValidName(firstName)) return ValidCode.INVALID_FIRST_NAME;
        if (!isValidName(lastName)) return ValidCode.INVALID_LAST_NAME;
        if (!isValidEmail(email)) return ValidCode.INVALID_EMAIL;
        if (!isValidPassword(password)) return ValidCode.INVALID_PASSWORD;
        if (!password.equals(passwordConfirm)) return ValidCode.INVALID_PASSWORD_CONFIRM;
        return ValidCode.SUCCESS;
    }

    private static boolean isValidName(String name) {
        final int MAX_CHARS_IN_NAME = 17;
        String nPattern = "^([^\\s]*[a-zA-Z\\u0430-\\u044f\\u0451\\u0410-\\u042f\\u0401\\-]){2,}$";
        Pattern p = Pattern.compile(nPattern);
        Matcher m = p.matcher(name);
        if (m.matches() && name.length() < MAX_CHARS_IN_NAME) return true;
        return false;
    }
}