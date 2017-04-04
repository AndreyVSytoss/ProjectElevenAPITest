package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public AuthRequest setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}
