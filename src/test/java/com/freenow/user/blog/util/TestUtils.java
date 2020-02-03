package com.freenow.user.blog.util;

import com.freenow.user.blog.dto.User;

import java.util.List;
import java.util.regex.Pattern;

public final class TestUtils {
    private TestUtils() {
    }

    /**
     * @param users List of users from which a user to find
     * @param name Name of user against which to query
     * @return User found against value provided in name
     */
    public static User getUserByUserName(List<User> users, String name) {
        User userFound = users.stream().filter(user -> user.getUsername().equalsIgnoreCase(name)).findFirst().get();
        return userFound;
    }

    public static boolean isCharSeqValid(String charSeq, String pattern) {
        return Pattern.compile(pattern).matcher(charSeq).matches();
    }
}
