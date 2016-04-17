<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel='stylesheet' href='/css/main.css'/>
    <link rel='stylesheet' href='/css/bootstrap.min.css'/>
    <link rel='stylesheet' href='/css/jquery-ui.css'/>
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
    <div id="description"></div>
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
        </div>
        <div id="main">
              <form>
                 <fieldset class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control" id="email" placeholder="Enter email">
                 </fieldset>
                  <fieldset class="form-group">
                    <label for="subject">Subject</label>
                     <input type="email" class="form-control" id="subject" placeholder="Enter subject">
                 </fieldset>
                  <fieldset class="form-group">
                      <label for="textArea">Content</label>
                      <textarea class="form-control" id="textArea" rows="3"></textarea>
                  </fieldset>
                  <button type="button" class="btn btn-sample" onclick="sendEmail()">Send</button>
                  <fieldset class="form-group">
                          <div id="message" style="text-align: center; color: #3b94d9;"></div>
                  </fieldset>
              </form>
        </div>
    </div>
    <div id="footer"> 2016 Opinion mining</div>
</div>

<script>



    function sendEmail() {
       $('#message').empty();
       var email = $('#email').val();
       var subject = $('#subject').val();
       var content = $('#textArea').val();
        $.ajax({
            url: '/sendEmail',
            type: 'POST',
            data: 'email=' + email + "&subject=" + subject + "&content=" + content,
            success: function (data) {
                console.log('data=' + data);
                $('#message').html('<h3>' + data + '</h3>');
            },
            error: function (data) {
                console.log('error =  ' + data);
                alert(data);
            }
        });
    }
</script>

</body>
</html>
