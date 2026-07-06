package org.example.sorce.UI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public static final String URL = "https://stellarburgers.education-services.ru/";

    private final SelenideElement bunsTab = $(By.xpath("//span[text()='Булки']/parent::div"));
    private final SelenideElement saucesTab = $(By.xpath("//span[text()='Соусы']/parent::div"));
    private final SelenideElement fillingsTab = $(By.xpath("//span[text()='Начинки']/parent::div"));
    private final SelenideElement loginButton = $(By.xpath("//button[text()='Войти в аккаунт']"));
    private final SelenideElement personalAccountButton = $(By.xpath("//p[text()='Личный Кабинет']/parent::a"));
    private final SelenideElement constructorHeader = $(By.xpath("//h1[text()='Соберите бургер']"));

    public void clickBunsTab() {
        bunsTab.shouldBe(Condition.visible).click();
    }

    public void clickSaucesTab() {
        saucesTab.shouldBe(Condition.visible).click();
    }

    public void clickFillingsTab() {
        fillingsTab.shouldBe(Condition.visible).click();
    }

    public void clickLoginButton() {
        loginButton.shouldBe(Condition.visible).click();
    }

    public void clickPersonalAccountButton() {
        personalAccountButton.shouldBe(Condition.visible).click();
    }

    public boolean isTabActive(String tabName) {
        return $(By.xpath("//div[contains(@class, 'tab_tab_type_current')]/span[text()='" + tabName + "']"))
                .shouldBe(Condition.exist)
                .exists();
    }
}

