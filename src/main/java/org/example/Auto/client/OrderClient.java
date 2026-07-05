package org.example.Auto.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Auto.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String BASE_URI = "https://stellarburgers.education-services.ru/api";

    // 1 Создание заказа С авторизацией (POST /api/orders)
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_URI + "/orders")
                .then();
    }

    // 2 Создание заказа БЕЗ авторизации (POST /api/orders)
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_URI + "/orders")
                .then();
    }


    // 4 Все ингредиенты в системе (GET /api/ingredients)
    public ValidatableResponse getIngredients() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/ingredients")
                .then();
    }
}
