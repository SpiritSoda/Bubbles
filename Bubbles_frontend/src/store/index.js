import { createStore } from 'vuex'
import userinfo from './modules/userinfo'
import chatroom from './modules/chatroom'
import localuser from './modules/localuser'
import settings from './modules/settings'
import error from './modules/error'
import tx from './modules/tx'
import global from './modules/global'
import file from './modules/file'
import $axios from '../utils/axios'
import $socket from '../utils/socket'
import $bus from '../utils/eventbus'

export default new createStore({
  state: () => {
    return {
      upload_id: -1
    }
  },
  getters: {

  },
  mutations: {
    reset_chatroom(state) {
      this.commit('chatroom/reset_chatroom', {})
      this.dispatch('update_localuser', {
        on_error: () => { },
        on_success: () => { },
      })
      document.title = "Bubbles"
    },
    logout(state) {
      this.commit('chatroom/reset_chatroom', {})
      this.commit('localuser/reset_localuser', {})
      $socket.unsubscribe_all()
      $socket.disconnect()
    },
    save_message(state, msg) {
      this.commit('chatroom/save_message', msg)
      // send message, scroll to bottom
      if (msg.senderId == state.localuser.local_id)
        $bus.emit('scroll_to_bottom')
      else
        $bus.emit('new_message', msg)
    }
  },
  actions: {
    // edit self user info
    edit_userinfo(context, payload) {
      // send new info to server first
      let token = this.state.localuser.token;
      $axios.post('/api/user/edit', payload.data,
        {
          headers: {
            'token': token
          }
        }
      ).then(
        response => {
          let code = response.data.code
          if (code != 0) {
            payload.on_error(code)
          }
          else {
            this.dispatch('userinfo/fetch_userinfo', this.state.localuser.local_id)
            payload.on_success()
          }
        }
      ).catch(
        e => {
          payload.on_error(-1)
        }
      )
    },
    // update self user info
    update_localuser(context, callback) {
      let on_error = callback.on_error ? callback.on_error : () => { }
      let on_success = callback.on_success ? callback.on_success : () => { }
      $axios.get('/api/user/selfInfo', {
        headers: {
          'token': this.state.localuser.token
        }
      }).then(
        response => {
          let code = response.data.code;
          if (code == 0) {
            this.commit('localuser/save_localuser', response.data.data)
            let userinfo = {
              id: response.data.data.id,
              avatar: response.data.data.avatar,
              username: response.data.data.username
            }
            this.commit('userinfo/save_user', userinfo)
            $socket.subscribe_all(Object.keys(this.state.localuser.chatrooms), (res) => {
              this.dispatch('process_chatroom_message', res)
            })
            on_success()
          }
          else {
            on_error()
          }
        }
      ).catch(
        e => {
          on_error()
        }
      )
    },

    // select a chatroom
    select_chatroom(context, id) {
      this.state.chatroom.selected_room = id;
      this.state.localuser.has_new_message[id] = 0;
      context.dispatch('update_onlines', id)
      document.title = "Bubbles: " + this.state.localuser.chatrooms[this.state.chatroom.selected_room].title
    },

    // update online users
    update_onlines(context) {
      let id = this.state.chatroom.selected_room
      if (id <= 0)
        return
      $axios.post(
        '/api/chatroom/onlines',
        {
          id
        },
        {
          headers: {
            'token': this.state.localuser.token
          }
        })
        .then(
          (response) => {
            let code = response.data.code
            if (code == 0) {
              let onlines = response.data.data.onlines
              this.state.chatroom.onlines = onlines;
            }
          }
        )
    },
    // process websocket message
    process_chatroom_message(context, res) {
      let message = JSON.parse(res.body)
      if (message.type == 2) {
        let msg = message.data;
        if (msg.chatroomId != this.state.chatroom.selected_room) {
          this.commit('localuser/has_new_message', msg.chatroomId)
          return;
        }
        msg['state'] = 0
        context.commit('save_message', msg)
      }
    },

    // send message to chatroom
    send_message(context, payload) {
      if (this.state.chatroom.selected_room == 0)
        return;
      let token = this.state.localuser.token
      let msg = {
        type: 0,
        content: payload.content,
        chatroomId: this.state.chatroom.selected_room
      }
      let on_error = payload.on_error ? payload.on_error : () => {}
      let on_success = payload.on_success ? payload.on_success : () => {}
      $axios.post(
        '/api/chat/send',
        msg,
        {
          headers: {
            'token': token
          }
        })
        .then(
          (response) => {
            let code = response.data.code;
            // console.log(response.data)
            if (code != 0){
              on_error()
            }
            else
              on_success()
          }
        )
        .catch(
          (e) => {
            on_error()
          }
        )
    },
    // upload file
    upload_file(context, payload) {
      let id = this.state.upload_id
      this.state.upload_id -= 1
      let task = {
        filename: payload.info.filename,
        type: 'upload',
        filesize: payload.info.filesize,
        id: id,
      }
      this.commit("file/append_task", task)
      // console.log("uploading ...", this.state.file.tasks)

      let token = this.state.localuser.token
      let on_progress = (e) => {
        let progress = e.loaded / e.total
        this.commit("file/update_progress", {
          id,
          progress
        })
        if(e.loaded >= e.total)
            this.commit("file/finish_task", {id})
      }
      let on_error = payload.on_error ? payload.on_error : (e) => {}
      $axios.upload_with_progress(
        '/api/chat/uploadFile',
        payload.data,
        {
          headers: {
            'token': token,
            'Content-Type': 'multipart/form-data'
          }
        },
        on_progress)
        .then(
          (response) => {
            console.log(response)
          }
        )
        .catch(
          (e) => {
            on_error(e)
          }
        )
    },
    // download file
    download_file(context, payload) {
      let task = {
        filename: payload.info.filename,
        type: 'download',
        filesize: payload.info.filesize,
        id: payload.id,
      }
      this.commit("file/append_task", task)
      // console.log("downloading ...", this.state.file.tasks)
      
      let token = this.state.localuser.token
      let on_progress = (e) => {
        let progress = e.loaded / e.total
        this.commit("file/update_progress", {
          id: payload.id,
          progress
        })
        if(e.loaded >= e.total)
            this.commit("file/finish_task", {id: payload.id})
      }
      let on_error = payload.on_error ? payload.on_error : (e) => {}
      $axios.download_with_progress(
        '/api/chat/downloadFile',
        {
          params: {
            message: payload.id
          },
          headers: {
            'token': token
          },
        },
        on_progress
        ).then(
          (response) => {
            const blob = new Blob([response.data]);
            const filename = decodeURIComponent(response.headers["content-disposition"].split(";")[1].split("filename=")[1])
            // console.log(filename)
            let url = window.URL.createObjectURL(blob);
            let a = document.createElement('a');
            a.href = url;
            a.download = payload.info.filename;
            a.click();
            window.URL.revokeObjectURL(url);
          }
        )
        .catch(
          (e) => {
            on_error(e)
          }
        )
    },
    // get history message
    get_message(context, payload) {
      if (this.state.chatroom.selected_room == 0)
        return;
      let on_error = payload.on_error ? payload.on_error : () => { }
      let on_success = payload.on_success ? payload.on_success : (cnt) => { }
      $axios.post(
        '/api/chat/get',
        {
          startId: (payload.startId ? payload.startId : 0),
          cnt: this.state.global.message_per_post,
          chatroomId: this.state.chatroom.selected_room
        },
        {
          headers: {
            'token': this.state.localuser.token
          }
        }
      ).then(
        (response) => {
          let code = response.data.code
          if (code == 0) {
            this.commit('chatroom/save_messages_to_front', response.data.data.msg)
            on_success(response.data.data.count)
          }
          else {
            on_error()
          }
        }
      )
        .catch(
          (e) => {
            on_error()
          }
        )
    },
    ban_user(context, payload){
      let on_error = payload.on_error ? payload.on_error : () => { }
      let on_success = payload.on_success ? payload.on_success : (cnt) => { }
      $axios.post('/api/chatroom/ban',
      {
        userId: payload.userId,
        chatroomId: payload.chatroomId
      },
      {
        headers: {
          token: this.state.localuser.token
        }
      }).then(
        (response) => {
          on_success(response)
        }
      ).catch(
        (e) => {
          console.log(e)
          on_error()
        }
      )
    }
  },
  modules: {
    userinfo,
    chatroom,
    localuser,
    settings,
    error,
    tx,
    global,
    file
  },
})