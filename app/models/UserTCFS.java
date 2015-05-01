package models;

import com.avaje.ebean.annotation.EnumValue;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
/**
 * UserTCFS entity managed by Ebean
 */
@Entity
public class UserTCFS extends Model {

    private static final long serialVersionUID = 1L;
    public static Model.Finder<String, UserTCFS> find = new Model.Finder<String, UserTCFS>(String.class, UserTCFS.class);
    @Id
    public String email;
    public String name;
    public String password;
    public String imagePath;
    public MemberType memberType;

    public UserTCFS(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    /**
     * Retrieve all users.
     */
    public static List<UserTCFS> findAll() {
        return find.all();
    }

    /**
     * Retrieve all users by type.
     */
    public static List<UserTCFS> findAllByType(MemberType memberType) {
        return find.where().eq("MemberType", memberType.name()).findList();
    }
    /**
     * Retrieve a UserTCFS from email.
     */
    public static UserTCFS findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    /**
     * Authenticate a UserTCFS.
     */
    public static UserTCFS authenticate(String email, String password) {
        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return name;
    }

    public boolean isAdmin() {
        return this.memberType == MemberType.Admin;
    }

    public boolean isWaiter() {
        return this.memberType == MemberType.Waiter;
    }

    public boolean isCook() {
        return this.memberType == MemberType.Сook;
    }

    public boolean isCashier() {
        return this.memberType == MemberType.Cashier;
    }

    public enum MemberType {
        @EnumValue("WT")Waiter, @EnumValue("CK")Сook, @EnumValue("ADM")Admin, @EnumValue("CASH")Cashier
    }

}

