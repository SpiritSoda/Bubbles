import { createApp } from 'vue'
import App from './App.vue'

import './assets/reset.css'
import './assets/main.css'
import './iconfont/css/all.css'

var app = createApp(App)
app.config.unwrapInjectedRef = true
app.mount('#app')