package by.yr.api;

import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

public class ApiAssertions {

    public static void assertDefaultMeta(Response response) {
        Integer metaFrom = response.path("meta.from");
        Integer metaTo = response.path("meta.to");
        Integer metaTotal = response.path("meta.total");

        assertAll("Meta validation",

                () -> assertEquals(1, (int) response.path("meta.current_page"), "Current page"),
                () -> assertEquals(ApiConstants.SEARCH_URL, response.path("meta.path"), "Meta path"),
                () -> assertTrue((int) response.path("meta.per_page") >= 1, "Per page >= 1"),

                () -> {
                    if (metaTotal != null && metaTotal > 0) {
                        assertNotNull(metaFrom, "From is not null");
                        assertTrue(metaFrom >= 1, "From >= 1");
                        assertTrue((int) response.path("meta.last_page") >= 1, "Last page >= 1");
                        assertNotNull(metaTo, "To is not null");
                        assertTrue(metaTo >= 1, "To >= 1");
                        assertTrue(metaTotal >= 1, "Total >= 1");
                    } else {
                        assertEquals(0, metaTotal != null ? metaTotal : 0, "Total = 0");
                        assertNull(metaFrom, "From is null");
                        assertTrue((int) response.path("meta.last_page") == 1, "Last page = 1");
                        assertNull(metaTo, "To is null");
                    }
                }
        );
    }
}
