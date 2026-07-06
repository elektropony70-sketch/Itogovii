package org.example.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {


    public static final String URL = "https://stellarburgers.education-services.ru/";


    // 1. ЛОКАТОРЫ ДЛЯ ТЕСТОВ КОНСТРУКТОРА И ЛОГИНА
    private final SelenideElement bunsTab = $(By.xpath("//span[text()='Булки']/parent::div"));
    private final SelenideElement saucesTab = $(By.xpath("//span[text()='Соусы']/parent::div"));
    private final SelenideElement fillingsTab = $(By.xpath("//span[text()='Начинки']/parent::div"));

    // Кнопка «Войти в аккаунт» на главной странице
    private final SelenideElement loginButton = $(By.xpath("//button[text()='Войти в аккаунт']"));
    // Кнопка «Личный кабинет» в шапке сайта
    private final SelenideElement personalAccountButton = $(By.xpath("//p[text()='Личный Кабинет']/parent::a"));
    // Заголовок «Соберите бургер» (подтверждает успешный вход и возврат на главную)
    private final SelenideElement constructorHeader = $(By.xpath("//h1[text()='Соберите бургер']"));


    // 2. МЕТОДЫ ДЛЯ КЛИКОВ И ДЕЙСТВИЙ
    public void clickBunsTab() { bunsTab.click(); }
    public void clickSaucesTab() { saucesTab.click(); }
    public void clickFillingsTab() { fillingsTab.click(); }

    // Новый метод: клик по кнопке «Войти в аккаунт» на главной
    public void clickLoginButton() {
        loginButton.shouldBe(Condition.visible).click();
    }

    // Новый метод: клик по кнопке «Личный кабинет»
    public void clickPersonalAccountButton() {
        personalAccountButton.shouldBe(Condition.visible).click();
    }


    // 3. МЕТОДЫ ПРОВЕРОК (ПРОДОЛЖЕНИЕ)
    public boolean isBunsTabActive() {
        String activeClass = bunsTab.getAttribute("class");
        return activeClass != null && activeClass.contains("tab_tab_type_current");
    }

    public boolean isSaucesTabActive() {
        String activeClass = saucesTab.getAttribute("class");
        return activeClass != null && activeClass.contains("tab_tab_type_current");
    }

    public boolean isFillingsTabActive() {
        String activeClass = fillingsTab.getAttribute("class");
        return activeClass != null && activeClass.contains("tab_tab_type_current");
    }

    // Новый метод: проверяет, виден ли заголовок «Соберите бургер» (успешный логин)
    public boolean isConstructorHeaderVisible() {
        return constructorHeader.isDisplayed();
    }
}

