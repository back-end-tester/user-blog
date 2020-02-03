package com.freenow.user.blog.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to Encapsulate data set by independent tests and get by dependent tests
 */
public class TestContextObject {
    private User user;
    private List<Post> posts;
    private List<Comment> comments;

    public TestContextObject() {
        posts = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
