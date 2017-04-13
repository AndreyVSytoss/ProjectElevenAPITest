package testframework;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import model.AuthCreateUserResponse;
import model.AuthUser;

import javax.security.cert.Certificate;
import java.util.Random;

import static io.restassured.RestAssured.certificate;
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
                        .log().all()
                        .statusCode(200)
                        .extract().body().asString();
    }

    public AuthCreateUserResponse createValidAuthUser(AuthUser auth) {
        return
                given()
                        .contentType("application/json")
                        .body(auth)
                        .when()
                        .post(manager.getProperty("authCreateUserPath"))
                        .then()
                        .log().all()
                        .statusCode(201)
                        .extract().as(AuthCreateUserResponse.class);
    }

    public String createUserAndGetId(AuthUser auth) {
        return
                given()
                        .contentType("application/json")
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

    private static String generateRandomString() {
        Random rnd = new Random();
            return "test" + rnd.nextInt();
        }
}
