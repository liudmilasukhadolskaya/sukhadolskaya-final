package by.yr.ui;


import by.yr.ui.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {
    @BeforeEach
    public void openSite() {
        HomePage homePage = new HomePage();
        homePage.openSite();
        homePage.acceptCookies();
        homePage.closePopup();
        homePage.clickPersonalAccount();
    }

    @Test
    @DisplayName("Verify that Login Page is opened")
    public void test1() {
       by.yr.ui.LoginPage loginPage = new by.yr.ui.LoginPage();
        Assertions.assertEquals("Вернуться на главную", loginPage.getTitleReturnToHP());
    }

    @Test
    @DisplayName("Verify the msg for invalid email")
    public void test2() {
        by.yr.ui.LoginPage loginPage = new by.yr.ui.LoginPage();
        loginPage.sendKeysEmail("rew");
        loginPage.clickLogin();

        Assertions.assertEquals("Email* должен быть валидным", loginPage.getTitleInvalidEmail());
    }

    @Test
    @DisplayName("Verify the msg for short psw")
    public void test3() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysPsw("123");
        loginPage.clickLogin();

        Assertions.assertEquals("Пароль* должен быть больше 6 символов", loginPage.getTitleInvalidPassword());
    }

    @Test
    @DisplayName("Verify the msg for not existing/invalid account")
    public void test4() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail("qwerty@gmail.com");
        loginPage.sendKeysPsw("1234567");
        loginPage.clickLogin();

        Assertions.assertEquals("Проверьте корректность введенных данных", loginPage.getTitleIncorrectAccount());
    }

    @Test
    @DisplayName("Verify Create New PSW link works")
    public void test5() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickCreateNewPassword();

        Assertions.assertEquals("Новый пароль", loginPage.getCreateNewPasswordText());
    }

    @Test
    @DisplayName("Verify successful login")
    public void test6() {
        LoginPage loginPage = new LoginPage();
        loginPage.sendKeysEmail("webnwmsbfxafriczku@nespj.com");
        loginPage.sendKeysPsw("mypsw12345");
        loginPage.clickLogin();

        Assertions.assertEquals("Здравствуйте, Юля!", loginPage.getPersonalAccountText());
    }
}
