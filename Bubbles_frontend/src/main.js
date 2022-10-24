import { createApp } from 'vue'
import App from './App.vue'

import './assets/reset.css'
import './assets/main.css'
import './iconfont/css/all.css'
import $axios from './utils/axios'
import $bus from './utils/eventbus'
import $md5 from './utils/md5'
import $socket from './utils/socket'

function debounce(func, wait = 300, immediate = true){
	let timer = null;
	return function anonymouse(...params){
		clearTimeout(timer);
		let now = immediate && !timer;
		timer = setTimeout(() => {
			timer = null;
			!immediate ? func.call(this, ...params) : null;
		},wait);
		now ? func.call(this, ...params) : null;
	}
}

var app = createApp(App)
app.config.unwrapInjectedRef = true
app.config.globalProperties.$axios = $axios
app.config.globalProperties.$bus = $bus
app.config.globalProperties.$md5 = $md5
app.config.globalProperties.$socket = $socket
app.directive("debounce",{
	beforeMount(el,binding){
		let val = binding.value,//获取指令传递的参数
			wait = 500,//wait默认500毫秒
			immediate = false,
			type = 'click',
			params = [],
			func
		if(val == null) return;//如果没有传递任何参数则什么也不做
		if(typeof val !== 'object' && typeof val !== 'function') return ;//如何传递的参数既不是对象也不是函数则也什么都不做
		if(binding.arg) wait = +binding.arg;//获取冒号后面的参数
		if(binding.modifiers && binding.modifiers.immediate) immediate = binding.modifiers.immediate;//获取修饰符
		if(typeof val === 'function') func = val;//如果传递的是函数则直接赋给func
		if(typeof val === 'object'){//如果是对象则对对象进行解析
			func = val.func || function(){};
			type = val.type || 'click';
			params = val.params || [];			
		}
		el.$type = type;
		//此步操作多定义了一个proxy函数目的是可以给func传递参数，同时保证func中的this指向
		el.$handle = debounce(function proxy(...args){
			return func.call(this, ...params.concat(args));
		},wait, immediate);
		el.addEventListener(el.$type, el.$handle);
	},
	unmounted(el){
		el.removeEventListener(el.$type, el.$handle);
	}
});

app.mount('#app')