package org.example.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public static final String URL = "https://stellarburgers.education-services.ru/login";

    private final SelenideElement loginHeader = $(By.xpath("//h2[text()='Вход']"));
    private final SelenideElement emailField = $(By.xpath("//label[text()='Email']/following-sibling::input"));
    private final SelenideElement passwordField = $(By.xpath("//input[@type='password']"));
    private final SelenideElement signInButton = $(By.xpath("//button[text()='Войти']"));

    public void login(String email, String password) {
        emailField.shouldBe(Condition.visible).setValue(email);
        passwordField.setValue(password);
        signInButton.click();
    }

    public boolean isLoginHeaderVisible() {
        // Умное ожидание Selenide: ждем появления заголовка до 4 секунд
        return loginHeader.shouldBe(Condition.visible).isDisplayed();
    }
}