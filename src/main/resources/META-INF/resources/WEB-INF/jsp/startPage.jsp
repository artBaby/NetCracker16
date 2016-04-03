<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel='stylesheet' href='/css/test.css'/>
    <link rel='stylesheet' href='/css/jquery-ui.css'/>
    <script src='${pageContext.request.contextPath}/javascript/Chart.js'></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-1.12.2.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-ui.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-ui.min.js"></script>
</head>

<body>
      <h1>TwitSense &trade;</h1>
      <table>
          <tr>
              <td>Input your request:</td>
              <td><input name="topic" type="text" size="50" id="requestText" /></td>
              <td>From</td>
              <td><input id="calendarFrom" type="text" size="10"/></td>
              <td>To</td>
              <td><input id="calendarTo" type="text" size="10"/></td>
              <td><input type="button" value="get info!" onclick="Drawing()" /></td>
          </tr>
          <tr><td></td><td><div id="message"></div></td></tr>
      </table>
      <div class="chart">
          <canvas  id="barChart" ></canvas>
      </div>

<script>
    var barChart;

    $(function(){
        $.datepicker.setDefaults(
                $.extend($.datepicker.regional["en"])
        );
        $("#calendarFrom").datepicker({dateFormat:'yy-mm-dd'});
        $("#calendarTo").datepicker({dateFormat:'yy-mm-dd'});
    });
    function drawChartBar(obj, idCanvas) {
        //drawing Chart(Bar)
        var barChartData = {
            labels: ['Negative', 'Somewhat negative', 'Neutral', 'Somewhat positive', 'Positive'],
            datasets: [
                {
                    fillColor: 'rgba(220,220,220,0.8)',
                    strokeColor: 'rgba(220,220,220,0.8)',
                    highlightFill: 'rgba(220,220,220,0.75)',
                    highlightStroke: 'rgba(220,220,220,1)',
                    data: [obj[0], obj[1], obj[2], obj[3], obj[4]]
                }
            ]
        };

        var ctx = document.getElementById(idCanvas).getContext('2d');
        barChart = new Chart(ctx).Bar(barChartData, {
            responsive: true
        });
    }

    function Drawing() {
        $('#message').empty();
        var inputText = $('#requestText').val().trim();
        var firstDate = $('#calendarFrom').val().trim();
        var lastDate = $('#calendarTo').val().trim();

        if(inputText === '') {
            $('#message').html('Entry data!');
        }else {
            console.log('inputText= ' + inputText);
            console.log('firstDate= ' + firstDate);
            console.log('lastDate= ' + lastDate);
            $.ajax({
                url: '/ajaxRequest',
                type: 'POST',
                data: 'topic=' + inputText + "&firstDate=" + firstDate + "&lastDate=" + lastDate,
                success: function (data) {
                    console.log("result=  " + data);
                    var obj = jQuery.parseJSON(data);
                    if (barChart != undefined || barChart != null)
                            barChart.destroy();
                    drawChartBar(obj, 'barChart');
                },
                error: function (data) {
                    console.log('error =  ' + data);
                    alert(data);
                }
            });
        }
    }
</script>
</body>
</html>