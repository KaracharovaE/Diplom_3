import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static driver.WebDriverCreator.createWebDriver;

public class BaseTest {
    protected WebDriver driver;
    protected static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    @Before
    public void setUp() {
        driver = createWebDriver();
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

