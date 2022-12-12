import { createStore } from 'vuex'
import userinfo from './modules/userinfo'
import chatroom from './modules/chatroom'
import localuser from './modules/localuser'
import settings from './modules/settings'
import error from './modules/error'
import tx from './modules/tx'
import global from './modules/global'
import $axios from '../utils/axios'

export default new createStore({
  state: {

  },
  getters: {

  },
  mutations: {
    reset_chatroom(state) {
      this.commit('chatroom/reset_chatroom', {})
    },
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
          if (response.data.code != 0) {
            payload.on_error()
          }
          else {
            this.dispatch('userinfo/fetch_userinfo', this.state.localuser.local_id)
            payload.on_success()
          }
        }
      ).catch(
        e => {
          payload.on_error()
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
              if (response.data.code != 0) {
                  callback.on_error()
              }
              else {
                  this.commit('localuser/save_localuser', response.data.data)
                  let userinfo = {
                    id: response.data.data.id,
                    avatar: response.data.data.avatar,
                    username: response.data.data.username
                  }
                  this.commit('userinfo/save_user', userinfo)
                  callback.on_success()
              }
          }
      ).catch(
          e => {
              callback.on_error()
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