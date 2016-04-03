<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel='stylesheet' href='/css/test.css'/>
    <link rel='stylesheet' href='/css/bootstrap.min.css'/>
    <link rel='stylesheet' href='/css/bootstrap-theme.min.css'/>
    <script src='${pageContext.request.contextPath}/javascript/Chart.js'></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-1.12.2.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-ui.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/bootstrap.min.js"></script>

</head>

<body>
<div id="wrapper">
    <div id="header"><img src="../images/1.png"></div>
    <div id="description">Sentiment analysis (also known as opinion mining) refers to the use of natural language processing, text analysis and computational linguistics to identify and extract subjective information in source materials. Sentiment analysis is widely applied to reviews and social media for a variety of applications, ranging from marketing to customer service.</div>
    <div id="content">
        <div id="sidebar">
            <div id="sidebarTop">News</div>
            <div id="sidebarBottom">Cache</div>
        </div>
        <div id="main">
            <div id="inputFields">
                <form role="form" class="form-horizontal">
                    <div class="form-group">
                        <div class="col-sm-6"><label>Input your request</label><input type="text" class="form-control" id="requestText"></div>
                        <div class="col-sm-3"><label>From</label><input type="text" class="form-control" id="calendarFrom"></div>
                        <div class="col-sm-3"><label>To</label><input type="text" class="form-control" id="calendarTo"></div>
                    </div>
                    <div class="form-group">
                        <div id="message"></div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input type="button" class="btn btn-info pull-right" value="get info!" onclick="drawing()" />
                        </div>
                    </div>
                </form>
            </div>
            <div id="output">
                <fieldset class="fieldset">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#home">Chart</a></li>
                        <li><a href="#menu1">Posts</a></li>
                    </ul>
                    <div class="tab-content">
                        <div id="home" class="tab-pane fade in active">
                            <canvas id="barChart"></canvas>
                        </div>
                        <div id="menu1" class="tab-pane fade">
                        </div>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>
    <div id="footer"></div>
</div>

<script>
    var barChart;

    $(function(){
        $.datepicker.setDefaults(
                $.extend($.datepicker.regional["en"])
        );
        $("#calendarFrom").datepicker({dateFormat:'yy-mm-dd'});
        $("#calendarTo").datepicker({dateFormat:'yy-mm-dd'});

        $(".nav-tabs a").click(function(){
            $(this).tab('show');
        });
        $('.nav-tabs a').on('shown.bs.tab', function(event){
            var x = $(event.target).text();         // active tab
            var y = $(event.relatedTarget).text();  // previous tab
            $(".act span").text(x);
            $(".prev span").text(y);
        });
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

    function drawing() {
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