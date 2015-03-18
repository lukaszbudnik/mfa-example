package com.github.lukaszbudnik.mfa;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MfaTest {

    private String testUser = "account";

    @Test
    public void test() throws IOException {

        GoogleAuthenticator gAuth = new GoogleAuthenticator();

        GoogleAuthenticatorKey key = gAuth.createCredentials(testUser);

        String url = GoogleAuthenticatorQRGenerator.getOtpAuthURL("lukaszbudnik", testUser, key);

        System.out.println(url);

        System.out.println("Copy above url into browser and configure google auth...");

        int verificationCode = 0;

        boolean authorised = gAuth.authorizeUser(testUser, verificationCode);

        Assert.assertTrue(authorised);
    }

}
