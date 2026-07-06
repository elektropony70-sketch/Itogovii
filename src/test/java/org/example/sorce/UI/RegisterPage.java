package org.example.sorce.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

    public static final String URL = "https://stellarburgers.education-services.ru/register";

    private final SelenideElement nameField = $(By.xpath("//label[text()='Имя']/following-sibling::input"));
    private final SelenideElement emailField = $(By.xpath("//label[text()='Email']/following-sibling::input"));
    private final SelenideElement passwordField = $(By.xpath("//input[@type='password']"));
    private final SelenideElement registerButton = $(By.xpath("//button[text()='Зарегистрироваться']"));
    private final SelenideElement loginLink = $(By.xpath("//a[text()='Войти']"));
    private final SelenideElement passwordError = $(By.className("input__error"));

    public void register(String name, String email, String password) {
        nameField.shouldBe(Condition.visible).setValue(name);
        emailField.setValue(email);
        passwordField.setValue(password);
        registerButton.click();
    }

    public void clickLoginLink() {
        loginLink.shouldBe(Condition.visible).click();
    }

    public boolean isPasswordErrorVisible() {
        return passwordError.shouldBe(Condition.visible).isDisplayed();
    }

    public String getPasswordErrorText() {
        return passwordError.getText();
    }
}