package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static utils.UserCreds.credsFromUser;
import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("Регистрация пользователя")
    public Response create(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("api/auth/register");
    }

    @Step("Авторизация пользователя")
    public Response login(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(credsFromUser(user))
                .when()
                .post("api/auth/login");
    }

    @Step("Удаление пользователя")
    public Response delete(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .delete("api/auth/user/");
    }
}