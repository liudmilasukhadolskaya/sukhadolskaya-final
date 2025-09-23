package by.yr.api;

import by.yr.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {
    private SearchService searchService;

    @BeforeEach
    void setup() {
        searchService = new SearchService();
    }

    @Test
    @DisplayName("Verify search by popular keyword returns results")
    public void searchByPopularKeyword() {
        Response response = searchService.searchByPopularKeyword();
        List<String>titles=searchService.getTitles(response);

        assertAll("Response validation",
                () -> assertEquals(200, searchService.getStatusCode(response), "Status code"),
                () -> assertTrue(searchService.getDataSize(response)>0,  "data.size() > 0"),
                () -> ApiAssertions.assertDefaultMeta(response));
    }
    @Test
    @DisplayName("Verify search for specific product")
    public void searchSpecificProduct() {
        String keyword="Ondes";
        Response response = searchService.sendSearchProductRequest(keyword);
        List<String>titles=searchService.getTitles(response);
        assertAll("Response validation",
                () -> assertEquals(200,searchService.getStatusCode(response), "Status code"),
                () -> assertTrue(searchService.getDataSize(response)>0,  "data.size() > 0"),
                ()->assertTrue(titles.stream().anyMatch(title->title.contains(keyword)),"At least one item contains "+keyword),
                () -> ApiAssertions.assertDefaultMeta(response));
    }


    @Test
    @DisplayName("Verify the search when no results found")
    public void searchWithNoResults() {
        Response response = searchService.sendSearchProductRequest(TestDataGenerator.generateRandomString(5));
        assertAll("Response validation",
                () -> assertEquals(200, searchService.getStatusCode(response), "Status code"),
                () -> assertEquals(0, searchService.getDataSize(response), "Data size"),
                () -> ApiAssertions.assertDefaultMeta(response));
    }
}
