package org.example.auto;

import io.restassured.response.ValidatableResponse;
import org.example.auto.models.User;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class StellarBurgersUserRegistrationTest extends BaseTest {

    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_NAME = "RegisterTester";

    // 1 Создание уникального пользователя
    @Test
    public void testCreateUniqueUserSuccess() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(uniqueEmail, TEST_PASSWORD, TEST_NAME);

        ValidatableResponse response = userClient.register(user)
                .statusCode(200)
                .body("success", is(true))
                .body("user.email", equalTo(uniqueEmail.toLowerCase()))
                .body("user.name", equalTo(TEST_NAME))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

        accessToken = response.extract().path("accessToken");
    }

    // 2 Создание пользователя
    @Test
    public void testCreateExistingUserThrowsError() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
        User firstUser = new User(uniqueEmail, TEST_PASSWORD, TEST_NAME);
        ValidatableResponse firstResponse = userClient.register(firstUser);
        accessToken = firstResponse.extract().path("accessToken");

        User duplicateUser = new User(uniqueEmail, TEST_PASSWORD, TEST_NAME);
        userClient.register(duplicateUser)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    // 3 Регистрация без EMAIL
    @Test
    public void testCreateUserWithoutEmailThrowsError() {
        User userWithoutEmail = new User("", TEST_PASSWORD, TEST_NAME);

        userClient.register(userWithoutEmail)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    // 4 Регистрация без ПАРОЛЯ
    @Test
    public void testCreateUserWithoutPasswordThrowsError() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
        User userWithoutPassword = new User(uniqueEmail, "", TEST_NAME);

        userClient.register(userWithoutPassword)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    // 5 Регистрация без ИМЕНИ
    @Test
    public void testCreateUserWithoutNameThrowsError() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
        User userWithoutName = new User(uniqueEmail, TEST_PASSWORD, "");

        userClient.register(userWithoutName)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}