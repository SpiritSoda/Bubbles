export default{
    namespaced: true,
    state: () => {
        return {
            timer_id: 0,
            /* 
              error code: 
                error_code > 0: error_occurs
                1000: login::timeout
                1001: login::empty username
                1002: login::user not exist
                1003: login::empty password
                1004: login::password not match

                2001: register::empty username
                2002: register::unselected icon
                2003: register::empty password
                2004: register::confirmed password not match
                2005: register::empty confirmed password
                2006: register::password too short
                2007: register::user exists
                2008: register::username too long

                3001: chatroom::connect fail
                3002: chatroom::no more messages
                3003: chatroom::message too long
                3004: chatroom:file too large
                3005: chatroom:upload error
                3006: chatroom:download error

                4000: modify profile::timeout
                4001: modify profile::password too short
                4002: modify profile::confirmed password not match
                4003: modify profile::password empty
                4004: modify profile::username empty
                4005: modify profile::username exist

                5000: create chatroom::timeout
                5001: create chatroom::passport empty
                5002: create chatroom::title empty
                5003: create chatroom::max user invalid
                5004: create chatroom::icon empty
                5005: create chatroom::comment empty
                5006: create chatroom::passport not valid
                5007: create chatroom::passport expired

                6000: invite friend::timeout
                6001: invite friend::user not exists
                6002: invite friend::user already in chatroom
                6003: invite friend::chatroom full
                6004: invite friend::user are banned
                6005: invite friend::inviter not in chatroom
                6006: invite friend::username empty

                7000: join chatroom::timeout
                7001: join chatroom::user already in chatroom
                7002: join chatroom::chatroom full
                7003: join chatroom::banned
                7004: join chatroom::empty token
                7005: join chatroom::invalid token
            */
            error_code: 0,
        }
    },
    getters: {
      
    },
    mutations: {
      set_error_code(state, error) {
        state.error_code = error
        clearTimeout(state.timer_id)
        state.timer_id = setTimeout(() => { state.error_code = 0 }, 1500)
      }
    },
    actions: {
      
    }
  }