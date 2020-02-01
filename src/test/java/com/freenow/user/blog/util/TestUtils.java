package com.freenow.user.blog.util;

import com.freenow.user.blog.dto.User;

import java.util.List;

public final class TestUtils {
    private TestUtils() {
    }

    public static User getUserByUserName(List<User> users, String name) {
        User userFound = users.stream().filter(user -> user.getUsername().equalsIgnoreCase(name)).findFirst().get();
        return userFound;
    }
}
