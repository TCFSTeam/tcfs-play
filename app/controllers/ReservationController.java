package controllers;

import models.Reservation;
import models.UserTCFS;
import play.mvc.Controller;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

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
}
