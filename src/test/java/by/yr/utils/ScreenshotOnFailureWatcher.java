package by.yr.utils;

import by.yr.ui.utils.DriverUtils;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class ScreenshotOnFailureWatcher
   implements AfterTestExecutionCallback {

        @Override
        public void afterTestExecution(ExtensionContext context) throws Exception {
            boolean failed = context.getExecutionException().isPresent();
            if (failed) {
                WebDriver driver = DriverUtils.getDriver();

                // Wait for the main content or modal to be visible
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(5)) // wait max 5 seconds
                                                                     .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
                    // Replace 'body' with a more specific element if possible
                } catch (Exception ignored) {
                    // If element not visible after timeout, still take screenshot
                }

                // Take screenshot
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on failure: " + context.getDisplayName(),
                        new ByteArrayInputStream(screenshot));
            }
        }
    }

