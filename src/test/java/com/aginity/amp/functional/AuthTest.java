package com.aginity.amp.functional;

import model.AuthCreateUserResponse;
import model.AuthUser;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTest extends TestBase{
    @Test
    public void getAuthToken(){
        AuthUser request = new AuthUser().setUsername("user").setPassword("user");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(request);

        assertThat(token).isNotEmpty();
    }

    @Test
    public void createAuthUser(){

        /* get admin token */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest);

        /* create random user */
        AuthUser request = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse response = manager.getAuthenticationServiceHelper().createValidAuthUser(request, token);

        assertThat(response.getUsername()).isEqualTo(request.getUsername());
        assertThat(response.getId()).isNotEmpty();
    }

    @Test
    public void checkId(){
        AuthUser request = manager.getAuthenticationServiceHelper().generateAuthUser();
        String id = manager.getAuthenticationServiceHelper().createUserAndGetId(request);
        assertThat(id).isNotEmpty();
    }
}
