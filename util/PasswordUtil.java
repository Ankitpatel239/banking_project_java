package util;

import java.security.MessageDigest;

public class PasswordUtil {
    public static String hash (String password) throws Exception {
        // Simple hashing for demonstration (not secure)
     MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
