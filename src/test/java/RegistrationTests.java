import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegistrationPage;
import io.qameta.allure.Step;
import utils.UserData;
import utils.UserClient;
import utils.User;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class RegistrationTests extends BaseTest {

    private MainPage objMainPage;
    private LoginPage objLoginPage;
    private RegistrationPage objRegistrationPage;
    private WebDriverWait wait;
    private UserClient userClient;
    private String userToken;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        driver.get(BASE_URL);
        objMainPage = new MainPage(driver);
        objLoginPage = new LoginPage(driver);
        objRegistrationPage = new RegistrationPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void successfulRegistration() {
        String name = UserData.getRandomName();
        String email = UserData.getRandomEmail();
        String password = UserData.getRandomPassword();
        registerUser(name, email, password);

        String expected = "Войти";
        String actual = objLoginPage.getLoginButton();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ошибка для некорректного пароля")
    public void errorForIncorrectPassword() {
        String name = UserData.getRandomName();
        String email = UserData.getRandomEmail();
        registerUser(name, email, "p10p8");

        String expectedErrorMessage = "Некорректный пароль";
        String actualErrorMessage = objRegistrationPage.waitPasswordError();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Step("Регистрация пользователя с данными: имя, email")
    private void registerUser(String name, String email, String password) {
        navigateToRegistrationPage();
        objRegistrationPage.register(name, email, password);
        userToken = getUserToken(email, password);
    }

    @Step("Переход на страницу регистрации")
    private void navigateToRegistrationPage() {
        objMainPage.clickLoginButton();
        objLoginPage.clickRegisterButton();
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