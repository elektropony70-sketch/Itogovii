package org.example.Auto.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.Auto.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String BASE_URI = "https://stellarburgers.education-services.ru/api";

    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_URI + "/orders")
                .then();
    }

    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(BASE_URI + "/orders")
                .then();
    }


}
