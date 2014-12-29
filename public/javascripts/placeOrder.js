/**
 * Created by alexander on 12/22/14.
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

$(document).ready(function() { $( "#single-append-text" ).select2( { placeholder: "Select an order item", maximumSelectionSize: 1 } ); });


$('.table-button').click(function () {
    $('.table-button').removeClass("btn-success");
    ($(this).addClass("btn-success"));
    $('#tableId').val($(this).val());

});
