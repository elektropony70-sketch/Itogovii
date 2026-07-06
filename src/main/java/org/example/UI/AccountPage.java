package org.example.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class AccountPage {

    public static final String URL = "https://stellarburgers.education-services.ru/account/profile";

    // Локаторы полей в Личном кабинете (инпуты с данными профиля)
    private final SelenideElement nameInput = $(By.xpath("//label[text()='Имя']/following-sibling::input"));
    private final SelenideElement loginInput = $(By.xpath("//label[text()='Логин']/following-sibling::input"));

// Метод, возвращающий значение из поля «Имя»
    public String getNameValue() {
    return nameInput.shouldBe(Condition.visible).getValue();
    }

// Метод, возвращающий значение из поля «Логин» (email)
    public String getLoginValue() {
    return loginInput.shouldBe(Condition.visible).getValue();
    }
}