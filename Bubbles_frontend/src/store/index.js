import {createStore} from 'vuex'
import userinfo from './modules/userinfo'
import chatroom from './modules/chatroom'
import localuser from './modules/localuser'
import settings from './modules/settings'
import error from './modules/error'
import tx from './modules/tx'

export default new createStore({
  //数据，相当于data
  state: {

  },
  getters: {
    
  },
  //里面定义方法，操作state方发
  mutations: {
    reset_chatroom(state){
      this.commit('chatroom/reset_chatroom', {})
    }
  },
  // 操作异步操作mutation
  actions: {
    
  },
  modules: {
    userinfo,
    chatroom,
    localuser,
    settings,
    error,
    tx
  },
})