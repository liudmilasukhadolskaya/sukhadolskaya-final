package by.yr.ui;

import by.yr.ui.pages.SearchPage;
import by.yr.utils.ScreenshotOnFailureWatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@ExtendWith(ScreenshotOnFailureWatcher.class)
public class SearchTest extends BaseTest {
    public static final String noResultsMsg = """
            Упс
            Извините, мы не смогли найти то, что вы ищете.
            Попробуйте другой запрос или посмотрите наш каталог.""";

    @Test
    @DisplayName("Verify the msg when no results found")
    public void noResultsFound() {
        SearchPage searchPage = new SearchPage();
        searchPage.sendKeysSearch("keyboard");

        Assertions.assertEquals(noResultsMsg, searchPage.getNoResultsMsg());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/search_keywords.csv")
    @DisplayName("Verify that search result is mostly relevant (60%)")
    void searchResultIsRelevant(String keyword) {
        SearchPage searchPage = new SearchPage();
        searchPage.sendKeysSearch(keyword);
        boolean relevant = searchPage.areSearchResultsMostlyRelevant(keyword, 0.6);

        Assertions.assertTrue(relevant, "Less than 60% of search results are relevant to " + keyword);
    }

    @Test
    @DisplayName("Verify search for a specific item")
    public void searchForSpecificProduct() {
        SearchPage searchPage = new SearchPage();
        String keyword = "парфюмерная вода ondes positives";
        searchPage.sendKeysSearch(keyword);
        boolean found = searchPage.searchAndVerifyResult(keyword);
        Assertions.assertTrue(found,
                "Search result does not contain " + keyword);
    }
}
