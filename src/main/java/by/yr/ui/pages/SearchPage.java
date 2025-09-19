package by.yr.ui.pages;

import by.yr.ui.utils.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchPage {
    private final String INPUT_SEARCH_FIELD = "//input[@class='eye-font eye-placeholder ng-tns-c114-0 ng-untouched ng-pristine ng-valid']";
    private final String TITLES_FROM_SEARCH_RESULT_TEMPLATE = "(//app-product-list//app-goods-card//div[@class='card-name'])[%d]";
    private final String TITLES_FROM_SEARCH_RESULT = "//app-product-list//app-goods-card//div[@class='card-name']";
    private final String TITLE_NO_RESULTS = "//div[@class='empty-result ng-star-inserted']";
    private final String TOP_NAV_CATEGORIES = "//ul[@class='main-nav_list main-nav_list_first-level']/li/a";
    private final Set<String> EXCLUDED_CATEGORIES = Set.of("ACT BEAUTIFUL", "АКЦИИ");
    private final String PRICE_FOR_PRODUCT_TEXT = "(//div[@class='products']//span[@itemprop='price'])[%d]";
    private WebElement selectedCategory;

    public void sendKeysSearch(String search) {
        WebDriver.sendKeysToElement(INPUT_SEARCH_FIELD, search + Keys.ENTER);
    }

    public String getProductNameByPosition(int index) {
        String xpath = String.format(TITLES_FROM_SEARCH_RESULT_TEMPLATE, index);
        return WebDriver.getTextFromElement(xpath);
    }

    public List<String> getSearchResultItemsTitleText() {
        return WebDriver.findElements(TITLES_FROM_SEARCH_RESULT).stream()
                        .map(el -> el.getText().toLowerCase()
                                              .replace("\"", "").trim()).toList();
    }

    public String getNoResultsMsg() {
        return WebDriver.getTextFromElement(TITLE_NO_RESULTS);
    }

    public List<WebElement> getAllTopNavCategories() {
        return WebDriver.getDriver().findElements(By.xpath(TOP_NAV_CATEGORIES));
    }

    public String getPriceForProductByPosition(int index) {
        return WebDriver.getTextFromElement(String.format(PRICE_FOR_PRODUCT_TEXT, index));
    }

    public void pickRandomCategory() {
        List<WebElement> categories = getAvailableCategories();
        Random random = new Random();
        selectedCategory = categories.get(random.nextInt(categories.size()));
        selectedCategory.click();
    }

    private List<WebElement> getAvailableCategories() {
        return getAllTopNavCategories().stream()
                                       .filter(e -> !EXCLUDED_CATEGORIES.contains(e.getText().trim()))
                                       .collect(Collectors.toList());
    }

    public boolean areSearchResultsMostlyRelevant(String keyword, double threshold) {
        List<String> results = getSearchResultItemsTitleText();

        if (results.isEmpty()) {
            return false; // no results = not relevant
        }

        long matches = results.stream()
                              .map(String::toLowerCase)
                              .filter(title -> title.contains(keyword.toLowerCase()))
                              .count();

        double ratio = (double) matches / results.size();
        return ratio >= threshold;
    }
}
