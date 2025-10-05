package by.yr.api;

import by.yr.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    public static final String INVALID_DATA_MSG="The given data was invalid.";
    public static final String EMAIL_IS_REQUIRES_MSG="Поле электронная почта обязательно для заполнения.";
    public static final String PSW_IS_REQUIRED_MSG="Поле пароль обязательно для заполнения.";
    public static final String CHECK_DATA_MSG="Проверьте корректность введенных данных";
    public static final String SHORT_PSW_MSG="Количество символов в поле пароль должно быть не меньше 6.";


    @Test
    @DisplayName("Verify Login with empty email")
    public void loginMissingEmail() {
        LoginService loginService = new LoginService();
        Response response = loginService.sendLoginRequest("", TestDataGenerator.generateRandomString(7), true);

        assertAll("Login with missing email validations",
                () -> assertEquals(422,loginService.getStatusCode(),"Status code mismatch"),
                () -> assertEquals(INVALID_DATA_MSG, loginService.getMessage(), "Message mismatch"),
                () -> assertEquals(EMAIL_IS_REQUIRES_MSG, loginService.getEmailError(), "Email error mismatch")
        );
    }

    @Test
    @DisplayName("Verify Login with empty password")
    public void loginMissingPsw() {
        LoginService loginService = new LoginService();
        Response response = loginService.sendLoginRequest(TestDataGenerator.generateRandomEmail(), "", true);


        assertAll("Login with missing psw validation",
                () -> assertEquals(422,loginService.getStatusCode(),"Status code mismatch"),
                () ->assertEquals(INVALID_DATA_MSG,loginService.getMessage()),
                () -> assertEquals(PSW_IS_REQUIRED_MSG, loginService.getPasswordError())
        );
    }

    @Test
    @DisplayName("Verify Login with empty email and password")
    public void loginMissingEmailAndPsw() {
        LoginService loginService = new LoginService();
        Response response = loginService.sendLoginRequest("", "", true);

        assertAll("Login with missing email and psw validation",
                () -> assertEquals(422,loginService.getStatusCode(),"Status code mismatch"),
                () ->assertEquals(INVALID_DATA_MSG,loginService.getMessage()),
                () ->assertEquals(EMAIL_IS_REQUIRES_MSG,loginService.getEmailError()),
                () -> assertEquals(PSW_IS_REQUIRED_MSG, loginService.getPasswordError())
        );
    }

    @Test
    @DisplayName("Verify Login for not existing user")
    public void loginNotExistingUser() {
        LoginService loginService = new LoginService();
        Response response = loginService.sendLoginRequest(TestDataGenerator.generateRandomEmail(), TestDataGenerator.generateRandomString(6), true);

        assertAll("Login with not existing user",
                () -> assertEquals(401,loginService.getStatusCode(),"Status code mismatch"),
                () ->assertEquals(CHECK_DATA_MSG,loginService.getMessage())
        );
    }

    @Test
    @DisplayName("Verify Login with a short psw")
    public void loginWithShotPsw() {
        LoginService loginService = new LoginService();
        Response response = loginService.sendLoginRequest(TestDataGenerator.generateRandomEmail(), TestDataGenerator.generateRandomString(5), true);

        assertAll("Login with a short psw",
                () -> assertEquals(422,loginService.getStatusCode(),"Status code mismatch"),
                () ->assertEquals(INVALID_DATA_MSG,loginService.getMessage()),
                () ->assertEquals(SHORT_PSW_MSG, loginService.getPasswordError(),"Password error msg mismatch")
        );
    }

    @Test
    @Disabled("Skipping successful login test for now")
    @DisplayName("Verify successful login")
    public void loginSuccessful() {
        LoginService loginService = new LoginService();
        Response response = loginService.sendLoginRequest(TestDataGenerator.getValidEmail(), TestDataGenerator.getValidPsw(), true);

        assertAll("Login with existing user",
                () -> assertEquals(200,loginService.getStatusCode(),"Status code mismatch"),
                () ->assertFalse(loginService.getToken().isEmpty(),"Token should not be empty")
        );
    }
}
