import Stomp from "stompjs"
import SockJS from 'sockjs-client/dist/sockjs.min.js';

// var stomp = Stomp.over(new SockJS("127.0.0.1:8080"))
var stomp = null
var subscribes = {

}
var active = false

function connect(token, on_connect, on_error) {
    var url = "https://b2884t1064.oicp.vip/chat?" + "token=" + token
    var socket = new SockJS(url)
    stomp = Stomp.over(socket)
    stomp.connect(token, "", () => { active = true; on_connect() }, on_error)
}
function subscribe(chatroom) {
    if (!active)
        return
    var sub = "/chat/chatroom" + chatroom
    if (subscribes[chatroom])
        unsubscribe(chatroom)
    subscribes[chatroom] = stomp.subscribe(sub, function (res) {
        console.log("Subscribed chatroom " + chatroom)
    });
}
function unsubscribe(chatroom) {
    if (!active)
        return
    stomp.unsubscribe(subscribes[chatroom].id);
    subscribes[chatroom] = null
}
function disconnect(callback) {
    stomp.disconnect(callback)
    active = false
}
function subscribe_all(chatrooms) {
    if (!active)
        return
    // console.log(chatrooms)
    // console.log(Object.keys(subscribes))
    let id = 0
    for (id of chatrooms) {
        if (!subscribes[id])
            subscribe(id)
    }
    for (id in subscribes) {
        if (chatrooms.indexOf(id) == -1)
            unsubscribe(id)
    }
}
function unsubscribe_all() {
    if (!active)
        return
    for (id in subscribes) {
        unsubscribe(id)
    }
}
function is_active() {
    return active
}

const $socket = {
    connect,
    subscribe,
    unsubscribe,
    disconnect,
    subscribe_all,
    unsubscribe_all,
    is_active
}
export default $socket
