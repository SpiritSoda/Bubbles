<script>
import LoginView from './components/login/LoginView.vue';
import SessionView from './components/session/SessionView.vue';
import $bus from './utils/eventbus.js';
import { computed } from 'vue';
import Button from './components/utils/Button.vue';

export default {
  components: {
    LoginView,
    SessionView,
    Button
  },
  data() {
    return {
      /* 
        error code: 
          1: empty username
          2: unselected usericon
          3: refresh messages fail
      */
      error_code: 0,
      timer_id: 0,
      /*
        instance state: 
          0: waiting for logining in
          1: logined in
       */
      state: 1,
      local_id: 7,
      onlines: [
        { id: 1, icon: 1, username: 'Kazuha' },
        { id: 2, icon: 2, username: 'Ayaka' },
        { id: 3, icon: 3, username: 'Kaeya' },
        { id: 4, icon: 4, username: 'Ganyu' },
        { id: 5, icon: 5, username: 'Tartarlia' },
        { id: 6, icon: 6, username: 'Zhongli' },
        { id: 7, icon: 7, username: 'You Know Who' },
      ],
      messages: [
        { id: 7, content: 'Avada Kedavra' },
        { id: 6, content: 'Stabilized!' },
        { id: 4, content: 'XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD' },
        { id: 5, content: '~' },
        { id: 2, content: 'Hehehehehe...' },
        { id: 7, content: '(╯‵□′)╯︵┻━┻' }
      ],
      settings: {
        dark_mode: { name: 'Dark Mode', value: false },
        hide_username: { name: 'Hide usrname', value: false }
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
      onlines: computed(() => this.onlines),
      messages: computed(() => this.messages),
      local_id: computed(() => this.local_id),
      settings: computed(() => this.settings),
      background_color: computed(() => this.color_style.content_background_color),
      font_color: computed(() => this.color_style.content_font_color),
      shadow_color: computed(() => this.color_style.content_shadow_color)
    }
  },
  mounted() {
    $bus.on('error', (error_code) => { this.set_error_code(error_code) })
    $bus.on('setting', (option) => { this.settings[option].value = !this.settings[option].value })
    $bus.on('send', (content) => { if (content === '') return; else this.messages.push({ id: this.local_id, content: content }) })
  },
}
</script>
  
<template>
  <div :style="{'color': color_style.content_font_color}">
    <LoginView v-if="this.state === 0"></LoginView>
    <SessionView v-else-if="this.state === 1"></SessionView>
  </div>
</template>
