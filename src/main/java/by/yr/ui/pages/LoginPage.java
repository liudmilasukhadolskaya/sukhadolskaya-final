package by.yr.ui.pages;

import by.yr.ui.utils.DriverUtils;
import io.qameta.allure.Step;

public class LoginPage {
    private final String INPUT_EMAIL = "//input[@id='user-email']";
    private final String INPUT_PASSWORD = "//input[@id='user-password']";
    private final String BUTTON_SUBMIT = "//button[@class='submit-btn eye-button']";
    private final String TITLE_EMAIL_VALIDATION = "//val-errors[@controlname='email']/div[@class='ng-star-inserted']";
    private final String TITLE_PSW_VALIDATION = "//val-errors[@controlname='password']/div[@class='ng-star-inserted']";
    private final String LINK_CREATE_NEW_PSW = "//a[@routerlink='/auth/forgot-password']";
    private final String TITLE_INCORRECT_ACCOUNT_DATA = "//val-errors[@class='ant-form-item-explain']/div[@class='ng-star-inserted']";
    private final String LINK_RETURN_TO_HP = "//div[@class='back-to-the-main']/p";
    public final String TITLE_NEW_PSW = "//form[@class='ng-untouched ng-pristine ng-invalid']/h2";
    public final String TITLE_IN_PERSONAL_ACCOUNT = "//div[@class='sidebar']/h2";

    public String getTitleReturnToHP() {
        return DriverUtils.getTextFromElement(LINK_RETURN_TO_HP);
    }

    public String getTitleIncorrectAccount() {
        return DriverUtils.getTextFromElement(TITLE_INCORRECT_ACCOUNT_DATA);
    }

    public String getTitleInvalidEmail() {
        return DriverUtils.getTextFromElement(TITLE_EMAIL_VALIDATION);
    }

    public String getTitleInvalidPassword() {
        return DriverUtils.getTextFromElement(TITLE_PSW_VALIDATION);
    }

    @Step("Enter email: {email}")
    public void sendKeysEmail(String email) {
        DriverUtils.sendKeysToElement(INPUT_EMAIL, email);
    }

    @Step("Enter password: {password}")
    public void sendKeysPsw(String password) {
        DriverUtils.sendKeysToElement(INPUT_PASSWORD, password);
    }

    @Step("Click login")
    public void clickLogin() {
        DriverUtils.clickElement(BUTTON_SUBMIT);
    }

    @Step("Click login")
    public void clickLoginWithActions() {
        DriverUtils.clickElementWithActions(BUTTON_SUBMIT);
    }

    @Step("Click Create New Password link")
    public void clickCreateNewPassword() {
        DriverUtils.clickElement(LINK_CREATE_NEW_PSW);
    }

    public String getCreateNewPasswordText() {
        return DriverUtils.getTextFromElement(TITLE_NEW_PSW);
    }

    public String getPersonalAccountText() {
        return DriverUtils.getTextFromElement(TITLE_IN_PERSONAL_ACCOUNT);
    }

    public void clickEmailField() {
        DriverUtils.clickElement(INPUT_EMAIL);
    }

    public void clickPasswordField() {
        DriverUtils.clickElement(INPUT_PASSWORD);
    }
}
