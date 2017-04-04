package testframework;

import java.util.Properties;

public class HelperManager {
    private Properties properties;
    private AccountHelper accountHelper;
    private UserServiceHelper userServiceHelper;
    private AuthenticationServiceHelper authenticationService;


    public HelperManager(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public AccountHelper getAccountHelper() {
        if (accountHelper == null) {
            accountHelper = new AccountHelper(this);
        }
        return accountHelper;
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