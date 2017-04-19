package testframework;

import io.restassured.response.Response;
import model.AuthCreateUserResponse;
import model.AuthUser;

import static io.restassured.RestAssured.given;


public class AuthenticationServiceHelper extends BaseHelper {
    public AuthenticationServiceHelper(HelperManager manager) {super(manager);}

    public Response getJWTtoken(AuthUser auth) {
        return
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(auth)
                        .when()
                        .post(manager.getProperty("authLogin"))
                        .then()
                        .extract().response();
    }

    public Response createAuthUser(AuthUser auth, String token) {
        return
                given()
                        .log().all()
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .body(auth)
                        .when()
                        .post(manager.getProperty("authCreateUserPath"))
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
