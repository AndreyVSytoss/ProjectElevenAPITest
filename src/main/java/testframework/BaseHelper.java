package testframework;

import java.io.File;
import java.util.Random;

public class BaseHelper {

    final static String JSON_SCHEMA_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "main" + File.separator + "java" + File.separator + "jsonScheme" + File.separator;

    protected HelperManager manager;

    public BaseHelper(HelperManager manager) {this.manager = manager;}

    public static String generateRandomString() {
        Random rnd = new Random();
        return "test" + rnd.nextInt();
    }
}
