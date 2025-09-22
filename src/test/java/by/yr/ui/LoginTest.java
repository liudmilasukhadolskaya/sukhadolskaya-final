package by.yr.ui;

import by.yr.ui.pages.HomePage;
import by.yr.ui.pages.LoginPage;
import by.yr.utils.TestDataGenerator;
import org.junit.jupiter.api.*;

public class LoginTest extends BaseTest {
        public static final String RETURN_TO_HOME = "Вернуться на главную";
        public static final String INVALID_EMAIL = "Email* должен быть валидным";
        public static final String SHORT_PASSWORD = "Пароль* должен быть больше 6 символов";
        public static final String REQUIRED_FIELD = "Это поле является обязательным";
        public static final String INCORRECT_ACCOUNT = "Проверьте корректность введенных данных";
        public static final String CREATE_NEW_PASSWORD = "Новый пароль";
        public static final String SUCCESSFUL_LOGIN = "Здравствуйте, Юля!";

    @BeforeEach
    public void goToLoginPage() {
        HomePage homePage = new HomePage();
        homePage.clickPersonalAccount();
    }

    @Test
    @DisplayName("Verify that Login Page is opened")
    public void loginPageOpened() {
       LoginPage loginPage = new LoginPage();
        Assertions.assertEquals(RETURN_TO_HOME, loginPage.getTitleReturnToHP());
    }

    @Test
    @DisplayName("Verify the msg for invalid email and missing psw")
    public void invalidEmailMissingPsw() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomString(5));
        loginPage.clickLogin();

        Assertions.assertEquals(INVALID_EMAIL, loginPage.getTitleInvalidEmail());
    }

    @Test
    @DisplayName("Verify the msg for short psw")
    public void shortPsw() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomEmail());
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(5));
        loginPage.clickLogin();

        Assertions.assertEquals(SHORT_PASSWORD, loginPage.getTitleInvalidPassword());
    }

    @Test
    @DisplayName("Verify the msg for missing email")
    public void missingEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(7));
        loginPage.clickLogin();
        loginPage.clickEmailField();
        loginPage.clickLogin();

        Assertions.assertEquals(REQUIRED_FIELD, loginPage.getTitleInvalidEmail());
    }

    @Test
    @DisplayName("Verify the msg for not existing user")
    public void notExistingUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomEmail());
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(7));
        loginPage.clickLogin();

        Assertions.assertEquals(INCORRECT_ACCOUNT, loginPage.getTitleIncorrectAccount());
    }

    @Test
    @DisplayName("Verify Create New PSW link works")
    public void newPswLink() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickCreateNewPassword();

        Assertions.assertEquals(CREATE_NEW_PASSWORD, loginPage.getCreateNewPasswordText());
    }

    @Test
    @Disabled("Skipping successful login test for now")
    @DisplayName("Verify successful login")
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.getValidEmail());
        loginPage.sendKeysPsw(TestDataGenerator.getValidPsw());
        loginPage.clickLogin();

        Assertions.assertEquals(SUCCESSFUL_LOGIN, loginPage.getPersonalAccountText());
    }
}
