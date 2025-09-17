package by.yr.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class SearchTest {

        @Test
        @DisplayName("Verify search by keyword returns results")
        public void test1() {
            SearchService searchService = new SearchService();
            searchService.sendSearchProductRequest("Ondes");

            Response response = searchService.getResponse();
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("data.size()", greaterThan(0))
                    .body("data[0].title", containsString("Ondes"))
                    .body("meta.current_page",equalTo(1))
                    .body("meta.from", greaterThanOrEqualTo(1))
                    .body("meta.total", greaterThanOrEqualTo(1))
                    .body("meta.last_page",greaterThanOrEqualTo(1))
                    .body("meta.path",is("https://api.y-r.by/api/v1/products"))
                    .body("meta.per_page",equalTo(16))
                    .body("meta.to",greaterThanOrEqualTo(1))
                    .body("meta.total",greaterThanOrEqualTo(1));
        }

        @Test
        @DisplayName("Verify the msg when there are no results")
        public void test2() {
            SearchService searchService = new SearchService();
            searchService.sendSearchProductRequest("qwerty");

            Response response = searchService.getResponse();
            response.then()
                    .statusCode(200)
                    .log().all()
                    .body("data.size()",equalTo(0))
                    .body("meta.current_page",equalTo(1))
                    .body("meta.from", is(nullValue()))
                    .body("meta.total", equalTo(0))
                    .body("meta.last_page",equalTo(1))
                    .body("meta.path",is("https://api.y-r.by/api/v1/products"))
                    .body("meta.per_page",equalTo(16))
                    .body("meta.to",nullValue())
                    .body("meta.total",equalTo(0));
        }
    }
