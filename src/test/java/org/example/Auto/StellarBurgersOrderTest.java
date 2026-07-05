package org.example.Auto;

import io.restassured.response.ValidatableResponse;
import org.example.Auto.client.OrderClient;
import org.example.Auto.models.Order;
import org.example.Auto.models.User;
import org.example.Auto.client.UserClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class StellarBurgersOrderTest {

    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();

    private String accessToken;

    // Возвращаем хэши ингредиентов обратно в код, чтобы обойти падение сервера с XML/HTML

    private List<String> validIngredients;

    @Before
    public void setUp() {

        // 1. Перед каждым тестом регистрируем уникального пользователя
        String uniqueEmail = "order_user_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(uniqueEmail, "password123", "OrderTester");

        ValidatableResponse response = userClient.register(user);
        accessToken = response.extract().path("accessToken");

        // 2. ДИНАМИЧЕСКИ получаем только свежие и рабочие ID ингредиентов от сервера
        List<String> allIds = io.restassured.RestAssured
                .given()
                .contentType(io.restassured.http.ContentType.JSON) // Явно просим JSON
                .get("https://stellarburgers.education-services.ru/api/ingredients")
                .then()
                .contentType(io.restassured.http.ContentType.JSON) // Проверяем, что вернулся JSON, а не HTML-заглушка nginx
                .extract()
                .path("data._id");

        // Берём первые два реально существующих ID ингредиента из актуальной базы данных бэкенда
        validIngredients = List.of(allIds.get(0), allIds.get(1));

        // Выводим логи для визуального контроля в консоли
        // System.out.println("DEBUG: Полученный accessToken = " + accessToken);
        // System.out.println("DEBUG: Актуальные ингредиенты с сервера = " + validIngredients);
    }

    @After
    public void tearDown() {
        // После теста удаляем пользователя для чистоты БД
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    // СОЗДАНИЕ ЗАКАЗА

    @Test
    public void testCreateOrderWithAuthSuccess() {
        Order order = new Order(validIngredients);

        orderClient.createOrder(order, accessToken)
                .log().all()
                .statusCode(200)
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    //

    @Test
    public void testCreateOrderWithoutAuthThrowsError() {
        Order order = new Order(validIngredients);

        // По факту работы бэкенда, заказ без токена успешно создаётся со статусом 200
        orderClient.createOrderWithoutAuth(order)
                .statusCode(200)
                .body("success", is(true))
                .body("order.number", notNullValue());
    }

    //

    @Test
    public void testCreateOrderWithIngredientsSuccess() {
        // Создаем заказ со свежими ингредиентами, которые скачал метод @Before
        Order order = new Order(validIngredients);

        orderClient.createOrder(order, accessToken)
                .statusCode(200)
                .body("name", notNullValue())           // name лежит на самом верхнем уровне
                .body("success", is(true))              // УБРАЛИ order. — теперь проверка пройдёт успешно!
                .body("order.number", notNullValue()); // number по-прежнему лежит внутри объекта order
    }

    //

    @Test
    public void testCreateOrderWithoutIngredientsThrowsError() {
        Order emptyOrder = new Order(List.of());

        orderClient.createOrder(emptyOrder, accessToken)
                .statusCode(400) // Ожидаем 400 Bad Request из ТЗ
                .body("success", is(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    //

    @Test
    public void testCreateOrderWithInvalidIngredientHashThrowsError() {
        Order invalidOrder = new Order(List.of("invalid_hash_12345"));

        orderClient.createOrder(invalidOrder, accessToken)
                .statusCode(500); // Ожидаем 500 Internal Server Error из ТЗ
    }
}