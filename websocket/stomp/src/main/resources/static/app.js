var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });

        stompClient.subscribe('/topic/greetings2', function (greeting) {
            console.log("out --" + new Date());

            showGreeting(JSON.parse(greeting.body).content);

            sleep2(10000).then(() => {
                console.log("in --" + new Date());
            });
        });

        stompClient.subscribe('/topic/greetings3', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });

        stompClient.subscribe('/topic/greetings4', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}


function sleep2(ms) {
    return new Promise(function(resolve, reject) {
        setTimeout(resolve, ms)
    })
}

function sleep() {
    console.log("start" + new Date());
    sleep2(10000).then(() => {
        console.log("end1 --" + new Date());
    });
    console.log("end2 --" + new Date());

}

sleep()


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val(), first: true}));
}

function sendName2() {
    stompClient.send("/app/hello2", {}, JSON.stringify({'name': $("#name2").val()}));
}

function sendName3() {
    stompClient.send("/app/hello3/124abc", {}, JSON.stringify({'name': $("#name3").val()}));
}

function sendName4() {
    stompClient.send("/app/hello4", {}, JSON.stringify({'name': $("#name4").val()}));
}
function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#send2" ).click(function() { sendName2(); });

    $( "#send3" ).click(function() { sendName3(); });
    $( "#send4" ).click(function() { sendName4(); });
});

