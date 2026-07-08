package org.example.Auto.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.Auto.models.Order;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String BASE_URI = "https://stellarburgers.education-services.ru/api";
    private final RequestSpecification baseSpec;

    // Конструктор настраивает спецификацию один раз для всего класса
    public OrderClient() {
        this.baseSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }

    // 1 Создание заказа С авторизацией (POST /api/orders)
    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .spec(baseSpec)
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post("/orders")
                .then();
    }

    // 2 Создание заказа БЕЗ авторизации (POST /api/orders)
    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .spec(baseSpec)
                .body(order)
                .when()
                .post("/orders")
                .then();
    }

    // 3 Все ингредиенты в системе (GET /api/ingredients)
    @Step("Получение списка ингредиентов")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(baseSpec)
                .when()
                .get("/ingredients")
                .then();
    }
}
