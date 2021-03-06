package com.freenow.user.blog.constant;

public final class TestConstants {

    private TestConstants() {
    }

    public static final String BASE_URI = "https://jsonplaceholder.typicode.com/";
    public static final String USERS_URL = "/users";
    public static final String CONTEXT_OBJECT_KEY = "contextObject";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String POST_ID_KEY = "postId";
    public static final String USER_ID_KEY = "userId";
    public static final String POSTS_URL = "/posts";
    public static final String COMMENTS_URL = "/comments";
    public static final int TITLE_MAX_NO_OF_CHAR = 150;
    public static final int BODY_MAX_NO_OF_CHAR = 500;
    public static final String ZIP_CODE_PATTERN = "^[0-9]{5}(?:-[0-9]{4})?$";

}
