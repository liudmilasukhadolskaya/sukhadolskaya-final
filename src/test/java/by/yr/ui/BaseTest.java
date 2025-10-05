package by.yr.ui;

import by.yr.ui.pages.HomePage;
import by.yr.ui.utils.DriverUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    @BeforeEach
    public void openSiteAcceptCookiesClosePopup(){
        HomePage homePage = new HomePage();
        homePage.openSite();
        homePage.acceptCookies();
        homePage.closePopup();
    }

  @AfterEach
    public void closeBrowser(){
        DriverUtils.quit();
    }
}
