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
        <%--<form id='form' method="POST" action="getTopic">--%>
            <p>Input your request:<br>
                <input name="topic" type="text" size="50" id="requestText" />
            </p>
            <input type="button" value="get info!" onclick="checkAjax()" />
        <%--</form>--%>

        <p id="result_text"></p>
    </div>
    <script type="text/javascript">
        function checkAjax() {
            var inputText = $("#requestText").val();
            console.log("inputText= "+inputText);
            $.ajax({
                url: '/ajaxTest',
                type: 'POST',
                data : "topic=" + inputText,
                success: function (data) {
                    console.log("result=  "+data);
                    $('#result_text').html(data);
                },
                error : function (data) {
                    alert(data.text);
                }
            });
        }
    </script>
</body>
</html>