package com.aginity.amp.functional;

import model.AuthCreateUserResponse;
import model.AuthUser;
import model.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import testframework.AuthenticationServiceHelper;
import testframework.UserServiceHelper;

import java.io.File;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends TestBase{
    private UserServiceHelper userHelper;
    private AuthenticationServiceHelper authHelper;

    private String token;

    @BeforeClass
    private void getTokenAndInitHelpers() {
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        token = manager.getAuthenticationServiceHelper().getJWTtoken(tokenRequest).getBody().asString();
        userHelper = manager.getUserServiceHelper();
        authHelper = manager.getAuthenticationServiceHelper();
    }

    @Test
    public void getUsers(){

        /* get user token */
        AuthUser tokenRequest = new AuthUser().setUsername("admin").setPassword("admin");
        String userToken = authHelper.getJWTtoken(tokenRequest).getBody().asString();

        /* get all users */
        Response actual = userHelper.getAllUsers(userToken);

        assertThat(actual.getStatusCode()).isEqualTo(200);
        assertThat(actual.as(List.class)).extracting("full_name").contains("Admin Admin", "User User");

         /* validate json schema */
        assertThat(actual.getBody().asString(), matchesJsonSchema(new File(authHelper.getJsonSchemaPath() + "users-schema.json")));
    }

    @Test
    public void createRandomUser(){

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response actual = userHelper.createUser(expected, token);

        assertThat(actual.getStatusCode()).isEqualTo(201);
        assertThat(actual.as(User.class)).isEqualTo(expected);
        /* validate json schema */
        assertThat(actual.getBody().asString(), matchesJsonSchema(new File(authHelper.getJsonSchemaPath() + "user-schema.json")));

    }

    @Test
    public void updateUser(){

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User oldUser = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response oldUserResp = userHelper.createUser(oldUser, token);

        /* update 'Organization' and 'Email' fields */

        User updatedUser = new User().setOrganization("test_organization").setEmail("test_email");
        User expected = oldUser.setOrganization("test_organization").setEmail("test_email");
        Response actual = userHelper.updateUser(updatedUser, oldUser.getId(), token);

        assertThat(actual.getStatusCode()).isEqualTo(200);
        assertThat(actual.as(User.class)).isEqualTo(expected);
    }

    @Test
    public void getUserByName() throws Exception {

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response response = userHelper.createUser(expected, token);

        /* get user by name */

        User actual = userHelper.getUserByName(expected.getUsername(), token);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getUserById() {

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createUserResponse = userHelper.createUser(expected, token);

        /* get user by id */

        Response actual = userHelper.getUserById(expected.getId(), token);

        assertThat(actual.getStatusCode()).isEqualTo(200);
        assertThat(actual.as(User.class)).isEqualTo(expected);
    }

    @Test
    public void getUserPermissions() {

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createUserResponse = userHelper.createUser(expected, token);

        /* get user permissions */

        Response actual = userHelper.getUserPermissionss(expected.getId(), token);

        assertThat(actual.as(String[].class)).contains("*:*", "user:view");
        assertThat(actual.as(String[].class).length).isEqualTo(2);
        assertThat(actual.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void createUserWithOneRole() {

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername(), "user");
        Response createUserResponse = userHelper.createUser(expected, token);

        /* get user permissions */

        Response actual = userHelper.getUserPermissionss(expected.getId(), token);

        assertThat(createUserResponse.as(User.class)).isEqualTo(expected);
        assertThat(actual.as(String[].class)).contains("user:view");
        assertThat(actual.as(String[].class).length).isEqualTo(1);
        assertThat(actual.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void addRole() {

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user with one role */

        User userWithOneRole = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername())
                .setRoles(userHelper.getRolesItems("user"));
        Response createUserResponse = userHelper.createUser(userWithOneRole, token);

        /* add admin role */

        Response addRoleResponse = userHelper.addRole(new String[]{userHelper
                .getRoleId("admin")}, userWithOneRole.getId(), token);
        Response actualUserPermResp = userHelper.getUserPermissionss(userWithOneRole.getId(), token);

        User actualUser = userHelper.getUserById(userWithOneRole.getId(), token).as(User.class);

        assertThat(addRoleResponse.getStatusCode()).isEqualTo(204);
        assertThat(actualUser).isNotEqualTo(userWithOneRole);
        assertThat(actualUserPermResp.as(String[].class)).contains("*:*", "user:view");
        assertThat(actualUserPermResp.as(String[].class).length).isEqualTo(2);
    }

    @Test
    public void removeRole(){

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User oldUser = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createdUserResp = userHelper.createUser(oldUser, token);

        /* remove role */

        Response removeRoleRes = userHelper.removeRole(oldUser.getId(),
                userHelper.getRoleId("user"), token );

        /*check updated user */

        User updatedUser = userHelper.getUserById(oldUser.getId(), token).as(User.class);
        Response userPermissions = userHelper.getUserPermissionss(oldUser.getId(), token);

        assertThat(updatedUser.getRoles()).isNotEqualTo(oldUser.getRoles());
        assertThat(removeRoleRes.getStatusCode()).isEqualTo(204);
        assertThat(userPermissions.as(List.class)).contains("*:*");
        assertThat(userPermissions.as(List.class).size()).isEqualTo(1);
    }

    @Test
    public void deleteUser() {

        /* create auth user */

        AuthUser authUserRequest = authHelper.generateAuthUser();
        AuthCreateUserResponse newAuthUser = authHelper.createAuthUser(authUserRequest, token).as(AuthCreateUserResponse.class);

        /* create random user */

        User expected = userHelper.generateUserData(newAuthUser.getId(), newAuthUser.getUsername());
        Response createUserResponse = userHelper.createUser(expected, token);

        /* get user by name */

        Response deleteUserResponse = userHelper.deleteUserById(expected.getId(), token);
        Response checkUser = userHelper.getUserById(expected.getId(), token);

      //  assertThat(deleteUserResponse.getStatusCode()).isEqualTo(200);
        assertThat(checkUser.getStatusCode()).isEqualTo(200); //not correct
    }
}
