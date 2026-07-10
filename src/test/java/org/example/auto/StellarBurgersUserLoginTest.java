package org.example.auto;

import io.restassured.response.ValidatableResponse;
import org.example.auto.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class StellarBurgersUserLoginTest extends BaseTest {

    private String email;
    private static final String TEST_PASSWORD = "password123";

    @Before
    public void setUp() {
        email = "login_user_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(email, TEST_PASSWORD, "LoginTester");

        ValidatableResponse response = userClient.register(user);
        accessToken = response.extract().path("accessToken");
    }

    // 1 ВХОД ПОД СУЩЕСТВУЮЩИМ ПОЛЬЗОВАТЕЛЕМ
    @Test
    public void testLoginExistingUserSuccess() {
        User loginCredentials = new User(email, TEST_PASSWORD);

        userClient.login(loginCredentials)
                .statusCode(200)
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    // 2 ВХОД С НЕВЕРНЫМ ПАРОЛЕМ
    @Test
    public void testLoginWithInvalidPasswordThrowsError() {
        User wrongCredentials = new User(email, "wrong_password_xyz");

        userClient.login(wrongCredentials)
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    // 3 ВХОД С НЕВЕРНЫМ ЛОГИН
    @Test
    public void testLoginWithInvalidEmailThrowsError() {
        User wrongCredentials = new User("non_existent_email_123@yandex.ru", TEST_PASSWORD);

        userClient.login(wrongCredentials)
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}