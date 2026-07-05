package org.example.Auto;

import io.restassured.response.ValidatableResponse;
import org.example.Auto.models.User;
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

        // Переменная accessToken унаследована из BaseTest, tearDown сработает сам
        accessToken = response.extract().path("accessToken");
    }

    // 2. СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ, КОТОРЫЙ УЖЕ ЗАРЕГИСТРИРОВАН
    @Test
    public void testCreateExistingUserThrowsError() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@yandex.ru";
        User firstUser = new User(uniqueEmail, User.DEFAULT_PASSWORD, User.DEFAULT_NAME);
        ValidatableResponse firstResponse = userClient.register(firstUser);
        accessToken = firstResponse.extract().path("accessToken");

        // Пытаемся зарегистрировать точно такого же пользователя второй раз
        User duplicateUser = new User(uniqueEmail, User.DEFAULT_PASSWORD, User.DEFAULT_NAME);
        userClient.register(duplicateUser)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));
    }

    // 3. СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ БЕЗ ОБЯЗАТЕЛЬНОГО ПОЛЯ
    @Test
    public void testCreateUserWithoutEmailThrowsError() {
        // Передаем пустую строку вместо email
        User userWithoutEmail = new User("", User.DEFAULT_PASSWORD, User.DEFAULT_NAME);

        userClient.register(userWithoutEmail)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}