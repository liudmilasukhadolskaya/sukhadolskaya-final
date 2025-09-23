package by.yr.api;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class SearchService {
    private final Random random=new Random();

    public Response sendSearchProductRequest(String keyword) {
        ApiService apiService = new ApiService();
        System.out.println("Searching with "+keyword);
        return apiService.doGet(ApiConstants.SEARCH_URL, Map.of("search", keyword));
    }

    public List<String>getTitles(Response response){
        return response.path("data.title");
    }

    public int getDataSize(Response response){
        return (int) response.path("data.size()");
    }

    public int getStatusCode(Response response){
        return response.getStatusCode();
    }

    public Response searchByPopularKeyword(){
        List<String>keywords=List.of("шампунь","гель","мыло","крем","помада","сыворотка");
        String keyword=keywords.get(random.nextInt(keywords.size()));
        return sendSearchProductRequest(keyword);
    }
}
