import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
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

    private MainPage objMainPage;
    private LoginPage objLoginPage;
    private RegistrationPage objRegistrationPage;

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
        objRegistrationPage.clickLogoButton();
        objMainPage.clickLoginButton();
        objLoginPage.login(email, password);
        verifyOrderButtonIsVisible();
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
        objLoginPage.clickPersonalAccountButton();
        objLoginPage.login(email, password);
        verifyOrderButtonIsVisible();
    }

    @Test
    @DisplayName("Успешный вход через кнопку в форме регистрации")
    public void loginUsingLoginButtonOnRegistrationForm() {
        objRegistrationPage.clickLogoButton();
        objMainPage.clickLoginButton();
        objLoginPage.clickRegisterButton();
        objRegistrationPage.clickLoginButton();
        objLoginPage.login(email, password);
        verifyOrderButtonIsVisible();
    }

    @Test
    @DisplayName("Успешный вход через кнопку в форме восстановления пароля")
    public void loginUsingLoginButtonOnPasswordRecovery() {
        objRegistrationPage.clickLogoButton();
        objMainPage.clickLoginButton();
        objLoginPage.clickPasswordRecoveryButton();
        PasswordRecoveryPage objPasswordRecoveryPage = new PasswordRecoveryPage(driver);
        objPasswordRecoveryPage.clickLoginButton();
        objLoginPage.login(email, password);
        verifyOrderButtonIsVisible();
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
