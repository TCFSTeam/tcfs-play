package unit;

import com.avaje.ebean.Ebean;
import models.UserTCFS;
import org.junit.Test;
import play.libs.Yaml;
import play.test.Helpers;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserTest extends CommonTest {

    @Test
    public void findById() {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");
                if (UserTCFS.findAll().isEmpty())
                    Ebean.save(all.get("users"));
                String email = "joe@tcfs.com";
                UserTCFS user = UserTCFS.findByEmail(email);
                assertEquals(user.email, email);
                String fakeEmail = "blabla@tcfs.com";
                UserTCFS fakeUser = UserTCFS.findByEmail(fakeEmail);
                assertNull(fakeUser);
            }
        });
    }

    @Test
    public void createUser() {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                String email = "someuser@tcfs.com";
                String name = "New user";
                String password = "qwerty";
                UserTCFS user = new UserTCFS(email, name, password);
                Ebean.save(user);
                UserTCFS loadedUser = UserTCFS.findByEmail(email);
                assertEquals(loadedUser.email, email);
                assertEquals(loadedUser.name, name);
                assertEquals(loadedUser.password, password);
            }
        });
    }
}
