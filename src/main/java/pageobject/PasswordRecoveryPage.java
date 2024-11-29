package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {
    private WebDriver driver;

    private final By loginButton = By.xpath("//div/div/p/a[@href='/login']");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
