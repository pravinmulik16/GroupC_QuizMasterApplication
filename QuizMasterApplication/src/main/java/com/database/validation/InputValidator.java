package com.database.validation;

import java.util.regex.Pattern;

public final class InputValidator {

    // Regular expression for standard email validation (abc@xyz.com)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    // Prevents initialization of utility class
    private InputValidator() {
    }

    /**
     * Checks if a text field is empty or null.
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates email format per BRD rules.
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Confirms password has at least 6 characters.
     */
    public static boolean isValidPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        return password.length() >= 6;
    }

    /**
     * Confirms mobile number contains exactly 10 numerical digits.
     */
    public static boolean isValidMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        return mobile.matches("\\d{10}");
    }
}
