package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    private final By personalAccountButton = By.xpath("//p[contains(text(), 'Личный Кабинет')]");
    private final By loginButton = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");

    private final By bunsButton = By.xpath("//span[contains(text(), 'Булки')]");
    private final By saucesButton = By.xpath("//span[contains(text(), 'Соусы')]");
    private final By fillingsButton = By.xpath("//span[contains(text(), 'Начинки')]");

    private final By bunButton = By.xpath("//p[contains(text(), 'Флюоресцентная булка R2-D3')]");
    private final By sauceButton = By.xpath("//p[contains(text(), 'Соус с шипами Антарианского плоскоходца')]");
    private final By fillingButton = By.xpath("//p[contains(text(), 'Биокотлета из марсианской Магнолии')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickBunsButton() {
        driver.findElement(bunsButton).click();
    }

    public void clickSaucesButton() {
        driver.findElement(saucesButton).click();
    }

    public void clickFillingsButton() {
        driver.findElement(fillingsButton).click();
    }

    public String getBun() {
        return driver.findElement(bunButton).getText(); // херь
    }

    public String getSauce() {
        return driver.findElement(sauceButton).getText(); // херь
    }

    public String getFilling() {
        return driver.findElement(fillingButton).getText(); // херь
    }

    public String waitMakeOrderButton() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Оформить заказ')]")))
                .getText();
    }
}