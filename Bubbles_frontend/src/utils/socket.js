var websock = null;
let rec; //断线重连后，延迟5秒重新创建WebSocket连接  rec用来存储延迟请求的代码
let isConnect = false; //连接标识 避免重复连接

let serial = 0;
var callbacks = [];

var open_callback = undefined

let create_websocket = (token, callback) => {
    try {
        open_callback = callback;
        var ws = "wss://b2884t1064.oicp.vip/chat?" + "token=" + token;
        websock = new WebSocket(ws);
        init_websocket();
    } catch (e) {
        console.log("Failed to connect ...");
        reconnect();
    }
};
let close_websocket = () => {
  websock.close();
};

let reconnect = () => {
    console.log("Trying to reconnect ...");
    if (isConnect) return; 
    rec && clearTimeout(rec);
    rec = setTimeout(function () { 
        create_websocket();
    }, 5000);
};
var heart_check = {
    timeout: 20000,
    timeoutObj: null,

    start: function () {
        this.timeoutObj = setTimeout(function () {
            if (isConnect) websock.send(checkMsg);
        }, this.timeout);
    },

    reset: function () {
        clearTimeout(this.timeoutObj);
        this.start();
    }
};

// 初始化websocket
function init_websocket() {
    websock.onmessage = function (e) {
        websocket_on_message(e)
    }
    websock.onclose = function (e) {
        websocket_on_close(e)
    }
    websock.onopen = function () {
        websocket_on_open()
    }

    websock.onerror = function () {
        console.log('Error occurs on websocket ...')
        isConnect = false;
        reconnect();
    }
}



// 实际调用的方法
function send_message(data, callback) {
    callbacks[serial] = callback;
    data["serial"] = serial;
    serial ++;
    if (websock.readyState === websock.OPEN) {
        websocketsend(data)
    } else if (websock.readyState === websock.CONNECTING) {
        setTimeout(function () {
            send_message(data, callback)
        }, 1000)
    } else {
        setTimeout(function () {
            send_message(data, callback)
        }, 1000)
    }
}

function get_sock(callback) {
    globalCallback = callback
}
// 数据接收
function websocket_on_message(e) {
    console.log(e.data)

    let msg = JSON.parse(decodeUnicode(e.data))

    if (!msg) {
        heart_check.reset();
    } else {
        if (msg.type == 0) {
            // connection build
        } else {
            console.log("websokcet receive message: " + msg)
            callbacks[msg.serial](msg)
            callbacks[msg.serial] = undefined
        }
    }


    function decodeUnicode(str) {
        str = str.replace(/\\/g, "%");
        //转换中文
        str = unescape(str);
        str = str.replace(/%/g, "\\");
        str = str.replace(/\\/g, "");
        return str;
    }
}

function websocketsend(data) {
    console.log("websocket sending message: " + JSON.stringify(data))
    websock.send(JSON.stringify(data))
}

function websocket_on_close(e) {
    isConnect = false ;
    console.log('Connection closed (' + e.code + ')')
}

function websocket_on_open(e) {
    open_callback()
    console.log('Success to connect ...')
}

const $socket = {
    send_message,
    // get_sock,
    create_websocket,
    close_websocket
}
export default $socket
