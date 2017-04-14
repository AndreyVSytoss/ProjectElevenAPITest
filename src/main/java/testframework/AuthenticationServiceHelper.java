package testframework;

import model.AuthCreateUserResponse;
import model.AuthUser;

import static io.restassured.RestAssured.given;


public class AuthenticationServiceHelper extends BaseHelper {
    public AuthenticationServiceHelper(HelperManager manager) {super(manager);}

    public String getJWTtoken(AuthUser auth) {
        return
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(auth)
                        .when()
                        .post(manager.getProperty("authLogin"))
                        .then()
                        .statusCode(200)
                        .log().ifValidationFails()
                        .extract().body().asString();
    }

    public AuthCreateUserResponse createValidAuthUser(AuthUser auth, String token) {
        return
                given()
                        .log().all()
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .body(auth)
                        .when()
                        .post(manager.getProperty("authCreateUserPath"))
                        .then()
                        .log().ifValidationFails()
                        .statusCode(201)
                        .extract().as(AuthCreateUserResponse.class);
    }

    public String createUserAndGetId(AuthUser auth, String token) {
        return
                given()
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .body(auth)
                        .when()
                        .post(manager.getProperty("authCreateUserPath"))
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract().body().jsonPath().getString("id");
    }

    //**************************************************************************//

    public AuthUser generateAuthUser(){
        return new AuthUser()
                .setUsername(generateRandomString())
                .setPassword(generateRandomString());
    }


}
