package by.yr.ui.utils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DriverUtils {
    private static org.openqa.selenium.WebDriver driver;
    private static final Duration DEFAULT_WAIT=Duration.ofSeconds(10);

    public static org.openqa.selenium.WebDriver getDriver() {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
        return driver;
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupted status
            System.err.println("Sleep was interrupted: " + e.getMessage());
        }
    }

    public static WebElement findElement(String xpath) {
        WebDriverWait wait=new WebDriverWait(getDriver(),DEFAULT_WAIT);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public static List<WebElement>findElements(String xpath){
        WebDriverWait wait=new WebDriverWait(getDriver(),DEFAULT_WAIT);
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
                System.out.println("⚠️ Stale element, retrying... (" + (i + 1) + ")");
                DriverUtils.sleep(1);
            } catch (ElementClickInterceptedException e) {
                WebElement element = getDriver().findElement(By.xpath(xpath));
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                js.executeScript("arguments[0].click();", element);
                return;
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
            return (text != null && !text.trim().isEmpty()) ? text : null;
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
