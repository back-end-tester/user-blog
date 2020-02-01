package com.freenow.user.blog;

import com.freenow.user.blog.constant.TestConstants;
import com.freenow.user.blog.dto.Comment;
import com.freenow.user.blog.dto.Post;
import com.freenow.user.blog.dto.TestContextObject;
import com.freenow.user.blog.dto.User;
import com.freenow.user.blog.util.TestUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.regex.Pattern;

import static com.freenow.user.blog.constant.TestConstants.BASE_URI;
import static com.freenow.user.blog.constant.TestConstants.USERS_URL;
import static io.restassured.RestAssured.given;

public class ApiTest {

    @BeforeClass
    public void setBaseUrl() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test(description = "Validating get users API returns success response and populating userId of a user in " +
        "TestContextObject")
    public void testGetUserInfoWIth200(ITestContext testContext) {
        List<User> users = given().when().get(USERS_URL).then().statusCode(HttpStatus.SC_OK).contentType(
            ContentType.JSON).extract().body().jsonPath().getList(".", User.class);
        User fetchedUser = TestUtils.getUserByUserName(users, "Samantha");
        TestContextObject testContextObject = new TestContextObject();
        testContextObject.setUserId(fetchedUser.getId());
        testContext.setAttribute(TestConstants.CONTEXT_OBJECT_KEY, testContextObject);
        Assert.assertNotNull(users);
    }

    @Test(
        description = "Validating get posts by userId API returns success response and populating posts of a user in " +
            "TestContextObject", dependsOnMethods = {"testGetUserInfoWIth200"})
    public void testGetPostsByUserIdWith200(ITestContext testContext) {
        TestContextObject testContextObject = (TestContextObject) testContext.getAttribute(
            TestConstants.CONTEXT_OBJECT_KEY);
        List<Post> posts = given().when().queryParams(TestConstants.USER_ID_KEY, testContextObject.getUserId()).get(
            TestConstants.POSTS_URL).then().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON).extract().body()
            .jsonPath().getList(".", Post.class);
        testContextObject.setPosts(posts);
        Assert.assertNotNull(posts);
    }

    @Test(description = "Validating get comments by postId returns success response and populating comments " +
        "TestContextObject", dependsOnMethods = {"testGetPostsByUserIdWith200"})
    public void testGetCommentsByPostIdsWith200(ITestContext testContext) {
        Pattern.compile("").matcher("").matches();
        TestContextObject testContextObject = (TestContextObject) testContext.getAttribute(
            TestConstants.CONTEXT_OBJECT_KEY);
        testContextObject.getPosts().forEach(post -> {
            List<Comment> comments = given().when().queryParams(TestConstants.POST_ID_KEY,
                testContextObject.getUserId()).get(TestConstants.COMMENTS_URL).then().statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON).extract().body().jsonPath().getList(".", Comment.class);
            testContextObject.getComments().addAll(comments);

        });
    }

    @Test(description = "Validating emails in Comments", dependsOnMethods = {"testGetCommentsByPostIdsWith200"})
    public void testValidEmail(ITestContext testContext) {
        TestContextObject testContextObject = (TestContextObject) testContext.getAttribute(
            TestConstants.CONTEXT_OBJECT_KEY);
        Pattern pattern = Pattern.compile(TestConstants.EMAIL_PATTERN);
        testContextObject.getComments().forEach(comment -> {
            Assert.assertTrue(pattern.matcher(comment.getEmail()).matches());
        });
    }

}
