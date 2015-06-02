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
import models.UserTCFS;
import play.Routes;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.activeOrders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alexander on 12/20/14.
 */
@Security.Authenticated(controllers.SecuredController.class)
public class OrderController extends Controller {

    /*
    * Custom javascript reverse-routing
     */
    public static Result orderJavascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("oJsRoutes",
                controllers.routes.javascript.OrderController.setTable(),
                controllers.routes.javascript.OrderController.setReady(),
                controllers.routes.javascript.OrderController.setWaiter(),
                controllers.routes.javascript.OrderController.setGuests()
        ));
    }

    /**
     * Place new order
     */
    public static Result place() {
        OrderTCFS order = new OrderTCFS();
        order.Waiter = request().username();
        order.TableId = 1;
        order.id = OrderTCFS.findAll().size() + 1;
        order.OrderStatus = "Active";
        order.setNotSaved();
        Ebean.save(order);
        return ok(views.html.placeOrder.render(UserTCFS.find.byId(request().username()), MenuItem.findAll(), order));
    }

    /**
     * Show active orders
     */
    public static Result active() {
        return ok(activeOrders.render(UserTCFS.find.byId(request().username())));
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
                sortBy = "TableId";
                break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        UserTCFS currentUserTCFS = UserTCFS.find.byId(request().username());
        Page<OrderTCFS> contactsPage = null;
        if (currentUserTCFS.isWaiter() || currentUserTCFS.isCook()) {
            contactsPage = OrderTCFS.find
                    .where(
                            Expr.or(
                                    Expr.ilike("OrderStatus", "%" + filter + "%"),
                                    Expr.or(
                                            Expr.ilike("Waiter", "%" + filter + "%"),
                                            Expr.ilike("TableId", "%" + filter + "%")
                                    )
                            )
                    )
                    .where().eq("OrderStatus", "Active")
                    .findPagingList(pageSize).setFetchAhead(false)
                    .getPage(page);
        } else if (currentUserTCFS.isCashier()) {
            contactsPage = OrderTCFS.find
                    .where(
                            Expr.or(
                                    Expr.ilike("OrderStatus", "%" + filter + "%"),
                                    Expr.or(
                                            Expr.ilike("Waiter", "%" + filter + "%"),
                                            Expr.ilike("TableId", "%" + filter + "%")
                                    )
                            )
                    )
                    .where().eq("OrderStatus", "WaitForPay")
                    .findPagingList(pageSize).setFetchAhead(false)
                    .getPage(page);
        } else {
            contactsPage = OrderTCFS.find
                    .where(
                            Expr.or(
                                    Expr.ilike("OrderStatus", "%" + filter + "%"),
                                    Expr.or(
                                            Expr.ilike("Waiter", "%" + filter + "%"),
                                            Expr.ilike("TableId", "%" + filter + "%")
                                    )
                            )
                    )
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
        int rowIndex = 0;
        for (OrderTCFS c : contactsPage.getList()) {
            if (currentUserTCFS.isCook()
                    && OrderTCFS.getReadinessStatus(c.id) >= 100)
                continue;
            ObjectNode row = Json.newObject();
            // starts from 1 because 0 column - link to edit/view order
            rowIndex = 0;
            row.put(String.valueOf(rowIndex++), "<a href=\"/edit/" + c.id + "\" ><i class=\"fa fa-edit fa-fw\"></i></a>");
            //set pay action for waiter and admin
            if (currentUserTCFS.isAdmin() || currentUserTCFS.isWaiter()) {
                if (c.saved) {
                    if (!c.OrderStatus.equals("Complete"))
                        row.put(String.valueOf(rowIndex++), "<a href=\"/pay/" + c.id + "\"><i class=\"fa fa-shopping-cart fa-fw\"></i></a>");
                    else
                        row.put(String.valueOf(rowIndex++), "<i class=\"fa fa-shopping-cart fa-fw\"></i>");
                } else {
                    row.put(String.valueOf(rowIndex++), "<i class=\"fa fa-shopping-cart fa-fw\"></i>");
                }
            } else if (currentUserTCFS.isCashier()) {
                if (c.saved) {
                    row.put(String.valueOf(rowIndex++), "<a href=\"/payclose/" + c.id + "\"><i class=\"fa fa-credit-card fa-fw\"></i></a>");
                }
            }
            row.put(String.valueOf(rowIndex++), c.id);
            row.put(String.valueOf(rowIndex++), UserTCFS.findByEmail(c.Waiter).toString());
            row.put(String.valueOf(rowIndex++), c.guestsCount);
            row.put(String.valueOf(rowIndex++), c.TableId);
            row.put(String.valueOf(rowIndex++), c.OrderStatus.toString());
            row.put(String.valueOf(rowIndex++), NumbersHelper.getReadinessString(OrderTCFS.getReadinessStatus(c.id)).toString() + "%");
            if (currentUserTCFS.memberType == UserTCFS.MemberType.Admin) {
                if (c.saved)
                    row.put(String.valueOf(rowIndex++), "Saved");
                else
                    row.put(String.valueOf(rowIndex++), "Not saved");
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
     * AJAX set waiter
     */
    public static Result setWaiter(Integer orderId, String waiter) {
        if (OrderTCFS.setWaiter(orderId, waiter))
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
            return ok(views.html.placeOrder.render(UserTCFS.find.byId(request().username()), MenuItem.findAll(), order));
        }
    }

    /**
     * Edit order form
     */
    public static Result edit(Integer id) {
        return ok(views.html.placeOrder.render(UserTCFS.find.byId(request().username()), MenuItem.findAll(), OrderTCFS.findById(id)));
    }

    /**
     * Return item from order
     */
    public static Result returnItem(Integer orderId, Integer itemId) {
        OrderTCFS.returnItem(itemId);
        return ok(views.html.placeOrder.render(UserTCFS.find.byId(request().username()), MenuItem.findAll(), OrderTCFS.findById(orderId)));
    }

    /**
     * Pay order action
     */
    public static Result table(Integer id) {
        OrderTCFS.proceedToPay(id);
        return ok(activeOrders.render(UserTCFS.find.byId(request().username())));
    }
}

