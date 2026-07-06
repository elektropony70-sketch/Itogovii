package org.example.UI;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.ValidatableResponse;
import org.example.sorce.Auto.client.UserClient;
import org.example.sorce.Auto.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class BaseUiTest {

    protected final UserClient userClient = new UserClient();
    protected final String browserType;
    protected String accessToken;

    public BaseUiTest(String browserType) {
        this.browserType = browserType;
    }

    @Parameterized.Parameters(name = "Браузер для теста: {0}")
    public static Collection<Object[]> getBrowsers() {
        return Arrays.asList(new Object[][] {
                {"chrome"},
                {"yandex"}
        });
    }

    protected User createAndRegisterUniqueUser() {
        String cleanId = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        String uniqueEmail = "user_" + cleanId + "@yandex.ru";
        String uniquePassword = "burgerpass" + cleanId;
        String uniqueName = "Alex_" + cleanId;

        User testUser = new User(uniqueEmail, uniquePassword, uniqueName);
        ValidatableResponse response = userClient.register(testUser);

        if (response != null) {
            this.accessToken = response.extract().path("accessToken");
        }
        return testUser;
    }

    @Before
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        if ("yandex".equals(browserType)) {
            String userHome = System.getProperty("user.home");
            String s = File.separator;
            String yandexBinaryPath = userHome + s + "AppData" + s + "Local" + s + "Yandex" + s + "YandexBrowser" + s + "Application" + s + "browser.exe";

            File file = new File(yandexBinaryPath);
            if (!file.exists()) {
                yandexBinaryPath = "C:" + s + "Program Files" + s + "Yandex" + s + "YandexBrowser" + s + "Application" + s + "browser.exe";
            }

            options.setBinary(yandexBinaryPath);
            WebDriverManager.chromedriver().driverVersion("148.0.7778.0").setup();
        } else {
            WebDriverManager.chromedriver().setup();
        }

        Configuration.browserCapabilities = options;
    }

    @After
    public void tearDown() {
        try {
            if (accessToken != null) {
                userClient.delete(accessToken);
            }
        } finally {
            accessToken = null;
            WebDriverRunner.closeWebDriver();
        }
    }
}
