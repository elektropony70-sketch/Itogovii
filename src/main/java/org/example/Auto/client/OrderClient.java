package org.example.Auto.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Auto.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String BASE_URI = "https://stellarburgers.education-services.ru/api";

    // 1 Создание заказа С авторизацией
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_URI + "/orders")
                .then();
    }

    // 2 Создание заказа БЕЗ авторизации
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_URI + "/orders")
                .then();
    }

    // 3 Заказы системы (GET /api/orders/all)
    public ValidatableResponse getAllOrders() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/orders/all")
                .then();
    }

    // 4. Получить заказы конкретного пользователя (GET /api/orders)
    public ValidatableResponse getUserOrders(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URI + "/orders")
                .then();
    }

    public ValidatableResponse getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .get("https://stellarburgers.education-services.ru/api")
                .then();
    }

}
