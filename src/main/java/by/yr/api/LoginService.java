package by.yr.api;

import io.restassured.response.Response;

public class LoginService {

    public Response sendLoginRequest(String email, String password, boolean remember) {
        ApiService apiService = new ApiService();
        String body = "{"
                + (email != null ? "\"email\":\"" + email + "\"," : "")
                + (password != null ? "\"password\":\"" + password + "\"," : "")
                + "\"remember\":" + remember
                + "}";

        return apiService.doPost(ApiConstants.LOGIN_URL, body);

    }
}