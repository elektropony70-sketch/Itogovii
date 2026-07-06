package org.example.UI;


import com.codeborne.selenide.Selenide;

import org.example.UI.MainPage;
import org.junit.Test;


import static org.junit.Assert.assertTrue;


public class StellarBurgersConstructorUiTest extends org.example.UI.BaseUiTest {

    public StellarBurgersConstructorUiTest(String browserType) {
        super(browserType);
    }


    @Test
    public void testSwitchToSaucesTab() {
        Selenide.open(MainPage.URL);
        MainPage mainPage = new MainPage();

        mainPage.clickSaucesTab();
        assertTrue("Вкладка 'Соусы' не стала активной", mainPage.isSaucesTabActive());
    }

    @Test
    public void testSwitchToFillingsTab() {
        Selenide.open(MainPage.URL);
        MainPage mainPage = new MainPage();

        mainPage.clickFillingsTab();
        assertTrue("Вкладка 'Начинки' не стала активной", mainPage.isFillingsTabActive());
    }

    @Test
    public void testSwitchToBunsTab() {
        Selenide.open(MainPage.URL);
        MainPage mainPage = new MainPage();

        // Сначала переключаемся на соусы, чтобы сбросить дефолтный фокус с булок
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();
        assertTrue("Вкладка 'Булки' не стала активной", mainPage.isBunsTabActive());
    }
}