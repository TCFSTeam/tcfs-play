package controllers;

import akka.util.Helpers;
import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DateTimeHelper;
import models.OrderTCFS;
import models.Reservation;
import models.UserTCFS;
import org.joda.time.DateTime;
import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
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

    /*
    * Custom javascript reverse-routing
     */
    public static Result reservationJavascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("rJsRoutes",
                controllers.routes.javascript.ReservationController.addreserv(),
                controllers.routes.javascript.ReservationController.update()
                ));
    }
    public static Result reserve() {
        UserTCFS currentUser = UserTCFS.find.byId(request().username());
        if (!currentUser.isAdmin())
            return unauthorized();
        List<Reservation> reservations = Reservation.findAllActive();
        return ok(views.html.reserv.render(currentUser, reservations));
    }

    public static Result delete(Integer reservId) {
        UserTCFS currentUser = UserTCFS.find.byId(request().username());
        if (!currentUser.isAdmin())
            return unauthorized();
        Reservation reservation = Reservation.findById(reservId);
        if(reservation != null){
            reservation.setIsActive(false);
            Ebean.save(reservation);
            List<Reservation> reservations = Reservation.findAllActive();
            return ok(views.html.reserv.render(currentUser, reservations));
        }
        else {
            return internalServerError();
        }
    }

    public static Result getreservation(Integer reservId) {
        Reservation reserv = Reservation.findById(reservId);
        return ok(Json.toJson(reserv));
    }

    public static Result addreserv() {
        try {
            UserTCFS currentUser = UserTCFS.find.byId(request().username());
            if (!currentUser.isAdmin())
                return unauthorized();
            DynamicForm data = Form.form().bindFromRequest();
            int reservId = Integer.parseInt(data.get("reservId"));
            Reservation reservation;
            if(reservId > 0){
                reservation = Reservation.findById(reservId);
            }
            else{
                reservation = new Reservation();
            }
            String startAt = data.get("startAt");
            reservation.setReservator(data.get("reservator"));
            reservation.setStartAt(DateTimeHelper.parseDateString(startAt));
            reservation.setCreatedAt(DateTime.now());
            reservation.setTableId(Integer.parseInt(data.get("tableId")));
            reservation.setIsActive(true);
            Ebean.save(reservation);
            List<Reservation> reservations = Reservation.findAllActive();
            return ok(views.html.reserv.render(currentUser, reservations));
        }
        catch (Exception ex){
            return internalServerError();
        }
    }

    public static Result update(Integer id) {
        try {
            UserTCFS currentUser = UserTCFS.find.byId(request().username());
            if (!currentUser.isAdmin())
                return unauthorized();
            DynamicForm data = Form.form().bindFromRequest();
            int reservId = Integer.parseInt(data.get("reservId"));
            if(reservId != id)
                return badRequest();
            Reservation reservation = null;
            if(reservId > 0){
                reservation = Reservation.findById(reservId);
            }
            if(reservation != null){
                String startAt = data.get("startAt");
                reservation.setReservator(data.get("reservator"));
                reservation.setStartAt(DateTimeHelper.parseDateString(startAt));
                reservation.setCreatedAt(DateTime.now());
                reservation.setTableId(Integer.parseInt(data.get("tableId")));
                reservation.setIsActive(true);
                Ebean.save(reservation);
                List<Reservation> reservations = Reservation.findAllActive();
                return ok(views.html.reserv.render(currentUser, reservations));
            }
            return notFound();
        }
        catch (Exception ex){
            return internalServerError();
        }
    }
}
