package com.aginity.amp.functional;

import model.AuthUser;
import model.User;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends TestBase{
    @Test
    public void getUsers(){

        /* get user token */
        AuthUser tokenRequest = new AuthUser().setUsername("user").setPassword("user");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest);

        /* get all users */
        List<User> response = manager.getUserServiceHelper().getAllUsers(token);

        assertThat(response).extracting("full_name").contains("Admin Admin", "User User");
    }

    @Test
    public void createRandomUser(){

        /* get user token */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest);

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        String id = manager.getAuthenticationServiceHelper().createUserAndGetId(authUserRequest, token);

        /* create random user */

        User expected = manager.getUserServiceHelper().generateUserData(id);
        User actual = manager.getUserServiceHelper().createUser(expected, token);
        System.out.println(actual);

        //assertThat(response).extracting("full_name").contains("Admin Admin", "User User");
    }

}
