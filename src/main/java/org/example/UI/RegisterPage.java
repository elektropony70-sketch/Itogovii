package org.example.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    public static final String URL = "https://stellarburgers.education-services.ru/register";

    // Локаторы полей ввода
    private final SelenideElement nameField = $(By.xpath("//label[text()='Имя']/following-sibling::input"));
    private final SelenideElement emailField = $(By.xpath("//label[text()='Email']/following-sibling::input"));
    private final SelenideElement passwordField = $(By.xpath("//input[@type='password']"));

    // Кнопка регистрации и ссылка перехода на логин
    private final SelenideElement registerButton = $(By.xpath("//button[text()='Зарегистрироваться']"));
    private final SelenideElement loginLink = $(By.xpath("//a[text()='Войти']"));

    // Элемент ошибки некорректного пароля
    private final SelenideElement passwordError = $(By.className("input__error"));

    // Метод заполнения формы и клика
    public void register(String name, String email, String password) {
        nameField.shouldBe(Condition.visible).setValue(name);
        emailField.setValue(email);
        passwordField.setValue(password);
        registerButton.click();
    }

    public void clickLoginLink() {
        loginLink.shouldBe(Condition.visible).click();
    }

    // Методы для проверки ошибки короткого пароля
    public boolean isPasswordErrorVisible() {
        return passwordError.shouldBe(Condition.visible).isDisplayed();
    }

    public String getPasswordErrorText() {
        return passwordError.getText();
    }
}