package by.yr.api;

import com.google.gson.JsonObject;
import io.restassured.response.Response;

public class LoginService {
    private Response lastResponse;

    public Response sendLoginRequest(String email, String password, boolean remember) {
        ApiService apiService = new ApiService();
        String body = buildLoginBody(email, password, remember);
        lastResponse = apiService.doPost(ApiConstants.LOGIN_URL, body);
        return lastResponse;
    }

    private String buildLoginBody(String email, String password, boolean remember) {
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);
        json.addProperty("remember", remember);
        return json.toString();
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

    public int getStatusCode() {
        return lastResponse.getStatusCode();
    }

    public String getToken() {
        return lastResponse.path("token");
    }
}
