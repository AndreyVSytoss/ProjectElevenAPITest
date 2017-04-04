package tests;

import model.User;
import org.junit.Test;
import static  org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

public class GitHubAccTest extends TestBase {

@Test
    public void getUserInfo(){
        User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
        assertEquals(userInfo.getCompany(), null);
        assertEquals(userInfo.getType(), "User");
    }

@Test
public void getCompareUsers(){
    User expected = new User();
    expected.setId(13361572);
    User actual = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
    assertThat(actual).isEqualToIgnoringGivenFields(expected, "updatedAt");
    }
}
