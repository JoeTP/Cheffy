package com.example.cheffy.utils;

import android.widget.EditText;

public abstract class Validator {
    public static boolean validateEmpty(EditText et) {
        boolean isValid = true;
        String inputString = et.getText().toString().trim();
        if (inputString.isEmpty()) {
            et.setError("Field cannot be empty");
            isValid = false;
        }
        return isValid;
    }

    public static boolean validateEmail(EditText et) {
        boolean isValid = true;
        String inputString = et.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (inputString.isEmpty()) {
            et.setError("Field cannot be empty");
            isValid = false;
        } else if (!inputString.matches(emailPattern)) {
            et.setError("Invalid email address");
            isValid = false;
        }
        return isValid;
    }

    public static boolean validatePassword(EditText et) {
        String inputString = et.getText().toString().trim();
        boolean isValid = true;
        String upperCasePattern = ".*[A-Z].*";
        String specialCharPattern = ".*[!@#$%^&*].*";
        if (inputString.isEmpty()) {
            et.setError("Field cannot be empty");
            isValid = false;
        } else if (inputString.length() < 8) {
            et.setError("Password must be at least 8 characters");
            isValid = false;
        } else if (!inputString.matches(upperCasePattern)) {
            et.setError("Password must contain at least one uppercase letter");
            isValid = false;
        } else if (!inputString.matches(specialCharPattern)) {
            et.setError("Password must contain at least one special character");
            isValid = false;
        }
        return isValid;
    }
    public static boolean validateMismatch(EditText et1, EditText et2){
        String inputString1 = et1.getText().toString().trim();
        String inputString2 = et2.getText().toString().trim();
        boolean isValid = true;
        if (!inputString1.equals(inputString2)) {
            et2.setError("Passwords do not match");
            isValid = false;
        }
        return isValid;
    }
}
