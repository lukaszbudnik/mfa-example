package com.github.lukaszbudnik.mfa;

import java.util.List;

public class CredentialRepositoryMock implements com.warrenstrange.googleauth.ICredentialRepository {

    private String secretKeyProperty = "secretKey";

    @Override
    public String getSecretKey(String userName) {
        String secretKey = System.getProperty(secretKeyProperty);

        return secretKey;
    }

    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
        System.setProperty(secretKeyProperty, secretKey);
    }
}
