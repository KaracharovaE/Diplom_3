package utils;

import static utils.Utils.randomString;

public class UserGenerator {

    public static User randomUser() {
        return new User()
                .withEmail(generateRandomEmail())
                .withPassword(randomString(12))
                .withName(randomString(20));
    }

    private static String generateRandomEmail() {

        String username = randomString(8);
        String domain = "gmail.com";
        return username + "@" + domain;
    }
}