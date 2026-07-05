package org.example.Auto;

import io.restassured.response.ValidatableResponse;
import org.example.Auto.client.UserClient;
import org.example.Auto.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class StellarBurgersUserRegistrationTest {

    private final UserClient userClient = new UserClient();
    private String accessToken; // Храним токен для удаления пользователя после теста

    private String uniqueEmail;
    private final String password = "password123";
    private final String name = "RegisterTester";

    @Before
    public void setUp() {
        // Генерируем уникальный email перед каждым тестом
        uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
    }

    @After
    public void tearDown() {
        // Если тест успешно создал пользователя и получил токен — удаляем его
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }


    // СОЗДАНИЕ УНИКАЛЬНОГО ПОЛЬЗОВАТЕЛЯ

    @Test
    public void testCreateUniqueUserSuccess() {
        User user = new User(uniqueEmail, password, name);

        ValidatableResponse response = userClient.register(user)
                .statusCode(200) // По доке стр. 4 успешный статус 200
                .body("success", is(true))
                .body("user.email", equalTo(uniqueEmail.toLowerCase()))
                .body("user.name", equalTo(name))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

        // Запоминаем токен, чтобы сработал tearDown()
        accessToken = response.extract().path("accessToken");
    }

    // СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ, КОТОРЫЙ УЖЕ ЗАРЕГИСТРИРОВАН

    @Test
    public void testCreateExistingUserThrowsError() {
        // Регистрируем первого пользователя
        User firstUser = new User(uniqueEmail, password, name);
        ValidatableResponse firstResponse = userClient.register(firstUser);
        accessToken = firstResponse.extract().path("accessToken"); // Сохраняем для удаления

        // Пытаемся зарегистрировать точно такого же пользователя второй раз
        User duplicateUser = new User(uniqueEmail, password, name);
        userClient.register(duplicateUser)
                .statusCode(403) // Ожидаем 403 Forbidden согласно странице 4 документации
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }


    // СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ БЕЗ ОБЯЗАТЕЛЬНОГО ПОЛЯ

    @Test
    public void testCreateUserWithoutEmailThrowsError() {
        // Передаем пустую строку вместо email
        User userWithoutEmail = new User("", password, name);

        userClient.register(userWithoutEmail)
                .statusCode(403) // Ожидаем 403 Forbidden согласно странице 4 документации
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}