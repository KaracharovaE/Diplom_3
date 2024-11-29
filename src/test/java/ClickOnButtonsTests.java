import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.PersonalAccountPage;
import pageobject.RegistrationPage;
import utils.User;
import utils.UserClient;
import utils.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class ClickOnButtonsTests extends BaseTest {

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
        objRegistrationPage.clickLogoButton();
        objMainPage.clickLoginButton();
        objLoginPage.login(email, password);
        objMainPage.clickPersonalAccountButton();

        objPersonalAccountPage.waitExitButton();
        String expected = "Выход";
        String actual = objPersonalAccountPage.waitExitButton();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор ")
    public void clickOnConstructorButton() {
        objRegistrationPage.clickLogoButton();
        objMainPage.clickLoginButton();
        objLoginPage.login(email, password);
        objMainPage.clickPersonalAccountButton();

        objPersonalAccountPage.clickConstructorButton();
        String expected = "Булки";
        String actual = objMainPage.getBunsButton();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Переход из личного кабинета на логотип Stellar Burgers")
    public void clickOnLogoButton() {
        objRegistrationPage.clickLogoButton();
        objMainPage.clickLoginButton();
        objLoginPage.login(email, password);
        objMainPage.clickPersonalAccountButton();

        objPersonalAccountPage.clickLogoButton();
        String expected = "Булки";
        String actual = objMainPage.getBunsButton();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void clickOnExitButton() {
        objRegistrationPage.clickLogoButton();
        objMainPage.clickLoginButton();
        objLoginPage.login(email, password);
        objMainPage.clickPersonalAccountButton();

        objPersonalAccountPage.waitVisibilityExitButton();
        objPersonalAccountPage.clickExitButton();
        String expected = "Войти";
        String actual = objLoginPage.getLoginButton();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        if (userToken != null) {
            userClient.delete(userToken);
        }
        super.tearDown();
    }

    private String getUserToken(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Response response = userClient.login(user);
        return response.jsonPath().getString("accessToken");
    }
}