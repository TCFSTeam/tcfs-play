
$(document).ready(function() {

    var tableId = 0;
    var reservId = 0;


    $('#reservations tr').click(function() {
        reservId = $(this).find("td").eq(0).html();
        var tablesStatistic =  "/getreservation/"+reservId;
        $.getJSON( tablesStatistic )
            .done(function( json ) {
                $("#name").val(json.reservator);
                tableId = json.tableId;
                $(".table-button").removeClass("btn-success");
                $("#table" + tableId).addClass("btn-success");
                $('.datetimepicker').datetimepicker('update', new Date(json.startAt.year,json.startAt.monthOfYear,json.startAt.dayOfMonth));
                console.log( "Reservator: " + json.reservator );
            })
            .fail(function( jqxhr, textStatus, error ) {
                var err = textStatus + ", " + error;
                alert("Request Failed: " + err)
                console.log( "Request Failed: " + err );
            });
    });

    $(".table-button").click(function () {
        tableId = $(this).attr("value");
        $(".table-button").removeClass("btn-success");
        $("#table" + tableId).addClass("btn-success");
    });

    $( "#addreserv" ).click(function() {
        var date = $("#startAt").data('date');
        var o = {
            reservator: $("#name").val(),
            startAt: new Date(date).toJSON(),
            tableId: tableId,
            reservId: reservId
        };
        $.ajax({
            type : 'POST',
            url : '@routes.ReservationController.addreserv()',
            data : o,
            success : function(data) {
                console.log("Done");
                window.location.reload();
            },
            error : function(data) {
                alert("Unable to add reservation");
            }
        });
    });
    $( "#updreserv" ).click(function() {
        var date = $("#startAt").data('date');
        var o = {
            reservator: $("#name").val(),
            startAt: new Date(date).toJSON(),
            tableId: tableId,
            reservId: reservId
        };
        $.ajax({
            type : 'POST',
            url : '/reserve/update/'+reservId,
            data : o,
            success : function(data) {
                console.log("Done");
                window.location.reload();
            },
            error : function(data) {
                alert("Unable to add reservation");
            }
        });
    });
});