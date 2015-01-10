package controllers;

import com.avaje.ebean.Ebean;
import models.MenuItem;
import models.OrderItem;
import models.OrderTCFS;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

/**
 * Created by alexander on 12/20/14.
 */
@Security.Authenticated(SecuredController.class)
public class OrderController extends Controller {

    /**
     * Place new order
     */
    public static Result place() {
        OrderTCFS order = new OrderTCFS();
        order.Waiter = request().username();
        order.Table = 1;
        order.id = OrderTCFS.findAll().size() + 1;
        order.OrderStatus = "Active";
        order.setNotSaved();
        Ebean.save(order);
        return ok(views.html.placeOrder.render(User.find.byId(request().username()), MenuItem.findAll(), order));
    }

    /**
     * Show active orders
     */
    public static Result active() {
        User.MemberType memberType = User.find.byId(request().username()).memberType;
        if (memberType == User.MemberType.Сook || memberType == User.MemberType.Cashier) {
            List<OrderTCFS> orders = OrderTCFS.findAllActive();
            return ok(views.html.activeOrders.render(User.find.byId(request().username()), orders));
        } else if (memberType == User.MemberType.Admin) {
            List<OrderTCFS> orders = OrderTCFS.findAll();
            return ok(views.html.activeOrders.render(User.find.byId(request().username()), orders));
        } else {
            List<OrderTCFS> orders = OrderTCFS.findActiveByUser(User.find.byId(request().username()));
            return ok(views.html.activeOrders.render(User.find.byId(request().username()), orders));
        }
    }

    /**
     * Filtering orders
     */
    public static Result sort(Integer tableId) {
        return ok(views.html.activeOrders.render(User.find.byId(request().username()), OrderTCFS.findAllActiveByTable(tableId, request().username())));
    }

    /**
     * Work on readiness status for order items
     */
    public static Result setReady(Integer orderId, Integer orderItemId) {
        if (OrderTCFS.setReadinessStatus(orderId, orderItemId))
            return ok();
        else
            return internalServerError();
    }

    /**
     * Processing new order
     */
    public static Result add() {
        OrderTCFS order;
        Form<MenuItem> formData = Form.form(MenuItem.class).bindFromRequest();
        if (formData.hasErrors()) {
            return badRequest();
        } else {
            MenuItem menuItem = MenuItem.findById(Integer.parseInt(formData.data().get("menuItemId").toString()));
            int orderId = (Integer.parseInt(formData.data().get("orderId").toString()));
            int table = (Integer.parseInt(formData.data().get("tableId").toString()));
            if (orderId > 0)
                order = OrderTCFS.findById(orderId);
            else {
                order = new OrderTCFS();
                order.Waiter = request().username();
                order.id = OrderTCFS.findAll().size() + 1;
                order.OrderStatus = "Active";
                order.setSaved();
            }
            if (menuItem != null && menuItem.id > 0) {
                OrderItem orderItem = new OrderItem(menuItem.id, false);
                Ebean.save(orderItem);
                order.items.add(orderItem);
            }
            if (order != null) {
                order.setSaved();
                order.setTable(table);
                Ebean.save(order);
            }
            return ok(views.html.placeOrder.render(User.find.byId(request().username()), MenuItem.findAll(), order));
        }
    }

    /**
     * Edit order form
     */
    public static Result edit(Integer id) {
        return ok(views.html.edit.render(User.find.byId(request().username()), OrderTCFS.findById((id))));
    }
}

