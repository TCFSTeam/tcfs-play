package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.NumbersHelper;
import models.MenuItem;
import models.OrderItem;
import models.OrderTCFS;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.activeOrders;

import java.util.Map;

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
        return ok(activeOrders.render(User.find.byId(request().username())));
    }

    public static Result list() {
        /**
         * Get needed params
         */
        Map<String, String[]> params = request().queryString();

        Integer iTotalRecords = OrderTCFS.find.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "id";
        String order = params.get("sSortDir_0")[0];

        switch (Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0:
                sortBy = "Waiter";
                break;
            case 1:
                sortBy = "OrderStatus";
                break;
            case 2:
                sortBy = "Table";
                break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        User currentUser = User.find.byId(request().username());
        Page<OrderTCFS> contactsPage = null;
        if (currentUser.memberType == User.MemberType.Admin
                || currentUser.memberType == User.MemberType.Cashier
                || currentUser.memberType == User.MemberType.Сook) {
            contactsPage = OrderTCFS.find.where(
                    Expr.or(
                            Expr.ilike("Waiter", "%" + filter + "%"),
                            Expr.or(
                                    Expr.ilike("OrderStatus", "%" + filter + "%"),
                                    Expr.ilike("Table", "%" + filter + "%")
                            )
                    )
            )
                    .findPagingList(pageSize).setFetchAhead(false)
                    .getPage(page);
        } else {


            contactsPage = OrderTCFS.find.where(
                    Expr.or(Expr.ilike("OrderStatus", "%" + filter + "%"),
                            Expr.or(Expr.ilike("guestsCount", "%" + filter + "%"),
                                    Expr.ilike("Table", "%" + filter + "%")))
            ).where().eq("OrderStatus", "Active").where().eq("Waiter", request().username())
                    .findPagingList(pageSize).setFetchAhead(false)
                    .getPage(page);
        }

        if (contactsPage == null)
            return internalServerError();
        Integer iTotalDisplayRecords = contactsPage.getTotalRowCount();

        /**
         * Construct the JSON to return
         */
        ObjectNode result = Json.newObject();

        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", iTotalRecords);
        result.put("iTotalDisplayRecords", iTotalDisplayRecords);

        ArrayNode an = result.putArray("aaData");

        for (OrderTCFS c : contactsPage.getList()) {
            ObjectNode row = Json.newObject();
            // starts from 1 because 0 column - link to edit/view order
            row.put("0", "<a href=\"/edit/" + c.id + "\" ><i class=\"fa fa-edit fa-fw\"></i></a>");

            //set pay action for waiter and admin
            if (currentUser.memberType == User.MemberType.Admin || currentUser.memberType == User.MemberType.Waiter) {
                if (c.saved) {
                    row.put("1", "<a href=\"/pay/" + c.id + "\"><i class=\"fa fa-shopping-cart fa-fw\"></i></a>");
                } else {
                    row.put("1", "<i class=\"fa fa-shopping-cart fa-fw\"></i>");
                }
            }

            row.put("2", c.id);
            row.put("3", User.findByEmail(c.Waiter).toString());
            row.put("4", c.guestsCount);
            row.put("5", c.Table);
            row.put("6", c.OrderStatus.toString());
            row.put("7", NumbersHelper.getReadinessString(OrderTCFS.getReadinessStatus(c.id)).toString() + "%");
            if (currentUser.memberType == User.MemberType.Admin) {
                if (c.saved)
                    row.put("8", "Saved");
                else
                    row.put("8", "Not saved");
            }
            an.add(row);
        }

        return ok(result);
    }

    /**
     * AJAX Work on readiness status for order items
     */
    public static Result setReady(Integer orderId, Integer orderItemId) {
        if (OrderTCFS.setReadinessStatus(orderId, orderItemId))
            return ok();
        else
            return internalServerError();
    }

    /**
     * AJAX setting table for order
     */
    public static Result setTable(Integer orderId, Integer tableId) {
        if (OrderTCFS.setTable(orderId, tableId))
            return ok();
        else
            return internalServerError();
    }

    /**
     * AJAX set guests count on table
     */
    public static Result setGuests(Integer orderId, Integer guestsCount) {
        if (OrderTCFS.setGuests(orderId, guestsCount))
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
                Ebean.save(order);
            }
            return ok(views.html.placeOrder.render(User.find.byId(request().username()), MenuItem.findAll(), order));
        }
    }

    /**
     * Edit order form
     */
    public static Result edit(Integer id) {
        return ok(views.html.placeOrder.render(User.find.byId(request().username()), MenuItem.findAll(), OrderTCFS.findById(id)));
    }

    /**
     * Pay order action
     */
    public static Result pay(Integer id) {
        OrderTCFS.proceedToPay(id);
        return ok(activeOrders.render(User.find.byId(request().username())));
    }

    /**
     * Pay order action
     */
    public static Result table(Integer id) {
        OrderTCFS.proceedToPay(id);
        return ok(activeOrders.render(User.find.byId(request().username())));
    }
}

