package by.yr.ui;

import by.yr.ui.pages.HomePage;
import by.yr.ui.pages.LoginPage;
import by.yr.utils.TestDataGenerator;
import org.junit.jupiter.api.*;

public class LoginTest extends BaseTest {
    @BeforeEach
    public void goToLoginPage() {
        HomePage homePage = new HomePage();
        homePage.clickPersonalAccount();
    }

    @Test
    @DisplayName("Verify that Login Page is opened")
    public void loginPageOpened() {
       LoginPage loginPage = new LoginPage();
        Assertions.assertEquals("Вернуться на главную", loginPage.getTitleReturnToHP());
    }

    @Test
    @DisplayName("Verify the msg for invalid email and missing psw")
    public void invalidEmailMissingPsw() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomString(5));
        loginPage.clickLogin();

        Assertions.assertEquals("Email* должен быть валидным", loginPage.getTitleInvalidEmail());
    }

    @Test
    @DisplayName("Verify the msg for short psw")
    public void shortPsw() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomEmail());
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(5));
        loginPage.clickLogin();

        Assertions.assertEquals("Пароль* должен быть больше 6 символов", loginPage.getTitleInvalidPassword());
    }

    @Test
    @DisplayName("Verify the msg for missing email")
    public void missingEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(7));
        loginPage.clickLogin();
        loginPage.clickEmailField();
        loginPage.clickLogin();

        Assertions.assertEquals("Это поле является обязательным", loginPage.getTitleInvalidEmail());
    }

    @Test
    @DisplayName("Verify the msg for not existing user")
    public void notExistingUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.generateRandomEmail());
        loginPage.sendKeysPsw(TestDataGenerator.generateRandomString(7));
        loginPage.clickLogin();

        Assertions.assertEquals("Проверьте корректность введенных данных", loginPage.getTitleIncorrectAccount());
    }

    @Test
    @DisplayName("Verify Create New PSW link works")
    public void newPswLink() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickCreateNewPassword();

        Assertions.assertEquals("Новый пароль", loginPage.getCreateNewPasswordText());
    }

    @Test
    @Disabled("Skipping successful login test for now")
    @DisplayName("Verify successful login")
    public void successfulLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail(TestDataGenerator.getValidEmail());
        loginPage.sendKeysPsw(TestDataGenerator.getValidPsw());
        loginPage.clickLogin();

        Assertions.assertEquals("Здравствуйте, Юля!", loginPage.getPersonalAccountText());
    }
}
