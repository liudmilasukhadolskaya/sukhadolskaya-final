package by.yr.api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiService {

    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("content-type", "application/json");
        return headers;
    }

    public Response doPost(String url, String body) {
        return given()
                .headers(getDefaultHeaders())
                .body(body)
                .when()
                .post(url);
    }

    public Response doGet(String url, Map<String, Object> queryParams) {
        return given()
                .headers(getDefaultHeaders())
                .queryParams(queryParams != null ? queryParams : new HashMap<>())
                .when()
                .get(url);
    }
}
