package testframework;

import model.RolesItem;
import model.User;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UserServiceHelper extends BaseHelper{

    public UserServiceHelper(HelperManager manager) {super(manager);}

    public List<User> getAllUsers(String token) {
             return given()
                        .log().all()
                        .when()
                        .header("Authorization", "Bearer " + token)
                        .get(manager.getProperty("userPath"))
                        .then()
                        .statusCode(200)
                        .extract().body().as(List.class);
    }

    public User createUser(User createUserRequest, String token) {
        return given()
                .log().all()
                .contentType("application/json")
                .body(createUserRequest)
                .header("Authorization", "Bearer " + token)
                .when()
                .post(manager.getProperty("userPath"))
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(User.class);
    }

    /*----------------------------------------------------------------------------------------------*/

    private static List<RolesItem> rolesItems = Arrays.asList(new RolesItem()
                    .setId("3b83146d-dc59-45f5-9556-58a35e5ddab1").setName("Admin"),
            new RolesItem()
                    .setId("477f7269-6054-4e87-9b42-306c715e97e9").setName("User"));

    public User generateUserData(String id){
        return new User()
                .setId(id)
                .setEmail(generateRandomString())
                .setUsername(generateRandomString())
                .setOrganization(generateRandomString())
                .setFullName(generateRandomString() + " " + generateRandomString())
                .setRoles(rolesItems);
    }
}