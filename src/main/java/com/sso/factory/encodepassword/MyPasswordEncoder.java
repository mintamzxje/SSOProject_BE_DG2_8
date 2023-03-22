package com.sso.factory.encodepassword;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        String salt = PasswordUtil.generateSalt();
        String hashedPassword;
        try {
            hashedPassword = PasswordUtil.hashPassword(rawPassword.toString(), salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
        return hashedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String salt = encodedPassword.substring(0, 24); // Assumes salt is 24 characters long
        String hashedPassword;
        try {
            hashedPassword = PasswordUtil.hashPassword(rawPassword.toString(), salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
        return encodedPassword.equals(hashedPassword);
    }
}
