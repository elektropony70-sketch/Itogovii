package org.example.UI;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.sorce.ruto.User;
import org.example.sorce.ruto.UserClient;
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
    protected String accessToken;

    // Убрали конструктор и аннотацию @Parameterized.Parameters

    @Step("Создание и регистрация уникального пользователя через API")
    protected User createAndRegisterUniqueUser() {
        String cleanId = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        String uniqueEmail = "user_" + cleanId + "@yandex.ru";
        String uniquePassword = "burgerpass" + cleanId;
        String uniqueName = "Alex_" + cleanId;

        User testUser = new User(uniqueEmail, uniquePassword, uniqueName);
        ValidatableResponse response = userClient.register(testUser);

        if (response != null) {
            String rawToken = response.extract().path("accessToken");
            if (rawToken != null) {
                this.accessToken = rawToken.replace("Bearer ", "").trim();
            }
        }
        return testUser;
    }

    @Before
    public void setUp() {
        Configuration.browserSize = "1920x1080";

        // Читаем браузер из системной переменной. Если не задан — берем chrome
        String browserType = System.getProperty("browser", "chrome").toLowerCase();

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
            WebDriverManager.chromedriver().capabilities(options).setup();
            Configuration.browser = "chrome"; // Селенид работает с Яндексом через хром-драйвер
        } else {
            WebDriverManager.chromedriver().setup();
            Configuration.browser = "chrome";
        }

        Configuration.browserCapabilities = options;
    }

    @After
    public void tearDown() {
        try {
            if (accessToken != null) {
                userClient.delete(accessToken);
            }
        } catch (Exception e) {
            System.err.println("Не удалось удалить пользователя после теста: " + e.getMessage());
        } finally {
            accessToken = null;
            WebDriverRunner.closeWebDriver();
        }
    }
}
