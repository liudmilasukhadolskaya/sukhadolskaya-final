package by.yr.ui.pages;

import by.yr.ui.utils.DriverUtils;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchPage {
    private final String INPUT_SEARCH_FIELD = "//input[contains(@class,'eye-font') and contains(@class, 'eye-placeholder')]";
    private final String TITLES_FROM_SEARCH_RESULT_TEMPLATE = "(//app-product-list//app-goods-card//div[@class='card-name'])[%d]";
    private final String TITLES_FROM_SEARCH_RESULT = "//app-product-list//app-goods-card//div[@class='card-name']";
    private final String TITLE_NO_RESULTS = "//div[@class='empty-result ng-star-inserted']";
    private final String TOP_NAV_CATEGORIES = "//ul[@class='main-nav_list main-nav_list_first-level']/li/a";
    private final Set<String> EXCLUDED_CATEGORIES = Set.of("ACT BEAUTIFUL", "АКЦИИ");
    private final String PRICE_FOR_PRODUCT_TEXT = "(//div[@class='products']//span[@itemprop='price'])[%d]";
    private WebElement selectedCategory;
    private static final Logger logger = LogManager.getLogger(SearchPage.class);

    @Step("Enter {search} in a search field and Press Enter")
    public void sendKeysSearch(String search) {
        DriverUtils.sendKeysToElement(INPUT_SEARCH_FIELD, search + Keys.ENTER);
        logger.info("Search submitted for '{}'", search);
    }

    public String getProductNameByPosition(int index) {
        String name = DriverUtils.getTextFromElement(String.format(TITLES_FROM_SEARCH_RESULT_TEMPLATE, index));
        logger.info("Product name at position {}: {}", index, name);
        return name;
    }

    public List<String> getSearchResultItemsTitleText() {
        return DriverUtils.findElements(TITLES_FROM_SEARCH_RESULT).stream()
                          .map(el -> el.getText().toLowerCase()
                                       .replace("\"", "").trim()).toList();
    }

    public String getNoResultsMsg() {
        return DriverUtils.getTextFromElement(TITLE_NO_RESULTS);
    }

    public List<WebElement> getAllTopNavCategories() {
        return DriverUtils.getDriver().findElements(By.xpath(TOP_NAV_CATEGORIES));
    }

    public String getPriceForProductByPosition(int index) {
        String price = DriverUtils.getTextFromElement(String.format(PRICE_FOR_PRODUCT_TEXT, index));
        logger.info("Price for product at position {}: {}", index, price);
        return price;
    }

    @Step("Click random category from top nav")
    public void pickRandomCategory() {
        List<WebElement> categories = getAvailableCategories();
        Random random = new Random();
        selectedCategory = categories.get(random.nextInt(categories.size()));
        String categoryName = selectedCategory.getText().trim();
        logger.info("Clicking on random category: {}", categoryName);
        selectedCategory.click();
    }

    private List<WebElement> getAvailableCategories() {
        return getAllTopNavCategories().stream()
                                       .filter(e -> !EXCLUDED_CATEGORIES.contains(e.getText().trim()))
                                       .collect(Collectors.toList());
    }

    public boolean areSearchResultsMostlyRelevant(String keyword, double threshold) {
        List<String> results = getSearchResultItemsTitleText();

        long matches = results.stream()
                              .map(String::toLowerCase)
                              .filter(title -> title.contains(keyword.toLowerCase()))
                              .count();

        double ratio = (double) matches / results.size();
        return ratio >= threshold;
    }

    @Step("Search for product and verify presence of '{keyword}' in results")
    public boolean searchAndVerifyResult(String keyword) {
        sendKeysSearch(keyword);
        List<String> results = getSearchResultItemsTitleText();

        return results.stream()
                      .anyMatch(title -> title.contains(keyword.toLowerCase()));
    }
}
