package by.yr.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class LoginTest {

    @Test
    @DisplayName("Verify Login with empty email")
    public void test1() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest("", "qwerty", true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(422)
                .body("message", is("The given data was invalid."))
                .body("errors.email[0]", is("Поле электронная почта обязательно для заполнения."));
    }

    @Test
    @DisplayName("Verify Login with empty password")
    public void test2() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest("user@gmail.com", "", true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(422)
                .body("message", is("The given data was invalid."))
                .body("errors.password[0]", is("Поле пароль обязательно для заполнения."));
    }

    @Test
    @DisplayName("Verify Login with empty email and password")
    public void test3() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest("", "", true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(422)
                .body("message", is("The given data was invalid."))
                .body("errors.email[0]", is("Поле электронная почта обязательно для заполнения."))
                .body("errors.password[0]", is("Поле пароль обязательно для заполнения."));
    }

    @Test
    @DisplayName("Verify Login with wrong email/password")
    public void test4() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest("wrong@gmail.com", "wrongpass", true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(401)
                .body("message", is("Проверьте корректность введенных данных"));
    }

    @Test
    @DisplayName("Verify successful")
    public void test5() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest("webnwmsbfxafriczku@nespj.com", "mypsw12345", true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(200)
                .body("token", not(emptyOrNullString()));
    }
}

