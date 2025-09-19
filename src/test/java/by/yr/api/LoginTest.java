package by.yr.api;

import by.yr.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class LoginTest {

    @Test
    @DisplayName("Verify Login with empty email")
    public void loginMissingEmail() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest("", TestDataGenerator.generateRandomString(7), true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(422)
                .body("message", is("The given data was invalid."))
                .body("errors.email[0]", is("Поле электронная почта обязательно для заполнения."));
    }

    @Test
    @DisplayName("Verify Login with empty password")
    public void loginMissingPsw() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest(TestDataGenerator.generateRandomEmail(), "", true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(422)
                .body("message", is("The given data was invalid."))
                .body("errors.password[0]", is("Поле пароль обязательно для заполнения."));
    }

    @Test
    @DisplayName("Verify Login with empty email and password")
    public void loginMissingEmailAndPsw() {
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
    @DisplayName("Verify Login for not existing user")
    public void loginNotExistingUser() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest(TestDataGenerator.generateRandomEmail(), TestDataGenerator.generateRandomString(6), true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(401)
                .body("message", is("Проверьте корректность введенных данных"));
    }

    @Test
    @Disabled("Skipping successful login test for now")
    @DisplayName("Verify successful login")
    public void loginSuccessful() {
        LoginService loginService = new LoginService();
        loginService.sendLoginRequest(TestDataGenerator.getValidEmail(), TestDataGenerator.getValidPsw(), true);

        Response response = loginService.getResponse();
        response.then()
                .log().all()
                .statusCode(200)
                .body("token", not(emptyOrNullString()));
    }
}
