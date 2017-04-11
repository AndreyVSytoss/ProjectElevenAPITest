package com.aginity.amp.functional;

import model.AuthRequest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTest extends TestBase{
    @Test
    public void getAuthToken(){
        AuthRequest request = new AuthRequest().setLogin("user").setLogin("password");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(request);
        assertThat(token).isNotEmpty();
    }
}
