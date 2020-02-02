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

    /**
     * Setting baseURI for test
     */
    @BeforeClass
    public void setBaseUrl() {
        RestAssured.baseURI = BASE_URI;
    }

    /**
     * @param testContext This is used to storing and retrieving objects between tests
     */

    @Test(description = "Validating get users API returns success response and populating userId of a user in " +
        "TestContextObject")
    public void testGetUserInfo(ITestContext testContext) {
        List<User> users = given().when().get(USERS_URL).then().statusCode(HttpStatus.SC_OK).contentType(
            ContentType.JSON).extract().body().jsonPath().getList(".", User.class);
        User fetchedUser = TestUtils.getUserByUserName(users, "Samantha");
        TestContextObject testContextObject = new TestContextObject();
        testContextObject.setUserId(fetchedUser.getId());
        testContext.setAttribute(TestConstants.CONTEXT_OBJECT_KEY, testContextObject);
        Assert.assertNotNull(users);
    }

    /**
     * @param testContext This is used to storing and retrieving objects between tests
     */
    @Test(
        description = "Validating get posts by userId API returns success response and populating posts of a user in " +
            "TestContextObject", dependsOnMethods = {"testGetUserInfo"})
    public void testGetPostsByUserId(ITestContext testContext) {
        TestContextObject testContextObject = (TestContextObject) testContext.getAttribute(
            TestConstants.CONTEXT_OBJECT_KEY);
        List<Post> posts = given().when().queryParams(TestConstants.USER_ID_KEY, testContextObject.getUserId()).get(
            TestConstants.POSTS_URL).then().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON).extract().body()
            .jsonPath().getList(".", Post.class);
        testContextObject.setPosts(posts);
        Assert.assertNotNull(posts);
    }

    /**
     * @param testContext This is used to storing and retrieving objects between tests
     */
    @Test(description = "Validating get comments by postId returns success response and populating comments " +
        "TestContextObject", dependsOnMethods = {"testGetPostsByUserId"})
    public void testGetCommentsByPostIds(ITestContext testContext) {
        TestContextObject testContextObject = (TestContextObject) testContext.getAttribute(
            TestConstants.CONTEXT_OBJECT_KEY);
        testContextObject.getPosts().forEach(post -> {
            List<Comment> comments = given().when().queryParams(TestConstants.POST_ID_KEY,
                testContextObject.getUserId()).get(TestConstants.COMMENTS_URL).then().statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON).extract().body().jsonPath().getList(".", Comment.class);
            testContextObject.getComments().addAll(comments);
        });
    }

    /**
     * @param testContext This is used to storing and retrieving objects between tests
     */
    @Test(description = "Validating emails in Comments", dependsOnMethods = {"testGetCommentsByPostIds"})
    public void testValidEmail(ITestContext testContext) {
        TestContextObject testContextObject = (TestContextObject) testContext.getAttribute(
            TestConstants.CONTEXT_OBJECT_KEY);
        Pattern pattern = Pattern.compile(TestConstants.EMAIL_PATTERN);
        testContextObject.getComments().forEach(comment -> {
            Assert.assertTrue(pattern.matcher(comment.getEmail()).matches());
        });
    }

    /**
     * HttpStatus could be asserted as Not Found:404 in case of data not found, but these APIs always return 200. If
     * data is not found then
     * empty list is returned
     */
    @Test(description = "Validating that no posts are found against a user who does not exist")
    public void testValidateNoPostsFoundAgainstAnArbitraryUserId() {
        List<Post> posts = given().when().queryParams(TestConstants.USER_ID_KEY, "tulsi").get(TestConstants.POSTS_URL)
            .then().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON).extract().body().jsonPath().getList(".",
                Post.class);
        Assert.assertEquals(posts.size(), 0);
    }

    /**
     * Even I have provided empty string as body this API returns 201
     * I could assert Bad Request: 400 status and also assery the error messages if schema validation was done at
     * server side.
     * So I can not create negative test case for this
     */
    @Test(description = "Validating a user is successfully create")
    public void testValidateCreateUser() {
        User user = given().when().body("").post(USERS_URL).then().statusCode(HttpStatus.SC_CREATED).contentType(
            ContentType.JSON).extract().body().as(User.class);
        Assert.assertNotNull(user.getId());
    }

}
