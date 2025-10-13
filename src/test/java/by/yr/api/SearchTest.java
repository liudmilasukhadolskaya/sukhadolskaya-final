package by.yr.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {
    private SearchService searchService;

    @BeforeEach
    void setup() {
        searchService = new SearchService();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/search_keywords.csv")
    @DisplayName("Verify search by popular keyword returns results")
    public void searchByPopularKeyword(String keyword) {
        Response response = searchService.sendSearchProductRequest(keyword);
        List<String> titles = searchService.getTitles(response);

        assertAll("Response validation",
                () -> assertEquals(200, searchService.getStatusCode(response), "Status code mismatch"),
                () -> assertTrue(searchService.getDataSize(response) > 0, "data.size() should be > 0")
                );
    }

    @Test
    @DisplayName("Verify search for specific product")
    public void searchSpecificProduct() {
        String keyword = "парфюмерная вода ondes positives";
        Response response = searchService.sendSearchProductRequest(keyword);
        List<String> titles = searchService.getTitles(response);

        assertAll("Response validation",
                () -> assertEquals(200, searchService.getStatusCode(response), "Status code mismatch"),
                () -> assertTrue(searchService.getDataSize(response) > 0, "data.size() should be > 0"),
                () -> assertTrue(searchService.containsKeyword(titles, keyword), "Expected at least one product title to contain " + keyword)
               );
    }

    @Test
    @DisplayName("Verify the search when no results found")
    public void searchWithNoResults() {
        String keyword = "keyboard";
        Response response = searchService.sendSearchProductRequest(keyword);
        assertAll("Response validation",
                () -> assertEquals(200, searchService.getStatusCode(response), "Status code mismatch"),
                () -> assertEquals(0, searchService.getDataSize(response), "data.size() should be 0")
               );
    }
}
