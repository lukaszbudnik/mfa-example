package com.github.lukaszbudnik.mfa;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.jboss.aerogear.security.otp.Totp;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class Rfc4226Test {

    private String application = "lukaszbudnik";
    private String testUser = "test-user-4226";

    @Test
    public void test() {
        // adding fixed secretKey so that test is repeatable
        // or use org.jboss.aerogear.security.otp.api.Base32.random()
        String secretKey = "IB6J7QGNUWGSWHY3";

        Totp totp = new Totp(secretKey);

        String uri = totp.uri(testUser) + "&issuer=" + application;
        System.out.println("uri: " + uri);

        File file = QRCode.from(uri).to(ImageType.PNG).withSize(250, 250).file();
        System.out.println("Open below file and configure your authenticator app");
        System.out.println("file: " + file);

        // in Unit test we get the code directly from Totp object
        // check in your authenticator app that you have the same code
        String verificationCode = totp.now();
        System.out.println("verificationCode: " + verificationCode);

        boolean authorised = totp.verify(verificationCode);

        Assert.assertTrue(authorised);
    }

}
