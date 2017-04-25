package utils;

import java.util.Properties;

public class HelperManager {
    private Properties properties;
    private UserServiceHelper userServiceHelper;
    private AuthenticationServiceHelper authenticationService;


    public HelperManager(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public UserServiceHelper getUserServiceHelper() {
        if (userServiceHelper == null) {
            userServiceHelper = new UserServiceHelper(this);
        }
        return userServiceHelper;
    }

    public AuthenticationServiceHelper getAuthenticationServiceHelper() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationServiceHelper(this);
        }
        return authenticationService;
    }
}
