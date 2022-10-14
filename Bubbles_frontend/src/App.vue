<script>
import LoginView from './components/login/LoginView.vue';
import SessionView from './components/session/SessionView.vue';
import $bus from './utils/eventbus.js';
import { computed } from 'vue';

export default {
  components: {
    LoginView,
    SessionView
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
      ]
    }
  },
  methods: {
    set_error_code(error) {
      this.error_code = error
      clearTimeout(this.timer_id)
      this.timer_id = setTimeout(() => { this.error_code = 0 }, 1500)
    }
  },
  provide() {
    return {
      error_code: computed(() => this.error_code),
      onlines: computed(() => this.onlines),
      messages: computed(() => this.messages),
      local_id: computed(() => this.local_id),
    }
  },
  mounted() {
    $bus.on('error', (error_code) => { this.set_error_code(error_code) })
    $bus.on('send', (content) => {this.messages.push({id: this.local_id, content: content})})
  }
}
</script>
  
<template>
  <div>
    <LoginView v-if="this.state === 0"></LoginView>
    <SessionView v-else-if="this.state === 1"></SessionView>
  </div>
</template>