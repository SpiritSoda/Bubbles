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
              1. empty username
              2. unselected usericon
      */
      error_code: 0,
      timer_id: 0
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
      error_code: computed(() => this.error_code)
    }
  },
  mounted() {
    $bus.on('error', (error_code) => { this.set_error_code(error_code) })
  }
}
</script>
  
<template>
  <div>
    <!-- <LoginView></LoginView> -->
    <SessionView></SessionView>
  </div>
</template>