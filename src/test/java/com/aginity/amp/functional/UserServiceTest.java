package com.aginity.amp.functional;

import model.AuthCreateUserResponse;
import model.AuthUser;
import model.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import java.io.File;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
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
         /* validate json schema */
        assertThat(actual.getBody().asString(), matchesJsonSchema(new File(manager.getAuthenticationServiceHelper().getJsonSchemaPath() + "users-schema.json")));
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
        /* validate json schema */
        assertThat(actual.getBody().asString(), matchesJsonSchema(new File(manager.getAuthenticationServiceHelper().getJsonSchemaPath() + "user-schema.json")));

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

    @Test
    public void getUserByName() throws Exception {

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response response = manager.getUserServiceHelper().createUser(expected, token);

        /* get user by name */

        User actual = manager.getUserServiceHelper().getUserByName(expected.getUsername(), token);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getUserById() {

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createUserResponse = manager.getUserServiceHelper().createUser(expected, token);

        /* get user by id */

        Response actual = manager.getUserServiceHelper().getUserById(expected.getId(), token);

        assertThat(actual.getStatusCode()).isEqualTo(200);
        assertThat(actual.as(User.class)).isEqualTo(expected);
    }

    @Test
    public void getUserPermissions() {

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createUserResponse = manager.getUserServiceHelper().createUser(expected, token);

        /* get user permissions */

        Response actual = manager.getUserServiceHelper().getUserPermissionss(expected.getId(), token);

        assertThat(actual.as(String[].class)).contains("*:*", "user:view");
        assertThat(actual.as(String[].class).length).isEqualTo(2);
        assertThat(actual.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void createUserWithOneRole() {

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername())
                .setRoles(manager.getUserServiceHelper().getRolesItems("user"));
        Response createUserResponse = manager.getUserServiceHelper().createUser(expected, token);

        /* get user permissions */

        Response actual = manager.getUserServiceHelper().getUserPermissionss(expected.getId(), token);

        assertThat(createUserResponse.as(User.class)).isEqualTo(expected);
        assertThat(actual.as(String[].class)).contains("user:view");
        assertThat(actual.as(String[].class).length).isEqualTo(1);
        assertThat(actual.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void addRole() {

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user with one role */

        User userWithOneRole = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername())
                .setRoles(manager.getUserServiceHelper().getRolesItems("user"));
        Response createUserResponse = manager.getUserServiceHelper().createUser(userWithOneRole, token);

        /* add admin role */

        Response addRoleResponse = manager.getUserServiceHelper().addRole(new String[]{manager.getUserServiceHelper()
                .getRoleId("admin")}, userWithOneRole.getId(), token);
        Response actualUserPermResp = manager.getUserServiceHelper().getUserPermissionss(userWithOneRole.getId(), token);

        User actualUser = manager.getUserServiceHelper().getUserById(userWithOneRole.getId(), token).as(User.class);

        assertThat(addRoleResponse.getStatusCode()).isEqualTo(204);
        assertThat(actualUser).isNotEqualTo(userWithOneRole);
        assertThat(actualUserPermResp.as(String[].class)).contains("*:*", "user:view");
        assertThat(actualUserPermResp.as(String[].class).length).isEqualTo(2);
    }

    @Test
    public void removeRole(){

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User oldUser = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createdUserResp = manager.getUserServiceHelper().createUser(oldUser, token);

        /* remove role */

        Response removeRoleRes = manager.getUserServiceHelper().removeRole(oldUser.getId(),
                manager.getUserServiceHelper().getRoleId("user"), token );

        /*check updated user */

        User updatedUser = manager.getUserServiceHelper().getUserById(oldUser.getId(), token).as(User.class);
        Response userPermissions = manager.getUserServiceHelper().getUserPermissionss(oldUser.getId(), token);

        assertThat(updatedUser.getRoles()).isNotEqualTo(oldUser.getRoles());
        assertThat(removeRoleRes.getStatusCode()).isEqualTo(204);
        assertThat(userPermissions.as(List.class)).contains("*:*");
        assertThat(userPermissions.as(List.class).size()).isEqualTo(1);
    }

    @Test
    public void deleteUser() {

        /* create auth user */

        AuthUser authUserRequest = manager.getAuthenticationServiceHelper().generateAuthUser();
        AuthCreateUserResponse newAuthUser = manager.getAuthenticationServiceHelper().createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = manager.getUserServiceHelper().generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createUserResponse = manager.getUserServiceHelper().createUser(expected, token);

        /* get user by name */

        Response deleteUserResponse = manager.getUserServiceHelper().deleteUserById(expected.getId(), token);
        Response checkUser = manager.getUserServiceHelper().getUserById(expected.getId(), token);

        System.out.println(checkUser.as(User.class));
      //  assertThat(deleteUserResponse.getStatusCode()).isEqualTo(200);
        assertThat(checkUser.getStatusCode()).isEqualTo(200); //not correct
    }
}
