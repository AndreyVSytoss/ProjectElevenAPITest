package com.aginity.amp.functional;

import io.restassured.response.Response;
import model.AuthCreateUserResponse;
import model.AuthUser;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTest extends TestBase{
    @Test
    public void getAuthToken(){
        AuthUser request = new AuthUser().setUsername("user").setPassword("user");
        Response response = manager.getAuthenticationServiceHelper().getJWTtoken(request);
        String token = response.getBody().asString();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(token).isNotEmpty();
    }

    @Test
    public void createAuthUser(){

        /* get admin token */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest).getBody().asString();

        /* create random user */
        AuthUser request = manager.getAuthenticationServiceHelper().generateAuthUser();
        Response response = manager.getAuthenticationServiceHelper().createAuthUser(request, token);
        AuthCreateUserResponse actual = response.as(AuthCreateUserResponse.class);

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(actual.getUsername()).isEqualTo(request.getUsername());
        assertThat(actual.getId()).isNotEmpty();
    }
}
