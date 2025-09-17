package by.yr.api;

import io.restassured.response.Response;

public class LoginService {
    private final String URL = "https://api.y-r.by/api/v1/token";
    private Response response;


    public void sendLoginRequest(String email, String password, boolean remember) {
        ApiService apiService = new ApiService();
        String body = "{"
                + (email != null ? "\"email\":\"" + email + "\"," : "")
                + (password != null ? "\"password\":\"" + password + "\"," : "")
                + "\"remember\":" + remember
                + "}";

        apiService.doPost(URL, body);
        response = apiService.getResponse();
    }

    public Response getResponse() {
        return response;
    }
}
