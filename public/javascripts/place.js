/**
 * Created by Alexander on 4/23/2015.
 */
$(document).ready(function () {
    /**
     * Autocomplete for menu items
     */
    $("#select_order_item").select2({ placeholder: "Select an order item", maximumSelectionSize: 1 });

    $(".dropdown-menu li a").click(function(){
        var waiterId = $(this).parent().attr('value');
        var orderId = $('#orderId').val();
        var waiterName = $(this).text();
        $(this).parents('.dropdown').find('.dropdown-toggle').html(waiterName+' <span class="caret"></span>');
        oJsRoutes.controllers.OrderController.setWaiter(orderId, waiterId).ajax({
            success: function () {
                console.log("Waiter changed to: " + waiterId);
                $.notify({
                    message: 'New waiter is:' + waiterName
                });
            },
            error: function () {
                console.log("Error on waiter changing " + waiterId);
                $.notify({
                    message: 'Waiter not changed!'
                },{
                    type: 'error'
                });
            }
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
     * Change readiness status for order items
     */
    $(".ready").change(function () {
        orderId = $('#orderId').val();
        itemId = $(this).attr("value");
        oJsRoutes.controllers.OrderController.setReady(orderId, itemId).ajax({
            success: function () {
                $("#chkReady" + itemId).prop("disabled", true);
                $("#chkReady" + itemId).parent().parent().addClass("success");
                $.notify({
                    message: 'New readiness changed for item:' + $(this).val()
                });
            },
            error: function () {
                $.notify({
                    message: 'Error on readiness setting!'
                },{
                    type: 'danger'
                });
            }
        });
    });

    /**
     * Change table logic
     */
    $(".table-button").click(function () {
        orderId = $('#orderId').val();
        tableId = $(this).attr("value");
        oJsRoutes.controllers.OrderController.setTable(orderId, tableId).ajax({
            success: function () {
                $(".table-button").removeClass("btn-success");
                $("#table" + tableId).addClass("btn-success");
                $.notify({
                    message: 'New table number:' + tableId.toString()
                });
            },
            error: function () {
                $.notify({
                    message: 'Error on table changing!'
                },{
                    type: 'danger'
                });
            }
        });
    });

    /**
     * Change guests count logic
     */
    $(".guests-button").click(function () {
        orderId = $('#orderId').val();
        guests = $(this).attr("value");
        oJsRoutes.controllers.OrderController.setGuests(orderId, guests).ajax({
            success: function () {
                $(".guests-button").removeClass("btn-success");
                $("#guests" + guests).addClass("btn-success");
                $.notify({
                    message: 'New guests count:' + guests.toString()
                });
            },
            error: function () {
                $.notify({
                    message: 'Error on guests changing!'
                },{
                    type: 'danger'
                });
            }
        });

    });
});