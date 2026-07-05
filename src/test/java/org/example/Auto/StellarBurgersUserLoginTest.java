package org.example.Auto;

import io.restassured.response.ValidatableResponse;
import org.example.Auto.client.UserClient;
import org.example.Auto.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class StellarBurgersUserLoginTest {

    private final UserClient userClient = new UserClient();
    private String accessToken; // Храним токен для удаления пользователя после теста

    private String email;
    private final String password = "login_password_123";
    private final String name = "LoginTester";

    @Before
    public void setUp() {
        // Перед каждым тестом регистрируем реального пользователя в базе
        email = "login_user_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(email, password, name);

        ValidatableResponse response = userClient.register(user);
        accessToken = response.extract().path("accessToken");
    }

    @After
    public void tearDown() {
        // Чистим базу данных стенда после теста
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }


    //  ВХОД ПОД СУЩЕСТВУЮЩИМ ПОЛЬЗОВАТЕЛЕМ

    @Test
    public void testLoginExistingUserSuccess() {
        // Создаем объект пользователя с верными учетными данными для логина
        User loginCredentials = new User(email, password);

        userClient.login(loginCredentials)
                .statusCode(200) // По доке стр. 4 успешный статус 200
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }


    // ВХОД С НЕВЕРНЫМ ЛОГИНОМ И ПАРОЛЕМ


    @Test
    public void testLoginWithInvalidCredentialsThrowsError() {
        User wrongCredentials = new User(email, "wrong_password_xyz");

        userClient.login(wrongCredentials)
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}