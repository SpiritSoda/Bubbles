<script>
import LoginView from './components/login/LoginView.vue'
import SessionView from './components/session/SessionView.vue';
import $bus from './utils/eventbus.js'

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
  mounted() {
    $bus.on('error', (error_code) => { this.set_error_code(error_code) })
  }
}
</script>
  
<template>
  <div>
    <!-- <LoginView :error_code="error_code"></LoginView> -->
    <SessionView :error_code="error_code"></SessionView>
  </div>
</template>