package by.yr.ui.utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebDriver {
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
        findElement(xpath).click();
    }


    public static void sendKeysToElement(String xpath, String value) {
        findElement(xpath).sendKeys(value);
    }

    public static String getTextFromElement(String xpath) {
        WebDriverWait wait=new WebDriverWait(getDriver(),DEFAULT_WAIT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText();
    }


    public static double getTextAsDouble(String xpath) {
        String rawText = WebDriver.getTextFromElement(xpath);
        // clean: remove currency symbols, spaces, keep digits, dots and commas
        String cleaned = rawText.replaceAll("[^0-9,\\.]", "");
        return Double.parseDouble(cleaned);
    }

    public static int getTextAsInt(String xpath) {
        String rawText = WebDriver.getTextFromElement(xpath);
        String cleaned = rawText.replaceAll("[^0-9]", "");
        return Integer.parseInt(cleaned);
    }
}


