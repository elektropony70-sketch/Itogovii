package org.example.Auto;

import io.restassured.response.ValidatableResponse;
import org.example.Auto.client.UserClient;
import org.example.Auto.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class StellarBurgersUserLoginTest extends BaseTest {

    private String email;

    @Before
    public void setUp() {
        email = "login_user_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(email, User.DEFAULT_PASSWORD, "LoginTester");

        ValidatableResponse response = userClient.register(user);
        accessToken = response.extract().path("accessToken");
    }

    // 1 ВХОД ПОД СУЩЕСТВУЮЩИМ ПОЛЬЗОВАТЕЛЕМ
    @Test
    public void testLoginExistingUserSuccess() {
        User loginCredentials = new User(email, User.DEFAULT_PASSWORD);

        userClient.login(loginCredentials)
                .statusCode(200) // По документации статус 200
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }

    // 2 ВХОД С НЕВЕРНЫМ ПАРОЛЕМ
    @Test
    public void testLoginWithInvalidCredentialsThrowsError() {
        User wrongCredentials = new User(email, "wrong_password_xyz");

        userClient.login(wrongCredentials)
                .statusCode(401) // 401 Unauthorized со стр. 3 документации
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}