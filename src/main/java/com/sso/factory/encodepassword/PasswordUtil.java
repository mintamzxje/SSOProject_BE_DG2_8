package com.sso.factory.encodepassword;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        String passwordWithSalt = salt + password;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedPassword = md.digest(passwordWithSalt.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
