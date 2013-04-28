<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head> 
        <title>FeedSpeak Preferences</title> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main-style.css" type="text/css" media="all" /><!-- Standard css style -->
        <script src="http://yui.yahooapis.com/3.10.0/build/yui/yui-min.js"></script><!-- YUI Lib CDN -->
       <!--<link rel="stylesheet" href="http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css" type="text/css">-->
        <link href='http://fonts.googleapis.com/css?family=Philosopher' rel='stylesheet' type='text/css'><!-- Googgle Philospher WebFont -->
        <script>
            // Create a YUI sandbox on your page.
            YUI().use('node', 'event', function(Y) {
                // The Node and Event modules are loaded and ready to use.
                // Your code goes here!
            });
        </script>

    </head> 
    <body > 
        
            <div id="hd" role="banner"><h1>FeedSpeak</h1></div> 
            <div id="bd" role="main"> 
                

                <form class="configureForm" action="${pageContext.request.contextPath}/auth" method="POST" onsubmit="return validateLogin();" >
                   <!-- <div class="formtitle">Configure FeedSpeak</div> -->

                    <div class="input">
                        <p class="name">

                            <input type="text" name="name" id="name" />
                            <label for="name">Name</label>
                        </p>

                        <p class="phone">

                            <input type="text" name="phone" id="phone" />
                            <label for="phone">Phone Number</label>

                        </p>
                        <!--<div class="formSubTitle">Personal Feed Preferences</div> -->
                        <p class="twitter">

                            <input type="checkbox" name="twitter" id="twitter" />
                            <label for="twitter">Twitter</label>

                        </p>
                        <p class="BBC News">

                            <input type="checkbox" name="bbc" id="bbc" />
                            <label for="bbc">BBC News</label>
                        </p>
                        <p class="submit">
                            <input type="submit" value="Authorise" />
                        </p>
                    </div>
                </form>


            <div id="ft" role="contentinfo"><p>FeedSpeak Footer. Yahoo! Hack Europe, London 2013. Powered by YUI & Twilio.</p></div> 
        </div> 
    </body>
</html>


