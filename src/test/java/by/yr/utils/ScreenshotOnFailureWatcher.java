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

                try {
                    new WebDriverWait(driver, Duration.ofSeconds(5))
                                                                     .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
                } catch (Exception ignored) {
                }
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on failure: " + context.getDisplayName(),
                        new ByteArrayInputStream(screenshot));
            }
        }
    }
