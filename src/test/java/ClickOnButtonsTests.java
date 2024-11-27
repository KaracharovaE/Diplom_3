import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.PersonalAccountPage;
import pageobject.RegistrationPage;
import utils.User;
import utils.UserClient;
import utils.UserGenerator;

import java.time.Duration;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class ClickOnButtonsTests extends BaseTest {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private MainPage objMainPage;
    private LoginPage objLoginPage;
    private RegistrationPage objRegistrationPage;
    private PersonalAccountPage objPersonalAccountPage;
    private UserClient userClient;
    private String email;
    private String password;
    private String userToken;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        driver.get(BASE_URL);
        objMainPage = new MainPage(driver);
        objPersonalAccountPage = new PersonalAccountPage(driver);
        objLoginPage = new LoginPage(driver);
        objRegistrationPage = new RegistrationPage(driver);
        userClient = new UserClient();

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        User user = UserGenerator.randomUser();
        Response response = userClient.create(user);
        assertEquals(SC_OK, response.statusCode());
        email = user.getEmail();
        password = user.getPassword();
        userToken = getUserToken(email, password);
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void clickOnPersonalAccount() {
        navigateToMainPage();
        loginUser();
        accessPersonalAccount();

        String expected = "Выход";
        String actual = objPersonalAccountPage.waitExitButton();
        assertEquals(expected, actual);
    }

    @Step("Переход на главную страницу")
    private void navigateToMainPage() {
        objRegistrationPage.clickLogoButton();
    }

    @Step("Авторизация пользователя")
    private void loginUser() {
        objMainPage.clickLoginButton();
        objLoginPage.login(email, password);
    }

    @Step("Доступ к личному кабинету")
    private void accessPersonalAccount() {
        objMainPage.clickPersonalAccountButton();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор ")
    public void clickOnConstructorButton() {
        navigateToMainPage();
        loginUser();
        accessPersonalAccount();

        objPersonalAccountPage.clickConstructorButton();
        String expected = "Флюоресцентная булка R2-D3";
        String actual = objMainPage.getBun();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Переход из личного кабинета на логотип Stellar Burgers")
    public void clickOnLogoButton() {
        navigateToMainPage();
        loginUser();
        accessPersonalAccount();

        objPersonalAccountPage.clickLogoButton();
        String expected = "Флюоресцентная булка R2-D3";
        String actual = objMainPage.getBun();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void clickOnExitButton() {
        navigateToMainPage();
        loginUser();
        accessPersonalAccount();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/account/profile")
        );
        objPersonalAccountPage.clickExitButton();
        String expected = "Войти";
        String actual = objLoginPage.getLoginButton();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        if (userToken != null) {
            deleteUser(userToken);
        }
        super.tearDown();
    }

    @Step("Удаление пользователя с токеном: {token}")
    private void deleteUser(String token) {
        userClient.delete(token);
    }

    private String getUserToken(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Response response = userClient.login(user);
        return response.jsonPath().getString("accessToken");
    }
}