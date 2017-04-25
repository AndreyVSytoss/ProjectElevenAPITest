package com.aginity.amp.functional;

import io.restassured.response.Response;
import model.AuthCreateUserResponse;
import model.AuthUser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.AuthenticationServiceHelper;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthenticationServiceTest extends TestBase{

    private AuthenticationServiceHelper authHelper;

    @BeforeClass
    private void initHelpers() {
        authHelper = manager.getAuthenticationServiceHelper();
    }

    @Test
    public void getAuthTokenTest(){
        AuthUser request = new AuthUser().setUsername("user").setPassword("user");
        Response response = authHelper.getJWTtoken(request);
        String token = response.getBody().asString();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(token).isNotEmpty();
    }

    @Test
    public void createAuthUser(){

        /* get admin token */

        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = authHelper.getJWTtoken(tokenRequest).getBody().asString();

        /* create random user */
        AuthUser request = authHelper.generateAuthUser();
        Response response = authHelper.createAuthUser(request, token);

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.as(AuthCreateUserResponse.class).getUsername()).isEqualTo(request.getUsername());
        assertThat(response.as(AuthCreateUserResponse.class).getId()).isNotEmpty();
    }

    @Test
    public void logout(){

        /* user login */
        AuthUser tokenRequest = new AuthUser().setUsername("user").setPassword("user");
        String token = authHelper.getJWTtoken(tokenRequest).getBody().asString();

        /* user logout */
        Response response = authHelper.logout(token);

        assertThat(response.statusCode()).isEqualTo(501);
        assertThat(response.getBody().jsonPath().getInt("code")).isEqualTo(501);
        assertThat(response.getBody().jsonPath().getString("message")).isEqualTo("HTTP 501 Not Implemented");
    }

    @Test
    public void deleteAuthUser(){

        /* user login */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = authHelper.getJWTtoken(tokenRequest).getBody().asString();

        /* create random user */
        AuthUser createUserReq = authHelper.generateAuthUser();
        AuthCreateUserResponse createUserResp = authHelper.createAuthUser(createUserReq, token).as(AuthCreateUserResponse.class);

        /* delete Auth user */
        Response response = authHelper.deleteAuthUser(createUserResp.getId(), token);

        assertThat(response.statusCode()).isEqualTo(204);
    }

    @Test
    public void changeAuthUserPassword(){

        /* user login */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = authHelper.getJWTtoken(tokenRequest).getBody().asString();

        /* create random user */
        AuthUser createUserReq = authHelper.generateAuthUser();
        AuthCreateUserResponse createUserResp = authHelper.createAuthUser(createUserReq, token).as(AuthCreateUserResponse.class);

        /* login with newly created user*/
        AuthUser newTokenRequest = new AuthUser().setUsername(createUserReq.getUsername()).setPassword(createUserReq.getPassword());
        String newToken = authHelper.getJWTtoken(newTokenRequest).getBody().asString();

        /* change Auth user password*/
        Response response = authHelper.changePassword(createUserResp.getId(), "new_password", newToken);

        assertThat(response.statusCode()).isEqualTo(204);
    }
}
