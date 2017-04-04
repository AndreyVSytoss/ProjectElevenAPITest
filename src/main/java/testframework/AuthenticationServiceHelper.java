package testframework;

import model.AuthRequest;
import model.User;

import java.io.File;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

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
