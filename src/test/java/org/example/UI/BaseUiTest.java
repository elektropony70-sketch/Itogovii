package org.example.UI;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.Arrays;
import java.util.Collection;

// Объявляем параметризацию на уровне базового класса, чтобы её наследовал каждый UI-тест
@RunWith(Parameterized.class)
public class BaseUiTest {

    protected final String browserType;


    // Конструктор принимает текущий браузер из параметров JUnit
    public BaseUiTest(String browserType) {
        this.browserType = browserType;
    }

    // Параметры для поочередного запуска тестов
    @Parameterized.Parameters(name = "Браузер для теста: {0}")
    public static Collection<Object[]> getBrowsers() {
        return Arrays.asList(new Object[][] {
                {"chrome"}, // Первый круг
                {"yandex"}  // Второй круг
        });
    }

    @Before
    public void setUp() {
        Configuration.browser = "chrome"; // Оба браузера работают на Chromium-движке
        Configuration.browserSize = "1920x1080";

        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        if ("yandex".equals(browserType)) {

            String userHome = System.getProperty("user.home");
            String yandexBinaryPath = userHome + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";

            java.io.File file = new java.io.File(yandexBinaryPath);
            if (!file.exists()) {
                yandexBinaryPath = "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe";
            }
            options.setBinary(yandexBinaryPath); // Указываем открывать Яндекс

            WebDriverManager.chromiumdriver().driverVersion("146.0.7680.0").setup();

            System.out.println("--- ЗАПУСК ПРОГОНА В ЯНДЕКС.БРАУЗЕРЕ ---");
        } else {
            // АВТОМАТИКА ДЛЯ CHROME: скачивает драйвер строго под ваш текущий Chrome 149
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

            System.out.println("--- ЗАПУСК ПРОГОНА В GOOGLE CHROME ---");
        }

        Configuration.browserCapabilities = options;
    }

    @After
    public void tearDown() {
        try {
            // ВРЕМЕННО: задерживаем окно на 2 секунды, чтобы увидеть результат глазами
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebDriverRunner.closeWebDriver();
    }
}