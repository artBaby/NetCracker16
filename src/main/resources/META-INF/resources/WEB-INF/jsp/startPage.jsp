<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel='stylesheet' href='/css/test.css'/>

<body>
    <div style="text-align:center">

        <h1>TwitSense &trade;</h1>
        <form id='form' method="POST" action="getTopic">
            <p>Input your request:<br>
                <input name='topic' type='text' size="50">
            </p>
            <input type='submit' value='get info!'>
        </form>
        <%="Hello World at " + System.currentTimeMillis()
                + " milli seconds "%>

    </div>
</body>

</html>