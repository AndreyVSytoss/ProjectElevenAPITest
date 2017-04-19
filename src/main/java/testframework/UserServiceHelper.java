package testframework;

import io.restassured.response.Response;
import model.User;

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