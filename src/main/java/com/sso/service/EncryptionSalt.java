package com.sso.service;

public interface EncryptionSalt {
    String getSaltEncryptedValue(String password);
}
