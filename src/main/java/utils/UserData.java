package utils;

import java.util.Random;
import java.util.UUID;

public class UserData {
    private static final String[] NAMES = {"Саша", "Лена", "Коля", "Маша", "Даша"};
    private static final Random RANDOM = new Random();

    public static String getRandomName() {
        return NAMES[RANDOM.nextInt(NAMES.length)];
    }

    public static String getRandomEmail() {
        return "user" + UUID.randomUUID() + "@example.com";
    }

    public static String getRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 12);
    }
}
