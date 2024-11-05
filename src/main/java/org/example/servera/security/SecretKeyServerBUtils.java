package org.example.servera.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class SecretKeyServerBUtils {
    private static final String SECRET_KEY = "Babebu";

    public static String generateToken(String url, long time) throws NoSuchAlgorithmException {
        String data = url + time + SECRET_KEY;
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Change to MD5 if required
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    public static long getCurrentTime() {
        return Instant.now().getEpochSecond();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
