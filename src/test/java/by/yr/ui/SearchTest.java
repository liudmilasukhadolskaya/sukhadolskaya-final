package by.yr.ui;

import by.yr.ui.pages.SearchPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class SearchTest extends BaseTest {

    @Test
    @DisplayName("Verify the msg when no results found")
    public void test1() {
        SearchPage searchPage = new SearchPage();
        searchPage.sendKeysSearch("keyboard");

        Assertions.assertEquals("Упс\n" +
                "Извините, мы не смогли найти то, что вы ищете.\n" +
                "Попробуйте другой запрос или посмотрите наш каталог.", searchPage.getNoResultsMsg());
    }

    @ParameterizedTest
    @ValueSource(strings = {"крем", "шампунь", "гель", "маска"})
    @DisplayName("Verify that search result is mostly relevant (60%)")
    void test2(String keyword) {
        SearchPage searchPage = new SearchPage();
        searchPage.sendKeysSearch(keyword);

        boolean relevant = searchPage.areSearchResultsMostlyRelevant(keyword, 0.6);

        Assertions.assertTrue(relevant, "Less than 60% of search results are relevant to " + keyword);
    }

    @Test
    @DisplayName("Verify search for a specific item")
    public void test3() {
        SearchPage searchPage = new SearchPage();
        String keyword = "парфюмерная вода ondes positives";
        searchPage.sendKeysSearch(keyword);

        List<String> results = searchPage.getSearchResultItemsTitleText();
        boolean found = false;
        for (String title : results) {
            if (title.contains(keyword.toLowerCase())) {
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found,
                "Search result does not contain " + keyword);
    }
}
