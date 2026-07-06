package org.example.UI;

import com.codeborne.selenide.Selenide;
import org.example.sorce.UI.MainPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class StellarBurgersConstructorUiTest extends BaseUiTest {

    public StellarBurgersConstructorUiTest(String browserType) {
        super(browserType);
    }

    @Test
    public void testSwitchToSaucesTab() {
        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);

        mainPage.clickSaucesTab();
        assertTrue("Вкладка 'Соусы' не стала активной", mainPage.isTabActive("Соусы"));
    }

    @Test
    public void testSwitchToFillingsTab() {
        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);

        mainPage.clickFillingsTab();
        assertTrue("Вкладка 'Начинки' не стала активной", mainPage.isTabActive("Начинки"));
    }

    @Test
    public void testSwitchToBunsTab() {
        MainPage mainPage = Selenide.open(MainPage.URL, MainPage.class);

        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();
        assertTrue("Вкладка 'Булки' не стала активной", mainPage.isTabActive("Булки"));
    }
}