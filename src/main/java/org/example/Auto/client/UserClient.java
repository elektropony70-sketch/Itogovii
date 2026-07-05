package org.example.Auto.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Auto.models.LogoutRequest;
import org.example.Auto.models.PasswordReset;
import org.example.Auto.models.TokenRequest;
import org.example.Auto.models.User;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserClient {

    // Полный базовый URL с префиксом /api, чтобы эндпоинты склеивались корректно
    private static final String BASE_URI = "https://stellarburgers.education-services.ru/api";

    private String formatToken(String token) {
        if (token != null && !token.startsWith("Bearer ")) {
            return "Bearer " + token;
        }
        return token;
    }

    // 1. Создание пользователя (POST /api/auth/register)
    public ValidatableResponse register(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URI + "/auth/register")
                .then();
    }

    // 2. Логин пользователя (POST /api/auth/login)
    public ValidatableResponse login(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URI + "/auth/login")
                .then();
    }

    // 3. Восстановление пароля — Запрос кода (POST /api/password-reset)
    public ValidatableResponse forgotPassword(String email) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of("email", email))
                .when()
                .post(BASE_URI + "/password-reset")
                .then();
    }

    // 4. Восстановление пароля — Ввод нового пароля (POST /api/password-reset/reset)
    public ValidatableResponse resetPassword(PasswordReset passwordReset) {
        return given()
                .contentType(ContentType.JSON)
                .body(passwordReset)
                .when()
                .post(BASE_URI + "/password-reset/reset")
                .then();
    }

    // 5. Выход из системы (POST /api/auth/logout)
    public ValidatableResponse logout(LogoutRequest logoutRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(logoutRequest)
                .when()
                .post(BASE_URI + "/auth/logout")
                .then();
    }

    // 6. Обновление токена (POST /api/auth/token)
    public ValidatableResponse refreshToken(TokenRequest tokenRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(tokenRequest)
                .when()
                .post(BASE_URI + "/auth/token")
                .then();
    }

    // 7. Получение информации о пользователе (GET /api/auth/user)
    public ValidatableResponse getUserData(String accessToken) {
        return given()
                .header("Authorization", formatToken(accessToken)) // Защита токена
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/auth/user")
                .then();
    }

    // 8. Изменение информации о пользователе (PATCH /api/auth/user)
    public ValidatableResponse updateUserData(User user, String accessToken) {
        return given()
                .header("Authorization", formatToken(accessToken)) // Защита токена
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .patch(BASE_URI + "/auth/user")
                .then();
    }

    // 9. Удаление пользователя (DELETE /api/auth/user)
    public ValidatableResponse delete(String accessToken) {
        return given()
                .header("Authorization", formatToken(accessToken)) // Защита токена
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URI + "/auth/user")
                .then();
    }
}