package org.example.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.example.sorce.Auto.models.User;
import org.example.sorce.UI.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

@RunWith(Parameterized.class)
public class StellarBurgersLoginUiTest extends BaseUiTest {

    public StellarBurgersLoginUiTest(String browserType) {
        super(browserType);
    }

    @Test
    public void testLoginFromMainPageButton() {
        User user = createAndRegisterUniqueUser();

        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage();
        loginPage.login(user.getEmail(), user.getPassword());

        $(By.xpath("//h2[text()='Вход']")).shouldNotBe(Condition.visible);
        mainPage.clickPersonalAccountButton();

        AccountPage accountPage = new AccountPage();
        accountPage.verifyProfileData(user.getName(), user.getEmail());
    }

    @Test
    public void testLoginFromPersonalAccountButton() {
        User user = createAndRegisterUniqueUser();

        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);
        mainPage.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage();
        loginPage.login(user.getEmail(), user.getPassword());

        $(By.xpath("//h2[text()='Вход']")).shouldNotBe(Condition.visible);
        mainPage.clickPersonalAccountButton();

        AccountPage accountPage = new AccountPage();
        accountPage.verifyProfileData(user.getName(), user.getEmail());
    }

    @Test
    public void testLoginFromRegisterPageFormButton() {
        User user = createAndRegisterUniqueUser();

        RegisterPage registerPage = Selenide.open(RegisterPage.URL, RegisterPage.class);
        registerPage.clickLoginLink();

        LoginPage loginPage = new LoginPage();
        loginPage.login(user.getEmail(), user.getPassword());

        $(By.xpath("//h2[text()='Вход']")).shouldNotBe(Condition.visible);
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.clickPersonalAccountButton();

        AccountPage accountPage = new AccountPage();
        accountPage.verifyProfileData(user.getName(), user.getEmail());
    }

    @Test
    public void testLoginFromForgotPasswordPageFormButton() {
        User user = createAndRegisterUniqueUser();

        ForgotPasswordPage forgotPage = Selenide.open(ForgotPasswordPage.URL, ForgotPasswordPage.class);
        forgotPage.clickLoginLink();

        LoginPage loginPage = new LoginPage();
        loginPage.login(user.getEmail(), user.getPassword());

        $(By.xpath("//h2[text()='Вход']")).shouldNotBe(Condition.visible);
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.clickPersonalAccountButton();

        AccountPage accountPage = new AccountPage();
        accountPage.verifyProfileData(user.getName(), user.getEmail());
    }
}