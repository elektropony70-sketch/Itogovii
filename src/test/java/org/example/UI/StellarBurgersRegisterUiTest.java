package org.example.UI;

import com.codeborne.selenide.Selenide;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class StellarBurgersRegisterUiTest extends BaseUiTest {

    // 2. Добавляем обязательный конструктор для связи с BaseUiTest
    public StellarBurgersRegisterUiTest(String browserType) {
        super(browserType);
    }

    @Before
    @Override
    public void setUp() {
        // Вызываем общую настройку окружения и браузеров из родительского класса
        super.setUp();
    }

    //Успешная регистрация
    @Test
    public void testSuccessfulRegistration() {

        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);
        mainPage.clickLoginButton();

        RegisterPage registerPage = Selenide.open(RegisterPage.URL, RegisterPage.class);
        String uniqueEmail = "ui_user_" + System.currentTimeMillis() + "@yandex.ru";

        registerPage.register("UiTester", uniqueEmail, "password123");

        // Инициализируем страницу логина через чистый конструктор Selenide
        LoginPage loginPage = new LoginPage();
        assertTrue("Не перешли на страницу логина после успешной регистрации", loginPage.isLoginHeaderVisible());
    }

    @Test
    public void testRegistrationWithShortPasswordThrowsError() {
        RegisterPage registerPage = Selenide.open(RegisterPage.URL, RegisterPage.class);
        String uniqueEmail = "ui_user_" + System.currentTimeMillis() + "@yandex.ru";

        registerPage.register("UiTester", uniqueEmail, "12345"); // Пароль меньше 6 символов

        assertTrue("Сообщение об ошибке некорректного пароля не отображается", registerPage.isPasswordErrorVisible());
        assertEquals("Некорректный пароль", registerPage.getPasswordErrorText());
    }
}
