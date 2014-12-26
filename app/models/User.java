package models;

import com.avaje.ebean.annotation.EnumValue;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
/**
 * User entity managed by Ebean
 */
@Entity
public class User extends Model {

    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, User> find = new Model.Finder<String, User>(String.class, User.class);
    @Id
    public String email;
    public String name;
    public String password;
    public String imagePath;
    public MemberType memberType;
    //public UserSettings settings;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }


    public enum MemberType {
        @EnumValue("W")Waiter, @EnumValue("CK")Сook, @EnumValue("A")Admin, @EnumValue("CASH")Cashier
    }

    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public String getEmail() {
        return email;
    }
    public String toString() {
        return "User(" + email + ")";
    }

}

