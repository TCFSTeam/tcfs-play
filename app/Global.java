/**
 * Created by test on 10/12/14.
 */

import com.avaje.ebean.Ebean;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import java.util.List;
import java.util.Map;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        @SuppressWarnings("unchecked")
        Map<String,List<Object>> all = (Map<String,List<Object>>) Yaml.load("initial-data.yml");

        // Save all roles
        Ebean.save(all.get("users"));
        // Save all roles
        Ebean.save(all.get("items"));

        // Insert users and for every user save its many-to-many association
        Ebean.save(all.get("orders"));
        for(Object user: all.get("orders")) {
            Ebean.saveManyToManyAssociations(user, "items");
        }
    }

}