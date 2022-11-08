<script>
import LoginView from './components/login/LoginView.vue';
import SessionView from './components/session/SessionView.vue';
import { computed } from 'vue';
import Button from './components/utils/Button.vue';
import RegisterView from './components/register/RegisterView.vue';

export default {
  components: {
    LoginView,
    SessionView,
    Button,
    RegisterView
  },
  data() {
    return {
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
      */
      error_code: 0,
      /*
          transaction signal that need to be done
      */
      signals: {
        // update login pannel after register success
        register_update: {
          required: false,
          data: null
        }
      },
      timer_id: 0,
      /*
        instance state: 
          0: login in
          1: register
          2: chatroom
       */
      state: 0,
      settings: {
        dark_mode: { name: 'Dark Mode', value: false },
        hide_username: { name: 'Hide timestamp', value: false }
      }
    }
  },
  methods: {
    set_error_code(error) {
      this.error_code = error
      clearTimeout(this.timer_id)
      this.timer_id = setTimeout(() => { this.error_code = 0 }, 1500)
    }
  },
  computed: {
    color_style() {
      if (this.settings.dark_mode.value) {
        return {
          content_background_color: '#222222',
          content_font_color: '#e2e2e2',
          content_shadow_color: 'rgba(255, 255, 255, .6)'
        }
      }
      else {
        return {
          content_background_color: '#fff',
          content_font_color: '#000',
          content_shadow_color: 'rgba(0, 0, 0, .3)'
        }
      }
    }
  },
  provide() {
    return {
      error_code: computed(() => this.error_code),
      settings: computed(() => this.settings),
      background_color: computed(() => this.color_style.content_background_color),
      font_color: computed(() => this.color_style.content_font_color),
      shadow_color: computed(() => this.color_style.content_shadow_color),
      signals: computed(() => this.signals)
    }
  },
  mounted() {
    // console.log(encodeURI([1,2]))
    this.$bus.on('error', (error_code) => { this.set_error_code(error_code) })
    this.$bus.on('signal_done', (signal) => { this.signals[signal].required = false })

    this.$bus.on('setting', (option) => { this.settings[option].value = !this.settings[option].value })
    this.$bus.on('switch_state', (new_state) => { this.state = new_state })
    this.$bus.on('registered', (user_info) => { this.state = 0; this.signals.register_update.required = true; this.signals.register_update.data = user_info })
    if(localStorage.getItem("token") != null){
      this.state = 2
    }
  },
  beforeDestroy() {
    this.$socket.close_websocket()
  },
}
</script>
  
<template>
  <div :style="{'color': color_style.content_font_color}">
    <LoginView v-if="this.state === 0"></LoginView>
    <RegisterView v-if="this.state === 1"></RegisterView>
    <SessionView v-else-if="this.state === 2"></SessionView>
  </div>
</template>
