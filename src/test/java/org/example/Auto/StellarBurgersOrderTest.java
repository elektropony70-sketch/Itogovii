package org.example.Auto;

import io.restassured.response.ValidatableResponse;
import org.example.Auto.client.OrderClient;
import org.example.Auto.models.Order;
import org.example.Auto.models.User;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class StellarBurgersOrderTest extends BaseTest {

    private final OrderClient orderClient = new OrderClient();
    private List<String> validIngredients;

    @Before
    public void setUp() {
        // 1. Регистрируем уникального пользователя через константы класса User
        String uniqueEmail = "order_user_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(uniqueEmail, User.DEFAULT_PASSWORD, "OrderTester");

        ValidatableResponse response = userClient.register(user);
        // Переменная accessToken унаследована из BaseTest
        accessToken = response.extract().path("accessToken");

        // 2. ДИНАМИЧЕСКИ получаем только свежие ID ингредиентов через наш OrderClient
        List<String> allIds = orderClient.getIngredients()
                .contentType(io.restassured.http.ContentType.JSON) // Защита от HTML-заглушек nginx
                .extract()
                .path("data._id");

        // Берём первые два реально существующих ID ингредиента
        validIngredients = List.of(allIds.get(0), allIds.get(1));
    }

    // 1. УСПЕШНОЕ СОЗДАНИЕ ЗАКАЗА С АВТОРИЗАЦИЕЙ И ИНГРЕДИЕНТАМИ
    @Test
    public void testCreateOrderWithAuthAndIngredientsSuccess() {
        Order order = new Order(validIngredients);

        orderClient.createOrder(order, accessToken)
                .statusCode(200)
                .body("success", is(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    // 2. СОЗДАНИЕ ЗАКАЗА БЕЗ АВТОРИЗАЦИИ (Особенность бэкенда: возвращает 200)
    @Test
    public void testCreateOrderWithoutAuthSuccess() {
        Order order = new Order(validIngredients);

        orderClient.createOrderWithoutAuth(order)
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    // 3. СОЗДАНИЕ ЗАКАЗА БЕЗ ИНГРЕДИЕНТОВ (400 Bad Request)
    @Test
    public void testCreateOrderWithoutIngredientsThrowsError() {
        Order emptyOrder = new Order(List.of());

        orderClient.createOrder(emptyOrder, accessToken)
                .statusCode(400)
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    // 4. СОЗДАНИЕ ЗАКАЗА С НЕВЕРНЫМ ХЕШЕМ ИНГРЕДИЕНТА (500 Internal Server Error)
    @Test
    public void testCreateOrderWithInvalidIngredientHashThrowsError() {
        Order invalidOrder = new Order(List.of("invalid_hash_12345"));

        orderClient.createOrder(invalidOrder, accessToken)
                .statusCode(500);
    }
}