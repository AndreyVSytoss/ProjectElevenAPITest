package testframework;

import model.RolesItem;
import model.User;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseHelper {

    final static String JSON_SCHEMA_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "main" + File.separator + "java" + File.separator + "jsonSchema" + File.separator;

    final static List<RolesItem> rolesItems = Arrays.asList(new RolesItem()
                    .setId("3b83146d-dc59-45f5-9556-58a35e5ddab1").setName("Admin"),
            new RolesItem()
                    .setId("477f7269-6054-4e87-9b42-306c715e97e9").setName("User"));

    protected HelperManager manager;

    public BaseHelper(HelperManager manager) {this.manager = manager;}

    public static String generateRandomString() {
        Random rnd = new Random();
        return "test" + rnd.nextInt();
    }

    public String getJsonSchemaPath() {
        return JSON_SCHEMA_PATH;
    }
}
