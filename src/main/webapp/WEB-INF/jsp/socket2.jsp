<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>广播式WebSocket</title>
    <script src="//cdn.sockjs.org/sockjs-0.3.min.js"></script>
    <script src="//cdn.bootcss.com/stomp.js/2.3.3/stomp.js"></script>
	<script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
</head>
<body onload="disconnect()">
<noscript><h2 style="color: #e80b0a;">Sorry，浏览器不支持WebSocket</h2></noscript>
<div>
    <div>
        <button id="connect" onclick="connect();">连接</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">断开连接</button>
    </div>

    <div id="conversationDiv">
        <label>输入你的名字</label><input type="text" id="name"/>
        <button id="sendName" onclick="sendName();">发送</button>
        <p id="response"></p>
    </div>
</div>
<script type="text/javascript">
    var stompClient = null;
    function setConnected(connected) {
        document.getElementById("connect").disabled = connected;
        document.getElementById("disconnect").disabled = !connected;
        document.getElementById("conversationDiv").style.visibility = connected ? 'visible' : 'hidden';
//        $("#connect").disabled = connected;
//        $("#disconnect").disabled = !connected;
        $("#response").html();
    }
    function connect() {
        var socket = new SockJS('/endpointLi');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected:' + frame);
            stompClient.subscribe('/topic/getResponse', function (response) {
                showResponse(JSON.parse(response.body).responseMessage);
            })
        });
    }
    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log('Disconnected');
    }
    function sendName() {
        var name = $('#name').val();
        console.log('name:' + name);
        stompClient.send("/welcome", {}, JSON.stringify({'name': name}));
    }
    function showResponse(message) {
        $("#response").html(message);
    }
</script>
</body>
</html>