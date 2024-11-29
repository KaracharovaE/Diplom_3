package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    private final By emailField = By.xpath("//fieldset[1]/div/div/input[@name= 'name']");
    private final By passwordField = By.xpath("//fieldset[2]/div/div/input[@name= 'Пароль']");
    private final By loginButton = By.xpath("//button[contains(text(), 'Войти')]");
    private final By registerButton = By.xpath("//a[@href='/register']");
    private final By passwordRecoveryButton = By.xpath("//a[@href='/forgot-password']");
    private final By personalAccountButton = By.xpath("//p[contains(text(), 'Личный Кабинет')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Авторизация")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Нажатие на кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Нажатие на кнопку 'Восстановить пароль'")
    public void clickPasswordRecoveryButton() {
        driver.findElement(passwordRecoveryButton).click();
    }

    @Step("Ожидание появления текста кнопки 'Войти'")
    public String getLoginButton() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(loginButton))
                .getText();
    }

    @Step("Нажатие на кнопку 'Личный Кабинет'")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }
}
