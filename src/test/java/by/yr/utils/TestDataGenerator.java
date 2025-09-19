package by.yr.utils;

import java.util.Random;

public class TestDataGenerator {
    public static final String VALID_EMAIL="webnwmsbfxafriczku@nespj.com";
    public static final String VALID_PSW="mypsw12345";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public static String generateRandomEmail() {
        return "user" + System.currentTimeMillis() + "@example.com";
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public static String getValidPsw(){
        return VALID_PSW;
    }

    public static String getValidEmail(){
        return VALID_EMAIL;
    }
}
