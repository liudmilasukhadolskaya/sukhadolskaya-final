package by.yr.api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiService {
    private Response response;


    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("content-type", "application/json");
        return headers;
    }

    public void doPost(String url, String body) {
        response = given()
                .headers(getDefaultHeaders())
                .body(body)
                .when()
                .post(url);
    }

    public void doGet(String url, Map<String, Object> queryParams) {
        response = given()
                .headers(getDefaultHeaders())
                .queryParams(queryParams != null ? queryParams : new HashMap<>())
                .when()
                .get(url);
    }

    public Response getResponse() {
        return response;
    }
}
