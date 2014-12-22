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