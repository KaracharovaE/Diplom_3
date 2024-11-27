import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import pageobject.MainPage;

import static org.junit.Assert.assertEquals;


public class TransitionsToBurgerFillingSectionsTests extends BaseTest {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private MainPage objMainPage;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        objMainPage = new MainPage(driver);
        driver.get(BASE_URL);
    }

    @Test
    @DisplayName("Успешный переход к разделу Булки")
    public void clickOnBunsButton() {
        objMainPage.clickSaucesButton();
        objMainPage.clickBunsButton();
        String expected = "Флюоресцентная булка R2-D3";
        String actual = objMainPage.getBun();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Успешный переход к разделу Соусы")
    public void clickOnSaucesButton() {
        objMainPage.clickSaucesButton();
        String expected = "Соус с шипами Антарианского плоскоходца";
        String actual = objMainPage.getSauce();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Успешный переход к разделу Начинки")
    public void clickOnFillingsButton() {
        objMainPage.clickFillingsButton();
        String expected = "Биокотлета из марсианской Магнолии";
        String actual = objMainPage.getFilling();
        assertEquals(expected, actual);
    }
}
