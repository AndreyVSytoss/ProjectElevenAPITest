package testframework;

import model.AuthRequest;

import static io.restassured.RestAssured.given;


public class AuthenticationServiceHelper extends BaseHelper {
    public AuthenticationServiceHelper(HelperManager manager) {super(manager);}

    public String getJWTtoken(AuthRequest auth) {
        return
                given()
                        .body(auth)
                        .when()
                        .post(manager.getProperty("authenticationPath"))
                        .then()
                        .statusCode(200)
                        .extract().header("token");
    }
}