package unit;

import com.avaje.ebean.Ebean;
import models.UserTCFS;
import org.junit.Test;
import models.UserTCFS.MemberType;
import play.test.Helpers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class UserTCFSTest extends CommonUnitTest {

    @Test
    public void testAuthenticate() {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                String email = "joe@tcfs.com";
                UserTCFS user = UserTCFS.findByEmail(email);
                assertEquals(user.getEmail(), email);
                UserTCFS admin = null;
                admin = UserTCFS.authenticate(email, "joe");
                assertNotNull(admin);
                String fakeEmail = "blabla@tcfs.com";
                UserTCFS fakeUser = UserTCFS.findByEmail(fakeEmail);
                assertNull(fakeUser);
            }
        });
    }

    @Test
    public void testCreateUser() {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                String email = "someuser@tcfs.com";
                String name = "New user";
                String password = "qwerty";
                UserTCFS user = new UserTCFS(email, name, password);
                Ebean.save(user);
                UserTCFS loadedUser = UserTCFS.findByEmail(email);
                assertEquals(loadedUser.getEmail(), email);
                assertEquals(loadedUser.toString(), name);
                assertEquals(loadedUser.password, password);
            }
        });
    }

    @Test
    public void testFindUsersTest() throws Exception {
        Helpers.running(fakeAppWithGlobal, new Runnable() {
            public void run() {
                loadAllData();
                int adminsCount = 0;
                int waitersCount = 0;
                int cookCount = 0;
                int cashierCount = 0;
                List<UserTCFS> allUsers = UserTCFS.findAll();
                assertNotNull(allUsers);
                for (UserTCFS user : allUsers){
                    if(user.isAdmin())
                        adminsCount++;
                    if(user.isCashier())
                        cashierCount++;
                    if(user.isCook())
                        cookCount++;
                    if(user.isWaiter())
                        waitersCount++;
                }
                try {
                    int actualadminsCount = UserTCFS.findAllByType(MemberType.Admin).size();
                    int actualwaitersCount = UserTCFS.findAllByType(MemberType.Waiter).size();
                    int actualcashierCount =  UserTCFS.findAllByType(MemberType.Cashier).size();
                    assertEquals(adminsCount, actualadminsCount);
                    assertEquals(waitersCount,actualwaitersCount );
                    assertEquals(cashierCount, actualcashierCount);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
