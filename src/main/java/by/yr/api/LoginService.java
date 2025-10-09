package by.yr.api;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class LoginService {
    private Response lastResponse;

    @Step(" Send Login Request and Get Response")
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

    @Step("Get errors.email from Response")
    public String getEmailError() {
        return lastResponse.path("errors.email[0]");
    }

    @Step("Get errors.password from Response")
    public String getPasswordError() {
        return lastResponse.path("errors.password[0]");
    }

    @Step("Get message from Response")
    public String getMessage() {
        return lastResponse.path("message");
    }

    @Step("Get Status Code from Response")
    public int getStatusCode() {
        return lastResponse.getStatusCode();
    }

    @Step("Get Token from Response")
    public String getToken() {
        return lastResponse.path("token");
    }
}
