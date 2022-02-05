$(function() {
  $('#container').highcharts({
    xAxis: {},
    yAxis: [{
      height: 200,
      lineWidth: 2,
      offset: 0
    }, {
      height: 200,
      top: 300,
      lineWidth: 2,

      offset: 0
    }, {
      height: 200,
      top: 550,
      lineWidth: 2,
      offset: 0
    }],
    series: [{
      type: 'area',
      name: 'Tokyo',
      yAxis: 0,
      data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    }, {
      type: 'line',
      name: 'New York',
      yAxis: 1,
      data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
    }, {
      type: 'column',
      name: 'Berlin',
      yAxis: 2,
      data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
    }]
  });
});