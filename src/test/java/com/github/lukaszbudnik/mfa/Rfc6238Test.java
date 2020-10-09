package com.github.lukaszbudnik.mfa;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.cryptacular.codec.Base32Decoder;
import org.cryptacular.generator.TOTPGenerator;
import org.jboss.aerogear.security.otp.Totp;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class Rfc6238Test {

    private String application = "lukaszbudnik";
    private String testUser = "test-user-6238";

    @Test
    public void test() {
        // adding fixed secretKey so that test is repeatable
        String secretKey = "PC5IVGTXGBW5KUK6";

        Totp totp = new Totp(secretKey);

        String uri = totp.uri(testUser) + "&issuer=" + application;
        System.out.println("uri: " + uri);

        File file = QRCode.from(uri).to(ImageType.PNG).withSize(250, 250).file();
        System.out.println("Open below file and configure your authenticator app");
        System.out.println("file: " + file);

        ByteBuffer bytes = ByteBuffer.allocate(10);
        new Base32Decoder().decode(CharBuffer.wrap(secretKey), bytes);

        // in Unit test we get the code directly from TOTPGenerator object
        // check in your authenticator app that you have the same code
        TOTPGenerator generator = new TOTPGenerator();
        // concatenate with empty string to convert the code to string
        String verificationCode = "" + generator.generate(bytes.array());
        System.out.println("verificationCode: " + verificationCode);

        boolean authorised = totp.verify(verificationCode);

        Assert.assertTrue(authorised);
    }

}
