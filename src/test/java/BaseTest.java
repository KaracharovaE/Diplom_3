import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static driver.WebDriverCreator.createWebDriver;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = createWebDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

