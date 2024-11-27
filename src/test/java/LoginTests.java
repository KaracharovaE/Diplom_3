import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.*;
import utils.User;
import utils.UserClient;
import utils.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class LoginTests extends BaseTest {
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
        userClient = new UserClient();
        objLoginPage = new LoginPage(driver);
        objMainPage = new MainPage(driver);
        objRegistrationPage = new RegistrationPage(driver);
        objPersonalAccountPage = new PersonalAccountPage(driver);

        RestAssured.baseURI = BASE_URL;
        User user = UserGenerator.randomUser();
        Response response = userClient.create(user);
        assertEquals(SC_OK, response.statusCode());
        email = user.getEmail();
        password = user.getPassword();
        userToken = getUserToken(email, password);
    }

    @Test
    @DisplayName("Успешный вход по кнопке «Войти в аккаунт» на главной")
    public void loginUsingLoginButtonOnMain() {
        navigateToMainPage();
        clickLoginButton();
        performLogin(email, password);
        verifyOrderButtonIsVisible();
    }

    @Step("Переход на главную страницу")
    private void navigateToMainPage() {
        objRegistrationPage.clickLogoButton();
    }

    @Step("Клик по кнопке «Войти в аккаунт»")
    private void clickLoginButton() {
        objMainPage.clickLoginButton();
    }

    @Step("Выполнение входа с email: {email} и паролем")
    private void performLogin(String email, String password) {
        objLoginPage.login(email, password);
    }

    @Step("Проверка, что кнопка «Оформить заказ» видима")
    private void verifyOrderButtonIsVisible() {
        String expectedButton = "Оформить заказ";
        String actualButton = objMainPage.waitMakeOrderButton();
        assertEquals(expectedButton, actualButton);
    }

    @Test
    @DisplayName("Успешный вход через кнопку «Личный кабинет»")
    public void loginUsingPersonalAccountButton() {
        clickPersonalAccountButton();
        performLogin(email, password);
        verifyOrderButtonIsVisible();
    }

    @Step("Клик по кнопке «Личный кабинет»")
    private void clickPersonalAccountButton() {
        objLoginPage.clickPersonalAccountButton();
    }

    @Test
    @DisplayName("Успешный вход через кнопку в форме регистрации")
    public void loginUsingLoginButtonOnRegistrationForm() {
        navigateToMainPage();
        objMainPage.clickLoginButton();
        objLoginPage.clickRegisterButton();
        objRegistrationPage.clickLoginButton();
        performLogin(email, password);
        verifyOrderButtonIsVisible();
    }

    @Test
    @DisplayName("Успешный вход через кнопку в форме восстановления пароля")
    public void loginUsingLoginButtonOnPasswordRecovery() {
        navigateToMainPage();
        clickLoginButton();
        objLoginPage.clickPasswordRecoveryButton();
        PasswordRecoveryPage objPasswordRecoveryPage = new PasswordRecoveryPage(driver);
        objPasswordRecoveryPage.clickLoginButton();
        performLogin(email, password);
        verifyOrderButtonIsVisible();
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
