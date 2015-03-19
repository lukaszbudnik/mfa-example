package com.github.lukaszbudnik.mfa;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class Rfc4226Test {

    private String application = "lukaszbudnik";
    private String testUser = "test-user-4226";

    @Test
    public void test() throws IOException {
        String secretKey = Base32.random();

        Totp totp = new Totp(secretKey);

        String uri = totp.uri(testUser) + "&issuer=" + application;

        System.out.println(uri);

        File file = QRCode.from(uri).to(ImageType.PNG).withSize(250, 250).file();

        System.out.println("Open above file and configure Google Authenticator");

        String verificationCode = "";

        boolean authorised = totp.verify(verificationCode);

        Assert.assertTrue(authorised);
    }

}
