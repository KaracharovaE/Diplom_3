package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {
    private WebDriver driver;

    private final By constructorButton = By.xpath("//p[contains(text(), 'Конструктор')]"); //херня
    private final By logoButton = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");
    private final By exitButton = By.xpath("//button[contains(text(), 'Выход')]");

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }

    public void clickExitButton() {
        driver.findElement(exitButton).click();
    }

    public String waitExitButton() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Выход')]")))
                .getText();
    }
}
