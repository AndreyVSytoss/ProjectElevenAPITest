package testframework;

import model.User;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class AccountHelper extends BaseHelper {

    public AccountHelper(HelperManager manager) {super(manager);}

    public User getUserInfo(String userName) {
        return
                given()
                .when()
                .get(manager.getProperty("userPath") + userName)
                .then()
                .statusCode(200)
                .log().body()
                .assertThat()
                .body(matchesJsonSchema(new File (System.getProperty("user.dir") + File.separator + "user-schema.json")))
                .extract()
                .as(User.class);
    }
}
