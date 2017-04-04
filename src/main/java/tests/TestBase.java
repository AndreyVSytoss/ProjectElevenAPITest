package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import testframework.HelperManager;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;


public class TestBase {
    static HelperManager manager;

    @BeforeClass
    public static void setUp() throws Exception {
        String configFile = System.getProperty("configFile", "application.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(new File(configFile)));
        manager = new HelperManager(properties);
        RestAssured.baseURI  = manager.getProperty("baseUrl");
    }
}
