package by.yr.api;

import io.restassured.response.Response;

import java.util.Map;

public class SearchService {
    private final String URL = "https://api.y-r.by/api/v1/products";
    private Response response;

    public void sendSearchProductRequest(String keyword) {
        ApiService apiService = new ApiService();
        apiService.doGet(URL, Map.of("search", keyword));
        response = apiService.getResponse();
    }

    public Response getResponse() {
        return response;
    }
}
