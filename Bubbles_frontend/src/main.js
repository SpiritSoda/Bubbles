import { createApp } from 'vue'
import App from './App.vue'

import './assets/reset.css'
import './assets/main.css'
import './iconfont/css/all.css'
import $axios from './utils/axios'
import $bus from './utils/eventbus'
import $md5 from './utils/md5'

var app = createApp(App)
app.config.unwrapInjectedRef = true
app.config.globalProperties.$axios = $axios
app.config.globalProperties.$bus = $bus
app.config.globalProperties.$md5 = $md5
app.mount('#app')