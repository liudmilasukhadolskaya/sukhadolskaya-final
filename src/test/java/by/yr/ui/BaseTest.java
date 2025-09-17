package by.yr.ui;

import by.yr.ui.WebDriver;
import org.junit.jupiter.api.AfterEach;

public class BaseTest {
  @AfterEach
    public void closeBrowser(){
        by.yr.ui.WebDriver.quit();
    }
}
