import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class ApplicationControllerTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
//        new User("bob@gmail.com", "Bob", "secret").save();
//        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
//        Content html = views.html.userSettings.render(User.find.byId(bob.name));
//        assertThat(contentType(html)).isEqualTo("text/html");
//        assertThat(contentAsString(html)).contains("Bob");
    }


}
