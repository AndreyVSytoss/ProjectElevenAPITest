package tests;

import model.User;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GitHubAccTest extends TestBase {

    @Test
        public void getUserInfo(){
            User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
            assertThat(userInfo.getCompany()).isEqualTo(null);
            assertThat(userInfo.getType()).isEqualTo("User");
        }

    @Test
    public void getUserInfo1(){
        User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
        assertThat(userInfo.getCompany()).isEqualTo(null);
        assertThat(userInfo.getType()).isEqualTo("User");
    }
    @Test
    public void getUserInfo2(){
        User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
        assertThat(userInfo.getCompany()).isEqualTo(null);
        assertThat(userInfo.getType()).isEqualTo("User");
    }
    @Test
    public void getUserInfo3(){
        User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
        assertThat(userInfo.getCompany()).isEqualTo(null);
        assertThat(userInfo.getType()).isEqualTo("User");
    }
    @Test
    public void getUserInfo4(){
        User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
        assertThat(userInfo.getCompany()).isEqualTo(null);
        assertThat(userInfo.getType()).isEqualTo("User");
    }
    @Test
    public void getUserInfo5(){
        User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
        assertThat(userInfo.getCompany()).isEqualTo(null);
        assertThat(userInfo.getType()).isEqualTo("User");
    }
    @Test
    public void getUserInfo6(){
        User userInfo = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
        assertThat(userInfo.getCompany()).isEqualTo(null);
        assertThat(userInfo.getType()).isEqualTo("User");
    }

//@Test
//public void getCompareUsers(){
//    User expected = new User();
//    expected.setId(13361572);
//    User actual = manager.getAccountHelper().getUserInfo("AndreyVSytoss");
//    assertThat(actual).isEqualToIgnoringGivenFields(expected, "updatedAt");
//    }
}
