package by.yr.api;

import io.restassured.response.Response;

public class LoginService {
    private Response lastResponse;

    public Response sendLoginRequest(String email, String password, boolean remember) {
        ApiService apiService = new ApiService();
        String body = "{"
                + (email != null ? "\"email\":\"" + email + "\"," : "")
                + (password != null ? "\"password\":\"" + password + "\"," : "")
                + "\"remember\":" + remember
                + "}";

        lastResponse= apiService.doPost(ApiConstants.LOGIN_URL, body);
        return lastResponse;
    }

    public String getEmailError() {
        return lastResponse.path("errors.email[0]");
    }

    public String getPasswordError() {
        return lastResponse.path("errors.password[0]");
    }

    public String getMessage() {
        return lastResponse.path("message");
    }

    public int getStatusCode(){
        return lastResponse.getStatusCode();
    }

    public String getToken(){
        return lastResponse.path("token");
    }
}
