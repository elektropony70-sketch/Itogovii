package org.example.auto;

import org.example.auto.client.UserClient;
import org.junit.After;

public class BaseTest {
    protected final UserClient userClient = new UserClient();
    protected String accessToken;

    @After
    public void tearDown() {

        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }
}
