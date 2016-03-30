<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel='stylesheet' href='/css/test.css'/>
    <script src='${pageContext.request.contextPath}/javascript/Chart.js'></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-1.12.2.js"></script>

</head>

<body>
    <div style="text-align:center">

    <h1>TwitSense &trade;</h1>
    <p>Input your request:<br>
        <input name="topic" type="text" size="50" id="requestText" />
    </p>
    <input type="button" value="get info!" onclick="checkAjax()" />
    <p id="result_text"></p>

</div>
    <div style="width: 50%;">
        <canvas id="canvas" height="450" width="600"></canvas>
    </div>
<script>
    function checkAjax() {
        var inputText = $("#requestText").val();
        var values  = [];
        var obj;

        console.log("inputText= " + inputText);

        $.ajax({
            url: '/ajaxRequest',
            type: 'POST',
            data: "topic=" + inputText,

            success: function (data) {
                console.log("result=  " + data);
                obj = jQuery.parseJSON( data );
                for (var i in obj) {
                    console.log(obj[i]);
                    values.push(obj[i]);
                }
                //drawing Chart(Bar)
                var barChartData = {
                    labels: ["Negative", "Somewhat negative", "Neutral", "Somewhat positive", "Positive"],
                    datasets: [
                        {
                            fillColor: "rgba(220,220,220,0.8)",
                            strokeColor: "rgba(220,220,220,0.8)",
                            highlightFill: "rgba(220,220,220,0.75)",
                            highlightStroke: "rgba(220,220,220,1)",
                            data: [obj[0], obj[1], obj[2], obj[3], obj[4]]
                        }
                    ]
                };
                var ctx = document.getElementById("canvas").getContext("2d");
                window.myBar = new Chart(ctx).Bar(barChartData, {
                    responsive: true
                });
            },
            error: function (data) {
                console.log("result =  " + data);
                alert(data);
            }
        });
    }
</script>
</body>
</html>