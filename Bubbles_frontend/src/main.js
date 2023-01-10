import { createApp } from 'vue'
import App from './App.vue'
import store from './store'

import './assets/reset.css'
import './assets/main.css'
import './iconfont/css/all.css'
import $axios from './utils/axios'
import $bus from './utils/eventbus'
import $md5 from './utils/md5'
import $socket from './utils/socket'
var app = createApp(App)
app.use(store);
app.config.unwrapInjectedRef = true
app.config.globalProperties.$axios = $axios
app.config.globalProperties.$bus = $bus
app.config.globalProperties.$md5 = $md5
app.config.globalProperties.$socket = $socket

app.mount('#app')