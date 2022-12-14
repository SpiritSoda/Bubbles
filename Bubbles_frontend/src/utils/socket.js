import Stomp from "stompjs"
import SockJS from 'sockjs-client/dist/sockjs.min.js';

// var stomp = Stomp.over(new SockJS("127.0.0.1:8080"))
var stomp = null
var subscribes = {

}
var self = null
var active = false
var interval = false

function connect(token, on_connect, on_error) {
    var url = "https://b2884t1064.oicp.vip/chat?" + "token=" + token
    var socket = new SockJS(url)
    stomp = Stomp.over(socket)
    stomp.connect(token, "", () => { active = true; on_connect() }, on_error)
    
    interval = setInterval(() => {
        if(stomp == null)
            return;
        if(stomp != null && stomp.connected)
            return;
        unsubscribe_all()
        active = false
        var url = "https://b2884t1064.oicp.vip/chat?" + "token=" + token
        var socket = new SockJS(url)
        stomp = Stomp.over(socket)
        stomp.connect(token, "", () => { active = true; on_connect() })
    }, 2000)
}
function subscribe(chatroom, callback) {
    if (!active)
        return
    var sub = "/chat/chatroom/" + chatroom
    if (subscribes[chatroom])
        unsubscribe(chatroom)
    subscribes[chatroom] = stomp.subscribe(sub, function (res) {
        callback(res)
    });
}
function subscribe_self(id, callback) {
    if (!active)
        return
    if(self && stomp && stomp.connected)
        stomp.unsubscribe(self.id)
    var sub = "/user/" + id + "/receive"
    self = stomp.subscribe(sub, function (res) {
        callback(res)
    });
}
function unsubscribe(chatroom) {
    if (!active)
        return
    if(subscribes[chatroom] == null)
        return
    stomp.unsubscribe(subscribes[chatroom].id);
    delete subscribes[chatroom]
}
function disconnect(callback) {
    stomp.disconnect(callback)
    clearInterval(interval)
    active = false
}
function subscribe_all(chatrooms, callback) {
    if (!active)
        return
    // console.log(chatrooms)
    // console.log(Object.keys(subscribes))
    let id = 0
    for (id of chatrooms) {
        if (!subscribes[id])
            subscribe(id, callback)
    }
    for (id in subscribes) {
        if (chatrooms.indexOf(id) == -1)
            unsubscribe(id)
    }
}
function unsubscribe_all() {
    if (!active)
        return
    let id = 0
    for (id in subscribes) {
        unsubscribe(id)
    }
    if(self != null){
        stomp.unsubscribe(self.id)
        self = null
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
    is_active,
    subscribe_self
}
export default $socket
