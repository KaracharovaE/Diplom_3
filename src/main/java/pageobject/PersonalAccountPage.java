package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By constructorButton = By.xpath("//p[contains(text(), 'Конструктор')]");
    private final By logoButton = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");
    private final By exitButton = By.xpath("//button[text()='Выход']");

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Увеличили время ожидания
    }

    @Step("Нажатие на кнопку 'Конструктор'")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Нажатие на логотип")
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }

    @Step("Нажатие на кнопку 'Выход'")
    public void clickExitButton() {
        driver.findElement(exitButton).click();
    }

    @Step("Ожидание появления текста кнопки 'Выход'")
    public String waitExitButton() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(exitButton))
                .getText();
    }

    @Step("Ожидание появления кнопки 'Выход'")
    public void waitVisibilityExitButton() {
        try {
            WebElement exitButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(exitButton));
            wait.until(ExpectedConditions.elementToBeClickable(exitButtonElement));

            exitButtonElement.getText();
        } catch (TimeoutException e) {
            throw new RuntimeException("Кнопка 'Выход' не стала видимой или кликабельной за отведенное время.", e);
        }
    }
}