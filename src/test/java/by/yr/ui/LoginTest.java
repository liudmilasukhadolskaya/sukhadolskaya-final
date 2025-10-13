package by.yr.ui;

import by.yr.ui.pages.HomePage;
import by.yr.ui.pages.LoginPage;
import by.yr.utils.ScreenshotOnFailureWatcher;
import by.yr.utils.TestDataGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ScreenshotOnFailureWatcher.class)
public class LoginTest extends BaseTest {
    public static final String RETURN_TO_HOME_TITLE = "Вернуться на главную";
    public static final String INVALID_EMAIL_MSG = "Email* должен быть валидным";
    public static final String SHORT_PASSWORD_MSG = "Пароль* должен быть больше 6 символов";
    public static final String REQUIRED_FIELD_MSG = "Это поле является обязательным";
    public static final String INCORRECT_ACCOUNT_MSG = "Проверьте корректность введенных данных";
    public static final String CREATE_NEW_PASSWORD_TITLE = "Новый пароль";
    public static final String SUCCESSFUL_LOGIN_TITLE = "Здравствуйте, Юля!";

    @BeforeEach
    public void goToLoginPage() {
        HomePage homePage = new HomePage();
        homePage.clickPersonalAccount();
    }

    @Test
    @DisplayName("Verify that Login Page is opened")
    public void loginPageOpened() {
        LoginPage loginPage = new LoginPage();
        Assertions.assertEquals(RETURN_TO_HOME_TITLE, loginPage.getTitleReturnToHP());
    }

    @Test
    @DisplayName("Verify the msg for empty psw")
    public void emptyPsw() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomEmail());
        loginPage.clickPasswordField();
        loginPage.clickLoginWithActions();

        Assertions.assertEquals(REQUIRED_FIELD_MSG, loginPage.getTitleInvalidPassword());
    }

    @Test
    @DisplayName("Verify the msg for short psw")
    public void shortPsw() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomEmail());
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(5));
        loginPage.clickLoginWithActions();

        Assertions.assertEquals(SHORT_PASSWORD_MSG, loginPage.getTitleInvalidPassword());
    }

    @Test
    @DisplayName("Verify the msg for empty email")
    public void emptyEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickEmailField();
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(7));
        loginPage.clickLoginWithActions();

        Assertions.assertEquals(REQUIRED_FIELD_MSG, loginPage.getTitleInvalidEmail());
    }

    @Test
    @DisplayName("Verify the msg for empty email and password")
    public void emptyEmailAndPsw() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickEmailField();
        loginPage.clickPasswordField();
        loginPage.clickLoginWithActions();

        assertAll("Login with missing email and psw validation",
                () -> assertEquals(REQUIRED_FIELD_MSG, loginPage.getTitleInvalidEmail()),
                () -> assertEquals(REQUIRED_FIELD_MSG, loginPage.getTitleInvalidPassword()));
    }

    @Test
    @DisplayName("Verify the msg for not existing user")
    public void notExistingUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomEmail());
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(7));
        loginPage.clickLogin();

        Assertions.assertEquals(INCORRECT_ACCOUNT_MSG, loginPage.getTitleIncorrectAccount());
    }

    @Test
    @DisplayName("Verify the msg for incorrect email format")
    public void incorrectEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomString(5));
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(7));
        loginPage.clickLoginWithActions();

        Assertions.assertEquals(INVALID_EMAIL_MSG, loginPage.getTitleInvalidEmail());
    }

    @Test
    @DisplayName("Verify Create New PSW link works")
    public void newPswLink() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickCreateNewPassword();

        Assertions.assertEquals(CREATE_NEW_PASSWORD_TITLE, loginPage.getCreateNewPasswordText());
    }

    @Test
    @Disabled("Skipping successful login test for now")
    @DisplayName("Verify successful login")
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.getValidEmail());
        loginPage.sendKeysPsw(TestDataGenerator.getValidPsw());
        loginPage.clickLogin();

        Assertions.assertEquals(SUCCESSFUL_LOGIN_TITLE, loginPage.getPersonalAccountText());
    }
}
