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
        Ebean.save(all.get("users"));
        Ebean.save(all.get("menuitems"));
        Ebean.save(all.get("orderitems"));
        Ebean.save(all.get("orders"));
    }

}