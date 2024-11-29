package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

private final By personalAccountButton = By.xpath("//a[contains(@class, 'AppHeader_header__link__3D_hX') and .//p[contains(text(), 'Личный Кабинет')]]");
    private final By loginButton = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");

    private final By bunsButton = By.xpath("//span[contains(text(), 'Булки')]");
    private final By fluorescentBun = By.xpath("//p[contains(text(), 'Флюоресцентная булка R2-D3')]");

    private final By bunSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and contains(@class, 'pt-4') and contains(@class, 'pr-10') and contains(@class, 'pb-4') and contains(@class, 'pl-10') and contains(@class, 'noselect')][1]");
    private final By sauceSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and contains(@class, 'pt-4') and contains(@class, 'pr-10') and contains(@class, 'pb-4') and contains(@class, 'pl-10') and contains(@class, 'noselect')][2]");
    private final By fillingSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG') and contains(@class, 'pt-4') and contains(@class, 'pr-10') and contains(@class, 'pb-4') and contains(@class, 'pl-10') and contains(@class, 'noselect')][3]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на кнопку 'Личный Кабинет'")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Нажатие на кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Получение текста кнопки 'Флюоресцентная булка R2-D3'")
    public String getFluorescentBun() {
        By fluorescentBunLocator = By.xpath("//p[contains(text(), 'Флюоресцентная булка R2-D3')]");
    return new WebDriverWait(driver, Duration.ofSeconds(20))
            .until(ExpectedConditions.visibilityOfElementLocated(fluorescentBunLocator)).getText();
    }

    @Step("Получение текста кнопки 'Булки'")
    public String getBunsButton() {
        By bunLocator = By.xpath("//span[contains(text(), 'Булки')]");
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(bunLocator)).getText();
    }

    @Step("Нажатие на кнопку 'Булки'")
    public void clickBunsButton() {
        driver.findElement(bunsButton).click();
    }

    @Step("Прокрутка к 'Булки'")
    public void scrollToBun() {
        WebElement element = driver.findElement(fluorescentBun);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isBunActive() {
            WebElement element = driver.findElement(bunSection);
            String classAttribute = element.getAttribute("class");
            return classAttribute.contains("tab_tab_type_current__2BEPc");
    }

    @Step("Прокрутка к 'Соусы'")
    public void scrollToSauce() {
        WebElement element = driver.findElement(By.xpath("//p[contains(text(), 'Соус Spicy-X')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isSauceActive() {
        WebElement element = driver.findElement(sauceSection);
        String classAttribute = element.getAttribute("class");
        return classAttribute.contains("tab_tab_type_current__2BEPc");
    }

    @Step("Прокрутка к 'Начинки'")
    public void scrollToFilling() {
        WebElement element = driver.findElement(By.xpath("//p[contains(text(), 'Мясо бессмертных моллюсков Protostomia')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(element));
    }

    public boolean isFillingActive() {
        WebElement element = driver.findElement(fillingSection);
        String classAttribute = element.getAttribute("class");
        return classAttribute.contains("tab_tab_type_current__2BEPc");
    }

    @Step("Ожидание появления текста кнопки 'Оформить заказ'")
    public String waitMakeOrderButton() {
        return new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Оформить заказ')]")))
                .getText();
    }
}