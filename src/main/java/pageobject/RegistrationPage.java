package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private WebDriver driver;

    private final By nameField = By.xpath("//fieldset[1]/div/div/input[@name= 'name']");
    private final By emailField = By.xpath("//fieldset[2]/div/div/input[@name= 'name']");
    private final By passwordField = By.xpath("//input[@name= 'Пароль']");
    private final By registrationButton = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");
    private final By loginButton = By.xpath("//a[@href='/login']");
    private final By logoButton = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Регистрация")
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegistrationButton();
    }

    @Step("Ожидание появления текста ошибки 'Некорректный пароль'")
    public String waitPasswordError() {
     return new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//fieldset[3]/div/p[contains(text(), 'Некорректный пароль')]")))
            .getText();
    }

    @Step("Нажатие на кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Нажатие на логотип")
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }
}