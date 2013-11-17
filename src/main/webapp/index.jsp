<%-- 

 Copyright [2013] [Marco Berri marcoberri@gmail.com]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and limitations under the License.

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MBfASTuRL V.2</title>
        <meta name="author" content="Marco Berri"/>
        <meta name="description" content="generazione url brevi, app in java, MongoDB, NodeJs" />

        <meta name="keywords" content="java,url,web,app,short url,url brevi" />

        <style type="text/css">

            body,p,div,h1,h2,h3{
                margin:0;
                padding:0;
            }

            h1{text-decoration:underline;}

            a,h1,h2,h3{color:#cc3300;}

            body{
                font-family:Verdana,Arial,Helvetica,sans;
                font-size:12px;
                background-image: url("img/pattern.gif");
            }

            ul{list-style-image:url("img/liststyleimage.png");}
            .wrapper{
                margin:40px 80px;
            }

            .header,.welcome{
                margin: 20px 0;
            }

            .form-container{
                width:500px;
                padding: 20px 20px 30px 50px;
                margin-bottom:30px;
                border:2px solid black;
                background: #eeeeee;
            }

            .footer{
                padding-bottom:40px;
                font-size:20px;
            }

        </style>

        <script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>

        <script type="text/javascript" language="JavaScript">

            function checkURL(value) {
                var urlregex = new RegExp("^(http:\/\/www.|https:\/\/www.|http:\/\/|https:\/\/){1}([0-9A-Za-z]+\.)");
                if (urlregex.test(value)) {
                    return (true);
                }
                return (false);
            }

            function submit_this() {

                var d = $("#url").val();

                if (!checkURL(d)) {
                    alert("url invalid format");
                    return;
                }

                $('#loadingmessage').show();

                $.ajax({
                    url: "s",
                    data: {url: d},
                    dataType: "json",
                    type: 'POST'
                }).done(function(data) {
                    $('#result').attr('value', data.urlComplete);
                    $('#loadingmessage').hide();
                })
                        .fail(function(jqXHR, textStatus) {
                    $('#loadingmessage').hide();
                    alert("Request failed: " + textStatus);

                });

            }
        </script>

    </head>

    <body>

        <div class="wrapper">

            <div class="header">
                <h1>MBFastUrl v.2.0</h1>
            </div>

            <img src="q/0p"/>

            <br />

            <div class="welcome">
                <h2>Benvenuti sul sito di MbFastUrl V.2</h2>
                <br/>
                il progetto è disponibile in forma sorgente a <a href="/r/9i">questo</a> indirizzo con relativa <a href="/r/e7">documentazione</a> e <a href="/r/8w">sorgenti</a><br/><br/>

            </div>

            <div class="form-container">

                <h3>Genera il tuo Url Breve</h3>
                <br/>

                <form method="post">

                    <input id="type" type="hidden" name="type" value="xml"/>

                    <div>
                        <b>Url:</b><br />
                        <input id="url" style="width: 450px;"  type="text" name="url" value="http://"/><input type="button" onclick="submit_this()" value="Genera Url Breve"/>
                        <div id='loadingmessage' style='display:none'>
                            <img src='img/loading.gif' width="40px" height="40px"/>
                        </div>                        
                    </div>

                    <br />

                    <div>
                        <b>Risultato</b><br />
                        <input type="text" style="width: 450px;" name="result" id="result"/>
                    </div>

                </form>

            </div>

            <br/><br/>

            <div class="footer">

                <a href="mailto:marco.berri@gmail.com">scrivetemi!</a>

                <br/><br/>

                <a href="/r/49">M.Berri</a>

            </div>

        </div>

    </body>

</html>

<%--

<!DOCTYPE html>
<html>
    <head>
        <title>Bootstrap 101 Template</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->


        <script type="text/javascript" language="JavaScript">

            function checkURL(value) {
                var urlregex = new RegExp("^(http:\/\/www.|https:\/\/www.|http:\/\/|https:\/\/){1}([0-9A-Za-z]+\.)");
                if (urlregex.test(value)) {
                    return (true);
                }
                return (false);
            }

            function submit_this() {

                var d = $("#url").val();

                if (!checkURL(d)) {
                    alert("url invalid format");
                    return;
                }

                $('#loadingmessage').show();

                $.ajax({
                    url: "s",
                    data: {url: d},
                    dataType: "json",
                    type: 'POST'
                }).done(function(data) {
                    $('#result').attr('value', data.urlComplete);
                    $('#loadingmessage').hide();
                })
                        .fail(function(jqXHR, textStatus) {
                            $('#loadingmessage').hide();
                            alert("Request failed: " + textStatus);

                        });

            }
        </script>


    </head>
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">MBFastUrl v.2</a>
                </div>
            </div>
        </div>

        <div class="container">
            <!-- Example row of columns -->
            <div class="row">
                <div class="col-md-4">
                    <h2>MBFastUrl v.2</h2>
                    <p>Make your short url!!</p>
                    <p>
                        <img src="http://mbfu.it/q/0p"/>
                        <a class="btn btn-default" href="https://code.google.com/p/mbfasturl2/" role="button">Download App Source code &Gt;</a>
                    
                    </p>
                </div>
            </div>

        </div>
        <div class="row">

            <form class="well" id="start-form">
                <label>Url</label>
                <input id="url" name="url" type="text" class="span3" placeholder="Insert Url">
                <span class="help-inline">Enter a Valid url!!</span>

                <button type="button" onclick="submit_this()" class="btn">Submit</button>

                <div id='loadingmessage' style='display:none'>
                    <img src='img/loading.gif' width="40px" height="40px"/>
                </div>                        

            </form>


        </div>

        <div class="row" x-style="display: none">

            <form class="well" id="result-form">
                <label>Short</label>
                <input type="text" class="span3" id="result" name="result">
            </form>


        </div>

        <script src="https://code.jquery.com/jquery.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
--%>
