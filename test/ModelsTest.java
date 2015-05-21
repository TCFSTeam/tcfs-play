/**
 * Created by test on 10/12/14.
 */

import models.UserTCFS;
import org.junit.*;
import play.test.WithApplication;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void createAndRetrieveUser() {
        new UserTCFS("bob@gmail.com", "Bob", "secret").save();
        UserTCFS bob = UserTCFS.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
    }

    @Test
    public void tryAuthenticateUser() {
        new UserTCFS("bob@gmail.com", "Bob", "secret").save();

        assertNotNull(UserTCFS.authenticate("bob@gmail.com", "secret"));
        assertNull(UserTCFS.authenticate("bob@gmail.com", "badpassword"));
        assertNull(UserTCFS.authenticate("tom@gmail.com", "secret"));
    }
}