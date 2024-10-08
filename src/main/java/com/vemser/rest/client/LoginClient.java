package com.vemser.rest.client;

import com.vemser.rest.model.request.login.LoginRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient {

    private final String LOGIN = "/login";

    public Response login(LoginRequest credentials) {
        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .body(credentials)
                        .when()
                        .post(LOGIN)
                ;
    }
}