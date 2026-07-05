package org.example.Auto;

import io.restassured.response.ValidatableResponse;
import org.example.Auto.client.UserClient;
import org.example.Auto.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class StellarBurgersUserRegistrationTest {

    private final UserClient userClient = new UserClient();
    private String accessToken;

    private String uniqueEmail;
    private final String password = "password123";
    private final String name = "RegisterTester";

    @Before
    public void setUp() {
        // Уникальный email через UUID
        uniqueEmail = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@yandex.ru";
        accessToken = null;
    }

    @After
    public void tearDown() {
        // Зачистка данных после каждого теста
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    // 1. СОЗДАНИЕ УНИКАЛЬНОГО ПОЛЬЗОВАТЕЛЯ
    @Test
    public void testCreateUniqueUserSuccess() {
        User user = new User(uniqueEmail, password, name);

        ValidatableResponse response = userClient.register(user)
                .statusCode(200)
                .body("success", is(true))
                .body("user.email", equalTo(uniqueEmail.toLowerCase()))
                .body("user.name", equalTo(name))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

        accessToken = response.extract().path("accessToken");
    }

    // 2. СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ, КОТОРЫЙ УЖЕ ЗАРЕГИСТРИРОВАН
    @Test
    public void testCreateExistingUserThrowsError() {
        User firstUser = new User(uniqueEmail, password, name);
        ValidatableResponse firstResponse = userClient.register(firstUser);

        // Сохраняем токен первого пользователя для удаления в tearDown
        accessToken = firstResponse.extract().path("accessToken");

        // Попытка дублирования
        User duplicateUser = new User(uniqueEmail, password, name);
        userClient.register(duplicateUser)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    // 3. СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ БЕЗ ОБЯЗАТЕЛЬНОГО ПОЛЯ
    @Test
    public void testCreateUserWithoutEmailThrowsError() {
        // За счет @JsonInclude в классе User, поле email просто не попадет в тело запроса
        User userWithoutEmail = new User(null, password, name);

        userClient.register(userWithoutEmail)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}