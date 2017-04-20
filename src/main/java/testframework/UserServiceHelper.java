package testframework;

import io.restassured.response.Response;
import model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class UserServiceHelper extends BaseHelper{

    public UserServiceHelper(HelperManager manager) {super(manager);}

    public Response getAllUsers(String token) {
             return given()
                        .log().all()
                        .when()
                        .header("Authorization", "Bearer " + token)
                        .get(manager.getProperty("userPath"))
                        .then()
                        .log().all()
                        .extract().response();
    }

    public Response createUser(User createUserRequest, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(createUserRequest)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(manager.getProperty("userPath"))
                .then()
                .log().all()
                .extract().response();
    }

    public Response updateUser(User user, String id, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(user)
                .header("Authorization", "Bearer " + token)
                .when()
                .patch(manager.getProperty("userPath") + id)
                .then()
                .extract().response();
    }

    public User getUserByName(String name, String token) throws Exception{

        User[] aUsers =  given()
                .log().all()
                .when()
                .header("Authorization", "Bearer " + token)
                .get(manager.getProperty("userPath"))
                .then()
                .extract().body().as(User[].class);

        List<User> users =  Arrays.asList(aUsers);

        List<User> filteredUsers = users.stream().filter(u -> u.getUsername().equals(name)).collect(Collectors.toList());
        if (filteredUsers.size() > 1) {
            throw new Exception("More then one user with ussername " +  name + " are found!") ;
        }
        if (filteredUsers.size() == 0) {
            throw new Exception("User with ussername " +  name + " doesn't exist!") ;
        }
        return filteredUsers.get(0);
    }

    public Response deleteUserById(String id, String token){
        return given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(manager.getProperty("userPath") + id)
                .then()
                .extract().response();
    }

    public Response getUserById(String id, String token){
        return given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(manager.getProperty("userPath") + id)
                .then()
                .extract().response();
    }

    public Response getUserPermissionss(String id, String token){
        return given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(manager.getProperty("userPath") + id + "/permissions")
                .then()
                .extract().response();
    }

    public Response addRole(String[] roleIds, String userId, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(roleIds)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(manager.getProperty("userPath")+ userId + "/roles/")
                .then()
                .extract().response();
    }

    public Response removeRole(String userId, String roleId, String token){
        return given()
                .log().all()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(manager.getProperty("userPath") + userId + "/roles/" + roleId)
                .then()
                .extract().response();
    }

    /*----------------------------------------------------------------------------------------------*/



    public User generateUserData(String id, String userName){
        return new User()
                .setId(id)
                .setEmail(generateRandomString())
                .setUsername(userName)
                .setOrganization(generateRandomString())
                .setFullName(generateRandomString() + " " + generateRandomString())
                .setRoles(rolesItems);
    }
}