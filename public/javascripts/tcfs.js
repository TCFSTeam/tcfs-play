// Set menu on sidebar
$(function () {
    $('#side-menu').metisMenu();
});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function () {
    $(window).bind("load resize", function () {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });
});

/**
 * Created by alexander on 12/22/14.
 */

$(document).ready(function () {
    $("#single-append-text").select2({ placeholder: "Select an order item", maximumSelectionSize: 1 });

    $(".table-button").click(function () {
        orderId = $('#orderId').val();
        tableId = $(this).attr("value");
        jsRoutes.controllers.OrderController.setTable(orderId, tableId).ajax({
            success: function () {
                $(".table-button").removeClass("btn-success");
                $("#table" + tableId).addClass("btn-success");
            }
        });

    });

    $(".guests-button").click(function () {
        orderId = $('#orderId').val();
        guests = $(this).attr("value");
        jsRoutes.controllers.OrderController.setGuests(orderId, guests).ajax({
            success: function () {
                $(".guests-button").removeClass("btn-success");
                $("#guests" + guests).addClass("btn-success");
            }
        });

    });

    $(".ready").change(function () {
        orderId = $('#orderId').val();
        itemId = $(this).attr("value");
        jsRoutes.controllers.OrderController.setReady(orderId, itemId).ajax({
            success: function () {
                $("#chkReady" + itemId).prop("disabled", true);
                $("#chkReady" + itemId).parent().parent().addClass("success");
            }
        });

    });
});

/**
 * Enable\disable save and pay buttons on order correct confirmation.
 */
$('#isCorrectOrder').change(function () {
    if ($(this).is(":checked")) {
        $('#saveOrder').removeClass("disabled");
        $('#payOrder').removeClass("disabled");
    } else {
        $('#saveOrder').addClass("disabled");
        $('#payOrder').addClass("disabled");
    }
});

/**
 * Set class for table button on order creation\editing.
 */


$( ".ready" ).change(function() {
    item = $('#orderId').val();
    alert( "Handler for .change() called." + item );

    $.ajax({
        url: jsRoutes.controllers.OrderController.setReady(item, $(this).val()),
        context: document.body
    }).done(function() {
        $(".table-button").removeClass("btn-success");
        ($(this).addClass("btn-success"));
    });
});
