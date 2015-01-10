/**
 * Created by alexander on 12/22/14.
 */

$(document).ready(function () {
    $("#single-append-text").select2({ placeholder: "Select an order item", maximumSelectionSize: 1 });
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
$('.table-button').click(function () {
    $('.table-button').removeClass("btn-success");
    ($(this).addClass("btn-success"));
    $('#tableId').val($(this).val());

});

/**
 * Do AJAX on order item ready.
 */
function setReadinessStatus(orderId, itemId) {
    jsRoutes.controllers.OrderController.setReady(orderId, itemId).ajax({
        success: function () {
            $("#chkReady" + itemId).prop("disabled", true);
            $("#chkReady" + itemId).parent().parent().addClass("success");
        }
    });
}