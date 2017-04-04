package tests;

import model.AuthRequest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTest extends TestBase{
    @Test
    public void getUserInfo(){
        AuthRequest request = new AuthRequest().setLogin("user").setLogin("password");
        String token = manager.getAuthenticationServiceHelper().getJWTtoken(request);
        assertThat(token).isNotEmpty();
    }
}
