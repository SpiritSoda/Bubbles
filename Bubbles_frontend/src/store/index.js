import { createStore } from 'vuex'
import userinfo from './modules/userinfo'
import chatroom from './modules/chatroom'
import localuser from './modules/localuser'
import settings from './modules/settings'
import error from './modules/error'
import tx from './modules/tx'
import global from './modules/global'
import $axios from '../utils/axios'
import $socket from '../utils/socket'

export default new createStore({
  state: {

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
    },
    logout(state) {
      this.commit('chatroom/reset_chatroom', {})
      this.commit('localuser/reset_localuser', {})
      $socket.unsubscribe_all()
      $socket.disconnect()
    }
  },
  actions: {
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
    update_localuser(context, callback) {
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
            $socket.subscribe_all(Object.keys(this.state.localuser.chatrooms))
            callback.on_success()
          }
          else {
            callback.on_error()
          }
        }
      ).catch(
        e => {
          callback.on_error()
        }
      )
    },

    select_chatroom(context, id) {
      this.state.chatroom.selected_room = id;
      context.dispatch('update_onlines', id)
    },

    update_onlines(context){
      let id = this.state.chatroom.selected_room
      if(id <= 0)
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
        }
      )
      .then(
        (response) => {
          let code = response.data.code
          if(code == 0){
            let onlines = response.data.data.onlines
            this.state.chatroom.onlines = onlines;
          }
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
    global
  },
})