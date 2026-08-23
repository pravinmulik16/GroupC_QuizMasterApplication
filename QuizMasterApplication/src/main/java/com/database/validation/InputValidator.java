package com.database.validation;

import java.util.regex.Pattern;

public final class InputValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private InputValidator() {
    }


    public static boolean isEmpty(String value) {

        return value == null || value.trim().isEmpty();
    }


    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }


    public static boolean isValidPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        return password.length() >= 6;
    }


    public static boolean isValidMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        return mobile.matches("\\d{10}");
    }
}
