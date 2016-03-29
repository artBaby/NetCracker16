<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Hello world page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel='stylesheet' href='/css/test.css'/>
        <script src='/javascript/Chart.js'></script>
    </head>
    <body>
        <h1>${topic}</h1>
        <div style="width: 50%">
            <canvas id="canvas" height="450" width="600"></canvas>
        </div>
        <script>
            var randomScalingFactor = function(){ return Math.round(Math.random()*100)};
            var barChartData = {
                labels : ["January","February","March","April","May","June","July"],
                datasets : [
                    {
                        fillColor : "rgba(220,220,220,0.5)",
                        strokeColor : "rgba(220,220,220,0.8)",
                        highlightFill: "rgba(220,220,220,0.75)",
                        highlightStroke: "rgba(220,220,220,1)",
                        data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
                    },
                    {
                        fillColor : "rgba(151,187,205,0.5)",
                        strokeColor : "rgba(151,187,205,0.8)",
                        highlightFill : "rgba(151,187,205,0.75)",
                        highlightStroke : "rgba(151,187,205,1)",
                        data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
                    }
                ]
            }
            window.onload = function(){
                var ctx = document.getElementById("canvas").getContext("2d");
                window.myBar = new Chart(ctx).Bar(barChartData, {
                    responsive : true
                });
            }
        </script>
    </body>
</html>