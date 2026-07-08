package org.example.auto;

import io.restassured.response.ValidatableResponse;
import org.example.auto.models.User;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class StellarBurgersUserRegistrationTest extends BaseTest {


    @Test
    public void testCreateUniqueUserSuccess() {

        String uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(uniqueEmail, User.DEFAULT_PASSWORD, User.DEFAULT_NAME);

        ValidatableResponse response = userClient.register(user)
                .statusCode(200)
                .body("success", is(true))
                .body("user.email", equalTo(uniqueEmail.toLowerCase()))
                .body("user.name", equalTo(User.DEFAULT_NAME))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

        accessToken = response.extract().path("accessToken");
    }

    @Test
    public void testCreateExistingUserThrowsError() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
        User firstUser = new User(uniqueEmail, User.DEFAULT_PASSWORD, User.DEFAULT_NAME);
        ValidatableResponse firstResponse = userClient.register(firstUser);
        accessToken = firstResponse.extract().path("accessToken");


        User duplicateUser = new User(uniqueEmail, User.DEFAULT_PASSWORD, User.DEFAULT_NAME);
        userClient.register(duplicateUser)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    public void testCreateUserWithoutEmailThrowsError() {
        User userWithoutEmail = new User("", User.DEFAULT_PASSWORD, User.DEFAULT_NAME);

        userClient.register(userWithoutEmail)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}