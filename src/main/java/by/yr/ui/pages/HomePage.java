package by.yr.ui.pages;

import by.yr.ui.utils.DriverUtils;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final String URL = "https://www.y-r.by";
    public final String BUTTON_ACCEPT_COOKIES = "//button[@class='btn cookie-actions-button'][2]";
    public final String BUTTON_CLOSE_POPUP = "//button[contains(@class,'nsm-dialog-btn-close')]";
    public final String LINK_PERSONAL_ACCOUNT = "//a[@routerlink='/auth/login']";
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    public HomePage() {

    }

    public void openSite() {
        DriverUtils.getDriver().get(URL);
    }

    public HomePage acceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(10));
            wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(BUTTON_ACCEPT_COOKIES))
            );
            DriverUtils.sleep(1);
            DriverUtils.clickElement(BUTTON_ACCEPT_COOKIES);
            logger.info("✅ Cookies popup accepted successfully.");
        } catch (Exception e) {
            logger.warn("⚠️ Cookies popup not found or already closed. Message: {}", e.getMessage());
        }
        return this;
    }

    public HomePage closeSelectLanguagePopup() {
        try {
            WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(15));
            WebElement closeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(BUTTON_CLOSE_POPUP))
            );
            DriverUtils.clickElement(BUTTON_CLOSE_POPUP);
            logger.info("✅ Select Language popup closed successfully.");
        } catch (Exception e) {
            logger.warn("⚠️ Select Language popup not found or already closed. Message: {}", e.getMessage());
        }
        return this;
    }

    @Step("Click Personal Account")
    public void clickPersonalAccount() {
        DriverUtils.clickElement(LINK_PERSONAL_ACCOUNT);
    }
}
