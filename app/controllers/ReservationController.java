package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.OrderTCFS;
import models.Reservation;
import models.UserTCFS;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 4/12/2015.
 */
@Security.Authenticated(controllers.SecuredController.class)
public class ReservationController extends Controller {
    public static play.mvc.Result reserve() {
        UserTCFS currentUser = UserTCFS.find.byId(request().username());
        if (!currentUser.isAdmin())
            return unauthorized();
        List<Reservation> reservations = new ArrayList<>();
        reservations = Reservation.findAll();
        return ok(views.html.reserv.render(currentUser, reservations));
    }
    public static Result getreservation(Integer reservId) {
        Reservation reserv = Reservation.findById(reservId);
        return ok(Json.toJson(reserv));
    }
}
