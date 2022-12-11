<script>
import LoginView from './components/login/LoginView.vue';
import SessionView from './components/session/SessionView.vue';
import { computed } from 'vue';
import CheckBox from './components/utils/CheckBox.vue';
import RegisterView from './components/register/RegisterView.vue';
import Popup from './components/utils/Popup.vue';
import ModifyUserinfo from './components/session/body/popup/ModifyUserinfo.vue';

export default {
  components: {
    LoginView,
    SessionView,
    CheckBox,
    RegisterView,
    Popup,
    ModifyUserinfo
  },
  data() {
    return {
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
      },
      popup: 0
    }
  },
  methods: {
    reset_popup(){
      this.popup = 0
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
      settings: computed(() => this.settings),
      background_color: computed(() => this.color_style.content_background_color),
      font_color: computed(() => this.color_style.content_font_color),
      shadow_color: computed(() => this.color_style.content_shadow_color),
    }
  },
  mounted() {
    // console.log(encodeURI([1,2]))
    this.$bus.on('setting', (option) => { this.settings[option].value = !this.settings[option].value })
    this.$bus.on('switch_state', (new_state) => { this.state = new_state })
    this.$bus.on('popup', (pop) => { this.popup = pop; this.$refs.popup.show() })
    this.$bus.on('close_popup', (pop) => { this.popup = pop; this.$refs.popup.hide() })
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
  <div class="bubbles" :style="{'color': color_style.content_font_color, 'position': 'relative'}">
    <LoginView v-if="this.state === 0"></LoginView>
    <RegisterView v-else-if="this.state === 1"></RegisterView>
    <SessionView v-else-if="this.state === 2"></SessionView>

    <Popup ref="popup" :on_quit="reset_popup">
      <ModifyUserinfo v-if="this.popup == 1"></ModifyUserinfo>
    </Popup>
  </div>
</template>

<style scoped>
.bubbles{
  width: 100%;
  height: 100%;
}
</style>
