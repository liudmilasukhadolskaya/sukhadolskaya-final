package by.yr.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class SearchService {
    private final Random random = new Random();

    @Step("Send Search Product Request and Get Response")
    public Response sendSearchProductRequest(String keyword) {
        ApiService apiService = new ApiService();
        System.out.println("Searching with " + keyword);
        return apiService.doGet(ApiConstants.SEARCH_URL, Map.of("search", keyword));
    }

    @Step("Get data.title from Response")
    public List<String> getTitles(Response response) {
        return ((List<String>) response.path("data.title")).stream()
                                                           .map(t -> t.toLowerCase().replace("\"", "").trim())
                                                           .toList();
    }

    @Step("Check if data.title contains keyword")
    public boolean containsKeyword(List<String> titles, String keyword) {
        return titles.stream().anyMatch(title -> title.contains(keyword.toLowerCase()));
    }

    @Step("Get data.size() from Response")
    public int getDataSize(Response response) {
        return (int) response.path("data.size()");
    }

    @Step("Get Status Code from Response")
    public int getStatusCode(Response response) {
        return response.getStatusCode();
    }
}
