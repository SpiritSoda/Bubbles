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
                3001: chatroom::refresh messages fail
                4000: modify profile::timeout
                4001: modify profile::password too short
                4002: modify profile::confirmed password not match
                4003: modify profile::password empty
                4004: modify profile::username empty
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