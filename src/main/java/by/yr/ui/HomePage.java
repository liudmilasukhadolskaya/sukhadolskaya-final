package by.yr.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final String URL = "https://www.y-r.by";
    public final String BUTTON_ACCEPT_COOKIES = "//button[.//span[contains(text(),'Принять')]]";
    public final String BUTTON_CLOSE_POPUP = "//button[contains(@class,'nsm-dialog-btn-close')]";
    public final String LINK_PERSONAL_ACCOUNT = "//a[@routerlink='/auth/login']";


    public HomePage() {

    }

    public void openSite() {
        by.yr.ui.WebDriver.getDriver().get(URL);
    }

    public HomePage acceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(by.yr.ui.WebDriver.getDriver(), Duration.ofSeconds(10));
            WebElement acceptBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(BUTTON_ACCEPT_COOKIES))
            );
            Thread.sleep(500);
            by.yr.ui.WebDriver.clickElement(BUTTON_ACCEPT_COOKIES);
            System.out.println("✅ Cookies accepted.");
        } catch (Exception e) {
            System.out.println("⚠️ Cookies msg not found or already closed.");
        }
        return this;
    }

    public HomePage closePopup() {
        try {
            WebDriverWait wait = new WebDriverWait(by.yr.ui.WebDriver.getDriver(), Duration.ofSeconds(15));
            WebElement closeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(BUTTON_CLOSE_POPUP))
            );
            by.yr.ui.WebDriver.clickElement(BUTTON_CLOSE_POPUP);
            System.out.println("✅ Popup closed.");
        } catch (Exception e) {
            System.out.println("⚠️ Popup not found or already closed.");
        }
        return this;
    }

    public void clickPersonalAccount() {
        by.yr.ui.WebDriver.clickElement(LINK_PERSONAL_ACCOUNT);
    }
}

