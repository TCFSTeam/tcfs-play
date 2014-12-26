package controllers;

import models.OrderItem;
import models.OrderTCFS;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by alexander on 12/20/14.
 */
@Security.Authenticated(SecuredController.class)
public class OrderController extends Controller {

    public static Result place() {
        return ok(views.html.placeOrder.render(User.find.byId(request().username()), OrderItem.findAll(),null));
    }

    public static Result active() {
        User.MemberType memberType = User.find.byId(request().username()).memberType;
        if(memberType == User.MemberType.Сook || memberType == User.MemberType.Cashier)
        {return ok(views.html.activeOrders.render(User.find.byId(request().username()), OrderTCFS.findAllActive()));}
        else if(memberType == User.MemberType.Admin)
        {return ok(views.html.activeOrders.render(User.find.byId(request().username()), OrderTCFS.findAll()));}
        else
        {return ok(views.html.activeOrders.render(User.find.byId(request().username()), OrderTCFS.findActiveByUser(User.find.byId(request().username()))));}
    }

    public static Result add() {
        Form<OrderItem> formData = Form.form(OrderItem.class).bindFromRequest();
        if (formData.hasErrors()) {
            return badRequest();
        }
        else {
            OrderItem orderItem = OrderItem.findById(Integer.parseInt(formData.data().get("itemId").toString()));
            OrderTCFS order = new OrderTCFS();
            order.items.add(orderItem);
            return ok(views.html.placeOrder.render(User.find.byId(request().username()), OrderItem.findAll(),order));
        }
    }
    public static Result edit(Integer id) {
            return ok(views.html.edit.render(User.find.byId(request().username()), OrderTCFS.findById((id))));
        }
    }

