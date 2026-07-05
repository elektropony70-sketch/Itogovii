package org.example.Auto.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Auto.models.LogoutRequest;
import org.example.Auto.models.User;



import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String BASE_URI = "https://stellarburgers.education-services.ru/api";

    private String formatToken(String token) {
        if (token != null && !token.startsWith("Bearer ")) {
            return "Bearer " + token;
        }
        return token;
    }

    // 1 Создание пользователя (POST /api/auth/register)
    public ValidatableResponse register(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URI + "/auth/register")
                .then();
    }

    // 2 Логин пользователя (POST /api/auth/login)
    public ValidatableResponse login(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URI + "/auth/login")
                .then();
    }

    // 3 Удаление пользователя (DELETE /api/auth/user)
    public ValidatableResponse delete(String accessToken) {
        return given()
                .header("Authorization", formatToken(accessToken)) // Защита токена
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URI + "/auth/user")
                .then();
    }
}