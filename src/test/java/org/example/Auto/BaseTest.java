package org.example.Auto;

import org.example.Auto.client.UserClient;
import org.junit.After;

public class BaseTest {
    protected final UserClient userClient = new UserClient();
    protected String accessToken;

    @After
    public void tearDown() {
        // Если в процессе теста был получен токен — удаляем пользователя
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }
}
