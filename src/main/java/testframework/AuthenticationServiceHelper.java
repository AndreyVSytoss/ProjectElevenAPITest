package testframework;

import io.restassured.response.Response;
import model.AuthCreateUserResponse;
import model.AuthUser;

import static io.restassured.RestAssured.given;


public class AuthenticationServiceHelper extends BaseHelper {
    public AuthenticationServiceHelper(HelperManager manager) {super(manager);}

    public Response getJWTtoken(AuthUser auth) {
        return given()
                .contentType("application/json")
                .body(auth)
                .when()
                .post(manager.getProperty("authPath") + "login")
                .then()
                .extract().response();
    }

    public Response createAuthUser(AuthUser auth, String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(auth)
                .when()
                .post(manager.getProperty("authPath") + "create")
                .then()
                .extract().response();
    }

    public Response logout(String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .post(manager.getProperty("authPath") + "logout")
                .then()
                .extract().response();
    }

    public Response deleteAuthUser(String userId, String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(manager.getProperty("authPath") + "user/" + userId)
                .then()
                .extract().response();
    }

    public Response changePassword(String userId, String password, String token) {
        return given()
                .contentType("application/json")
                .body(password)
                .header("Authorization", "Bearer " + token)
                .when()
                .patch(manager.getProperty("authPath") + "user/" + userId + "/password")
                .then()
                .extract().response();
    }



    //**************************************************************************//

    public AuthUser generateAuthUser(){
        return new AuthUser()
                .setUsername(generateRandomString())
                .setPassword(generateRandomString());
    }
}
