package com.aginity.amp.functional;

import model.AuthCreateUserResponse;
import model.AuthUser;
import model.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import java.lang.reflect.Array;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends TestBase{
    private String token;

    @BeforeClass
        private void getToken() {
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest).getBody().asString();
    }


    @Test
    public void getUsers(){

        /* get user token */
        AuthUser tokenRequest = new AuthUser().setUsername("user").setPassword("user");
        String userToken = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest).getBody().asString();

        /* get all users */
        Response actual = manager.getUserServiceHelper().getAllUsers(userToken);

        assertThat(actual.getStatusCode()).isEqualTo(200);
        assertThat(actual.as(List.class)).extracting("full_name").contains("Admin Admin", "User User");
    }

    @Test
    public void createRandomUser(){

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response actual = manager.getUserServiceHelper().createUser(expected, token);

        assertThat(actual.getStatusCode()).isEqualTo(201);
        assertThat(actual.as(User.class)).isEqualTo(expected);
    }

    @Test
    public void updateUser(){

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User oldUser = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response oldUserResp = manager.getUserServiceHelper().createUser(oldUser, token);

        /* update 'Organization' and 'Email' fields */

        User updatedUser = new User().setOrganization("test_organization").setEmail("test_email");
        User expected = oldUser.setOrganization("test_organization").setEmail("test_email");
        Response actual = manager.getUserServiceHelper().updateUser(updatedUser, oldUser.getId(), token);

        assertThat(actual.getStatusCode()).isEqualTo(200);
        assertThat(actual.as(User.class)).isEqualTo(expected);
    }
}
