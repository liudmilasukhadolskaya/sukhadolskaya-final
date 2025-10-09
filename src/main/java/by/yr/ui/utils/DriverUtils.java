package by.yr.ui.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DriverUtils {
    private static org.openqa.selenium.WebDriver driver;
    private static final Duration DEFAULT_WAIT = Duration.ofSeconds(10);
    private static final Logger logger = LogManager.getLogger(DriverUtils.class);


    public static org.openqa.selenium.WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            // You can control this from the command line: -Dheadless=true or -Dheadless=false
            boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
            if (isHeadless) {
                options.addArguments("--headless=new");
                logger.info("🧠 Running in headless mode");
            } else {
                logger.info("🧠 Running with visible browser");
            }

            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
        return driver;
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("🧹 WebDriver closed successfully");
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupted status
            logger.warn("Sleep was interrupted: {} ", e.getMessage());
        }
    }

    public static WebElement findElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT);
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            logger.error("❌ Element not found by xpath: {}", xpath);
            throw e;
        }
    }

    public static List<WebElement> findElements(String xpath) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
        return getDriver().findElements(By.xpath(xpath));
    }

    public static void clickElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        for (int i = 0; i < 3; i++) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                logger.warn("⚠️ Stale element, retrying... attempt {}", i + 1);
                DriverUtils.sleep(1);
            } catch (ElementClickInterceptedException e) {
                logger.warn("⚠️ Click intercepted. Retrying using JavaScript for: {}", xpath);
                WebElement element = getDriver().findElement(By.xpath(xpath));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
                return;
            } catch (Exception e) {
                logger.error("❌ Failed to click element: {} - {}", xpath, e.getMessage());
            }
        }
        throw new RuntimeException("Failed to click element after retries: " + xpath);
    }

    public static void clickElementWithActions(String locator) {
        WebElement element = driver.findElement(By.xpath(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

    public static void sendKeysToElement(String xpath, String value) {
        findElement(xpath).sendKeys(value);
    }

    public static String getTextFromElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_WAIT);
        return wait.until(driver -> {
            WebElement element = driver.findElement(By.xpath(xpath));
            String text = element.getText();
            if (text != null && !text.trim().isEmpty()) {
                logger.info("📄 Text from {}: '{}'", xpath, text);
                return text;
            }
            return null;
        });
    }

    public static double getTextAsDouble(String xpath) {
        String rawText = DriverUtils.getTextFromElement(xpath);
        String cleaned = rawText.replaceAll("[^0-9,\\.]", "");
        return Double.parseDouble(cleaned);
    }

    public static int getTextAsInt(String xpath) {
        String rawText = DriverUtils.getTextFromElement(xpath);
        String cleaned = rawText.replaceAll("[^0-9]", "");
        return Integer.parseInt(cleaned);
    }
}
