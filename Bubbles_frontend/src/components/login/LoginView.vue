<script>
import LoginPanel from './LoginPanel.vue'
import HorizontalSplit from '../utils/HorizontalSplit.vue';
export default {
    components: {
    LoginPanel,
    HorizontalSplit
},
    data() {
        return {
            color: 'rgba(255,255,255, .8)'
        }
    },
    methods: {
        register(){
            this.$bus.emit("switch_state", 1);
        }
    }
}
</script>
<template>
    <div>
        <div class="login-pannel-wrapper">
            <LoginPanel></LoginPanel>
        </div>
        <a href="javascript:;" class="register-entry" @click="register">
            <span>Register</span>
            <HorizontalSplit :length="60" :top="0" :color="color"></HorizontalSplit>
        </a>
    </div>
</template>

<style scoped>
.login-pannel-wrapper{
    width: 500px;
    height: 350px;
    position: absolute;
    left: 0;
    right: 0;
    margin: 100px auto;
}
.register-entry{
    display: block;
    width: 80px;
    height: 22px;
    line-height: 22px;
    position: absolute;
    top: 460px;
    left: 0;
    right: 0;
    margin: auto;
    border-radius: 11px;

    font-size: 14px;
    text-align: center;
    color: rgba(255, 255, 255, .8);
    transition: all .3s;
    animation: appear-up ease 1s;
}
.register-entry:hover{
    color: rgb(124, 179, 255);
    box-shadow: 0 0 5px rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1);
}
</style>