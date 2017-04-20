package com.aginity.amp.functional;

import io.restassured.response.Response;
import model.AuthCreateUserResponse;
import model.AuthUser;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTest extends TestBase{

    @Test
    public void getAuthTokenTest(){
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

    @Test
    public void logout(){

        /* user login */
        AuthUser tokenRequest = new AuthUser().setUsername("user").setPassword("user");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest).getBody().asString();

        /* user logout */
        Response response = manager.getAuthenticationServiceHelper().logout(token);

        assertThat(response.statusCode()).isEqualTo(501);
        assertThat(response.getBody().jsonPath().getInt("code")).isEqualTo(501);
        assertThat(response.getBody().jsonPath().getString("message")).isEqualTo("HTTP 501 Not Implemented");
    }

    @Test
    public void deleteAuthUser(){

        /* user login */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest).getBody().asString();

        /* create random user */
        AuthUser createUserReq = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse createUserResp = manager.getAuthenticationServiceHelper().createAuthUser(createUserReq, token).as(AuthCreateUserResponse.class);

        /* delete Auth user */
        Response response = manager.getAuthenticationServiceHelper().deleteAuthUser(createUserResp.getId(), token);

        assertThat(response.statusCode()).isEqualTo(204);
    }

    @Test
    public void changeAuthUserPassword(){

        /* user login */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest).getBody().asString();

        /* create random user */
        AuthUser createUserReq = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse createUserResp = manager.getAuthenticationServiceHelper().createAuthUser(createUserReq, token).as(AuthCreateUserResponse.class);

        /* login with newly created user*/
        AuthUser newTokenRequest = new AuthUser().setUsername(createUserReq.getUsername()).setPassword(createUserReq.getPassword());
        String newToken = manager.getAuthenticationServiceHelper().getJWTtoken(newTokenRequest).getBody().asString();

        /* change Auth user password*/
        Response response = manager.getAuthenticationServiceHelper().changePassword(createUserResp.getId(), "new_password", newToken);

        assertThat(response.statusCode()).isEqualTo(204);
    }
}
