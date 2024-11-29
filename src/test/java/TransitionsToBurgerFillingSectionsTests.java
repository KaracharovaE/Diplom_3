import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pageobject.MainPage;

import static org.junit.Assert.assertTrue;

public class TransitionsToBurgerFillingSectionsTests extends BaseTest {

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
    public void successfulTransitionsToBunsSection() {
        objMainPage.scrollToSauce();
        objMainPage.scrollToBun();
        objMainPage.clickBunsButton();
        assertTrue(objMainPage.isBunActive());
    }

    @Test
    @DisplayName("Успешный переход к разделу Соусы")
    public void successfulTransitionsToSaucesSection() {
        objMainPage.scrollToSauce();
        assertTrue(objMainPage.isSauceActive());
    }

    @Test
    @DisplayName("Успешный переход к разделу Начинки")
    public void successfulTransitionsToFillingsSection() {
        objMainPage.scrollToFilling();
        assertTrue(objMainPage.isFillingActive());
    }
}
