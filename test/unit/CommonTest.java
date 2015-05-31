package unit;

import org.junit.Before;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander on 5/31/2015.
 */
public class CommonTest {
    FakeApplication fakeAppWithGlobal;
    @Before
    public void onStart() {
        System.out.println("Starting FakeApplication");
        Map<String, String> settings = new HashMap();
        settings.put("db.default.driver", "org.h2.Driver");
        settings.put("db.default.url", "jdbc:h2:mem:play-test;MODE=MySQL");
        settings.put("ebean.default", "models.*");
        fakeAppWithGlobal = Helpers.fakeApplication(settings);
    }
}
