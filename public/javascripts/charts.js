/**
 * Created by alexander on 09.04.15.
 */
$(document).ready(function() {
    var optionsForPieChart = {
        chart: {
            renderTo: 'ordersByWaiter',
            type: 'spline'
        },
        title: {
            text: 'Waiter\'s completed orders'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        series: [{}]
    };

    var optionsForTableChart = {
        chart: {
            renderTo: 'ordersByTable',
            type: 'spline'
        },
        title: {
            text: 'Completed orders by table'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' {point.per}
        },
        series: [{}]
    };

    var orderStatistic =  "/getOrdersStatistics";
    $.getJSON(orderStatistic,  function(data) {
        var processed_json = new Array();
        $.each(data, function(index, item) {
            processed_json.push([index, parseInt(item)]);
        })
        optionsForPieChart.series[0].type = 'pie';
        optionsForPieChart.series[0].name = 'Total orders';
        optionsForPieChart.series[0].data = processed_json;
        var chartOrders = new Highcharts.Chart(optionsForPieChart);
    });

    var tablesStatistic =  "/getTablesStatistics";
    $.getJSON(tablesStatistic,  function(data) {
        var processed_json = new Array();
        $.each(data, function(index, item) {
            processed_json.push([index, parseInt(item)]);
        })
        optionsForTableChart.series[0].type = 'pie';
        optionsForTableChart.series[0].name = 'Total orders';
        optionsForTableChart.series[0].data = processed_json;
        var chartOrders = new Highcharts.Chart(optionsForTableChart);
    });
});
