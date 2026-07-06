package org.example.UI;

import com.codeborne.selenide.Selenide;
import org.example.Auto.client.UserClient;
import org.example.Auto.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class StellarBurgersLoginUiTest extends BaseUiTest {

    private final UserClient userClient = new UserClient();
    private String accessToken;
    private String email;
    private final String password = "password123";

    // Конструктор для передачи типа браузера в родительский класс
    public StellarBurgersLoginUiTest(String browserType) {
        super(browserType);
    }

    @Before
    @Override
    public void setUp() {
        // Сначала вызываем setUp из BaseUiTest, чтобы настроить нужный браузер (Chrome или Яндекс)
        super.setUp();

        // Затем создаем уникального пользователя через API для текущего прогона
        email = "ui_login_" + System.currentTimeMillis() + "@yandex.ru";
        User user = new User(email, password, "LoginUiTester");
        accessToken = userClient.register(user).extract().path("accessToken");
    }

    @After
    @Override
    public void tearDown() {
        // Сначала удаляем пользователя через API, чтобы не засорять базу данных
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
        // Затем вызываем закрытие браузера из родительского класса
        super.tearDown();
    }

    @Test
    public void testLoginFromMainPageButton() {
        // 1. Открываем главную страницу
        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);
        mainPage.clickLoginButton();

        // 2. Вводим данные на странице логина
        LoginPage loginPage = new LoginPage();
        loginPage.login(email, password);

        // 3. НОВЫЙ ШАГ: После успешного входа кликаем на кнопку «Личный кабинет» в шапке
        mainPage.clickPersonalAccountButton();

        // 4. Инициализируем страницу личного кабинета
        AccountPage accountPage = new AccountPage();

        // 5. ПРОВЕРКА ИНФОРМАЦИИ: Сверяем, что в профиле отображаются именно наши Имя и Email
        assertEquals("Имя пользователя в Личном кабинете не совпадает",
                "LoginUiTester", accountPage.getNameValue());

        assertEquals("Email пользователя в Личном кабинете не совпадает",
                email, accountPage.getLoginValue());
    }

    @Test
    public void testLoginFromPersonalAccountButton() {
        // 1. Открываем главную страницу
        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);

        // 2. Кликаем по кнопке «Личный кабинет», чтобы перейти на страницу логина
        mainPage.clickPersonalAccountButton();

        // 3. Авторизуемся в системе
        LoginPage loginPage = new LoginPage();
        loginPage.login(email, password);

        // 4. После логина снова кликаем «Личный кабинет», чтобы попасть в профиль
        mainPage.clickPersonalAccountButton();

        // 5. Инициализируем страницу профиля
        AccountPage accountPage = new AccountPage();

        // 6. СВЕРКА ДАННЫХ: Проверяем, что залогинился именно наш пользователь
        assertEquals("Имя пользователя в Личном кабинете не совпадает",
                "LoginUiTester", accountPage.getNameValue());

        assertEquals("Email пользователя в Личном кабинете не совпадает",
                email, accountPage.getLoginValue());
    }

    @Test
    public void testLoginFromRegisterPageFormButton() {
        // 1. Открываем страницу регистрации
        RegisterPage registerPage = Selenide.open(RegisterPage.URL, RegisterPage.class);

        // 2. Кликаем по ссылке «Войти» на форме регистрации
        registerPage.clickLoginLink();

        // 3. Авторизуемся на открывшейся странице логина
        LoginPage loginPage = new LoginPage();
        loginPage.login(email, password);

        // 4. Возвращаемся к главной странице, чтобы кликнуть на «Личный кабинет»
        MainPage mainPage = new MainPage();
        mainPage.clickPersonalAccountButton();

        // 5. Инициализируем страницу личного кабинета
        AccountPage accountPage = new AccountPage();

        // 6. СВЕРКА ДАННЫХ: Проверяем профиль пользователя
        assertEquals("Имя пользователя в Личном кабинете не совпадает",
                "LoginUiTester", accountPage.getNameValue());

        assertEquals("Email пользователя в Личном кабинете не совпадает",
                email, accountPage.getLoginValue());
    }

    @Test
    public void testLoginFromForgotPasswordPageFormButton() {
        // 1. Открываем страницу восстановления пароля
        ForgotPasswordPage forgotPage = Selenide.open(ForgotPasswordPage.URL, ForgotPasswordPage.class);

        // 2. Кликаем по ссылке «Войти» под формой восстановления
        forgotPage.clickLoginLink();

        // 3. Авторизуемся на открывшейся странице логина
        LoginPage loginPage = new LoginPage();
        loginPage.login(email, password);

        // 4. Используем объект главной страницы, чтобы перейти в Личный кабинет
        MainPage mainPage = new MainPage();
        mainPage.clickPersonalAccountButton();

        // 5. Инициализируем страницу личного кабинета
        AccountPage accountPage = new AccountPage();

        // 6. СВЕРКА ДАННЫХ: Проверяем, что в профиле отображаются нужные Имя и Email
        assertEquals("Имя пользователя в Личном кабинете не совпадает",
                "LoginUiTester", accountPage.getNameValue());

        assertEquals("Email пользователя в Личном кабинете не совпадает",
                email, accountPage.getLoginValue());
    }
}
