package org.example.Auto.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Auto.models.User;


import static io.restassured.RestAssured.given;

public class UserClient {

    // Полный базовый URL с префиксом /api, чтобы эндпоинты склеивались корректно
    private static final String BASE_URI = "https://stellarburgers.education-services.ru/api";

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

    // 9. Удаление пользователя (DELETE /api/auth/user)
    public ValidatableResponse delete(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URI + "/auth/user")
                .then();
    }
}