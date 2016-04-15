<%@ page import="java.util.*" %>
<%@ page import="netCrackerTestApp.objects.JsonHistory" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel='stylesheet' href='/css/test.css'/>
    <link rel='stylesheet' href='/css/bootstrap.min.css'/>
    <link rel='stylesheet' href='/css/bootstrap-theme.min.css'/>
    <link rel='stylesheet' href='/css/jquery-ui.css'/>
    <script src='${pageContext.request.contextPath}/javascript/Chart.js'></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-1.12.2.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/jquery-ui.js"></script>
    <script src="${pageContext.request.contextPath}/javascript/bootstrap.min.js"></script>


</head>

<body>
<div id="wrapper">
    <div id="header">
        <img src="../images/squirrel.png"  width="68" height="85" hspace="15">
        <h4 style="color: orangered; padding: 25px 0 0 0; display: inline-block;">Opinion mining</h4>
        <ul>
            <li><a href="info">Information</a></li>
            <li><a href="help">Help</a></li>
        </ul>
    </div>
    <div id="description"><h1 style="text-align: center; font-style: normal;">Sentiment analysis</h1>
        <p>Sentiment analysis aims to determine the attitude of a speaker or a writer with respect to some topic or the overall contextual polarity of a document.</p>
    </div>
    <div id="content">
        <div id="sidebar">
            <div id="sidebarTop"> <a class="twitter-timeline" data-dnt="true" href="https://twitter.com/Omi_support" data-widget-id="718071517645512705">Tweets by @Omi_support</a>
                <script>
                    !function(d,s,id){
                        var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';
                        if(!d.getElementById(id)){
                            js=d.createElement(s);
                            js.id=id;
                            js.src=p+"://platform.twitter.com/widgets.js";
                            fjs.parentNode.insertBefore(js,fjs);
                        }
                    }
                    (document,"script","twitter-wjs");
                </script>
            </div>
            <div id="sidebarBottom">
                <h3>History</h3>
                <div id="history">
                    <table class="table table-hover">
                        <%
                            List<JsonHistory> listTopicsWithDates = (List<JsonHistory>) request.getAttribute("listTopicsWithDates");
                            for (JsonHistory history : listTopicsWithDates) {
                                out.println("<tr><td><a style=\"text-decoration: none; color: black;\" href=\"#\" onclick=\"getTopicAndDateByLink(this); return false\">" +
                                        history.getTopic() + " - " + history.getCreatedDate() +"</a></td></tr>");
                            }
                        %>
                    </table>
                </div>
                <a href="#clearHistory" style="text-decoration: none; float: right;" onclick="deleteRequestHistory()">Clear history</a>
            </div>
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
                        <div class="col-sm-12">
                            <input type="button" class="btn btn-info pull-right" value="get info!" onclick="getSentimentResultWithTweetsByTopic()" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-6">
                            <div id="message"></div>
                        </div>
                    </div>
                </form>
            </div>
            <div id="output">
                <fieldset class="fieldset">
                    <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="active"><a id="tab1" href="#chart" data-toggle="tab">Chart</a></li>
                        <li><a id="tab2" href="#posts" data-toggle="tab">Posts</a></li>
                    </ul>
                    <div class="tab-content">
                        <div id="chart" class="tab-pane active">
                            <canvas id="barChart"></canvas>
                        </div>
                        <div id="posts" class="tab-pane"></div>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>
    <div id="footer"> 2016 Opinion mining</div>
</div>

<script>
    var barChart;
    var ipAddress = jQuery.parseJSON('${jsonIpAddress}');

    $(function(){
        $.datepicker.setDefaults(
                $.extend($.datepicker.regional["en"])
        );
        $("#calendarFrom").datepicker({dateFormat:'yy-mm-dd'});
        $("#calendarTo").datepicker({dateFormat:'yy-mm-dd'});
    });

    function getTopicAndDateByLink(value) {
        var topicAndDate = value.innerHTML;
        var activeTab = 'tab1';
        console.log('topicAndDate= ' + topicAndDate);
        $.ajax({
            url: '/getSentimentResultByTopicAndDate',
            type: 'POST',
            data: 'topicAndDate=' + topicAndDate + "&ipAddress=" + ipAddress,
            success: function (data) {
                console.log('data =  ' + data);
                var sentimentResultWithTweets = jQuery.parseJSON(data);
                var sentimentResults = sentimentResultWithTweets.map( sentimentResultTweet => sentimentResultTweet.sentimentResult);
                var numberOfTweets = sentimentResultWithTweets.map( sentimentResultTweet => sentimentResultTweet.numberOfTweets);
                $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                    activeTab = $(e.target).attr('id');
                    if (activeTab == 'tab1') {
                        if (barChart != undefined || barChart != null)
                            barChart.destroy();
                        drawChartBar(sentimentResults, numberOfTweets);
                    }
                });
                activeTab = $('.nav-tabs .active').text();
                if (activeTab == 'Chart'){
                    if (barChart != undefined || barChart != null)
                        barChart.destroy();
                    drawChartBar(sentimentResults, numberOfTweets);
                }
                showTweets(sentimentResultWithTweets);
            },
            error: function (data) {
                console.log('getTopicAndDateByLink Error =  ' + data);
                alert(data);
            }
        });

    }

    function drawChartBar(sentimentResultArray, numberOfTweetsArray) {

        //drawing Chart(Bar)
        var barChartData = {
            labels: sentimentResultArray,
            datasets: [
                {
                    fillColor: 'rgba(220,220,220,0.8)',
                    strokeColor: 'rgba(220,220,220,0.8)',
                    highlightFill: 'rgba(220,220,220,0.75)',
                    highlightStroke: 'rgba(220,220,220,1)',
                    data: numberOfTweetsArray
                }
            ]
        };

        var ctx = document.getElementById('barChart').getContext('2d');
        barChart = new Chart(ctx).Bar(barChartData, {
            responsive: true
        });
    }

    function showTweets(sentimentResultWithTweets) {
        var str= '<table class="table table-hover">';
        for(var k in sentimentResultWithTweets){
            var sentimentResultTweet = sentimentResultWithTweets[k];
            for(var i in sentimentResultTweet.tweets){
                str= str.concat('<tr><td>' + sentimentResultTweet.sentimentResult + '</td><td>' + sentimentResultTweet.tweets[i] + '</td></tr>');
            }
        }
        str.concat('</table>');
        $('#posts').html(str);

    }

    function getSentimentResultWithTweetsByTopic() {
        $('#message').empty();
        var inputText = $('#requestText').val().trim();
        var firstDate = $('#calendarFrom').val().trim();
        var lastDate = $('#calendarTo').val().trim();

        if(inputText === '') {
            $('#message').html('<h4 style="color: red">Enter data!</h4>');
            barChart.destroy();
            $('#posts').remove();
        }else {
            console.log('inputText= ' + inputText);
            console.log('firstDate= ' + firstDate);
            console.log('lastDate= ' + lastDate);
            $.ajax({
                url: '/ajaxRequest',
                type: 'POST',
                data: 'topic=' + inputText + "&firstDate=" + firstDate + "&lastDate=" + lastDate + "&ipAddress=" + ipAddress,
                success: function (data) {
                    console.log('data=' + data);
                    getHistory();
                    var sentimentResultWithTweets = jQuery.parseJSON(data);
                    var sentimentResults = sentimentResultWithTweets.map( sentimentResultTweet => sentimentResultTweet.sentimentResult);
                    var numberOfTweets = sentimentResultWithTweets.map( sentimentResultTweet => sentimentResultTweet.numberOfTweets);
                    if (barChart != undefined || barChart != null)
                        barChart.destroy();
                    drawChartBar(sentimentResults, numberOfTweets);
                    showTweets(sentimentResultWithTweets);
                },
                error: function (data) {
                    console.log('error =  ' + data);
                    alert(data);
                }
            });
        }
    }

    function getHistory() {
        console.log('ip= ' + ipAddress);
        $.ajax({
            url: '/ajaxGetHistory',
            type: 'POST',
            data: 'ipAddress=' + ipAddress,
            success: function (data) {
                var listTopicsWithDates = jQuery.parseJSON(data);

                var str= '<table class="table table-hover">';
                for(var i in listTopicsWithDates){
                    var topicWithDate = listTopicsWithDates[i];
                        str= str.concat('<tr><td><a style="text-decoration: none; color: black;" href="#" onclick="getTopicAndDateByLink(this); return false">' + topicWithDate.topic + ' - ' + topicWithDate.createdDate + '</a></td></tr>');
                }
                str.concat('</table>');
                $('#history').html(str);

            },
            error: function (data) {
                console.log('getHistory Error =  ' + data);
                alert(data);
            }
        });
    }

    function deleteRequestHistory() {
        console.log('ip= ' + ipAddress);
        $.ajax({
            url: '/delete',
            type: 'POST',
            data: 'ipAddress=' + ipAddress,
            success: function (data) {
                $('#history').html('<table class="table table-hover"></table>');
            },
            error: function (data) {
                console.log('deleteRequestHistory Error =  ' + data);
                alert(data);
            }
        });

    }
</script>
</body>
</html>