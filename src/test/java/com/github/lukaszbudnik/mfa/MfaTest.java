package com.github.lukaszbudnik.mfa;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MfaTest {

    private String testUser = "test-user";

    @Test
    public void test() throws IOException {

        GoogleAuthenticator gAuth = new GoogleAuthenticator();

        GoogleAuthenticatorKey key = gAuth.createCredentials();

        String url = GoogleAuthenticatorQRGenerator.getOtpAuthURL("lukaszbudnik", testUser, key);

        System.out.println(url);

        String totpUrl = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("lukaszbudnik", testUser, key);

        File file = QRCode.from(totpUrl).to(ImageType.PNG).withSize(250, 250).file("/tmp/" + key.getKey());

        System.out.println("Copy above url into browser or open above file and configure Google Authenticator");

        int verificationCode = 0;

        boolean authorised = gAuth.authorize(key.getKey(), verificationCode);

        Assert.assertTrue(authorised);
    }

}
