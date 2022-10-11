<script>
import $bus from '../../../utils/eventbus.js'
export default {
    props: ['avatar', 'error_code'],
    data() {
        return {
            username: '',
            msg: ''
        }
    },
    methods: {
        submit() {
            if (this.username === '') {
                $bus.emit('error', 1);
            }
            else if (this.avatar === -1) {
                $bus.emit('error', 2);
            }
            else {

            }
        }
    },
    computed: {
        error_msg() {
            if (this.error_code == 1)
                this.msg = 'Username can not be empty ...'
            else if (this.error_code == 2)
                this.msg = 'Don\'t forget to choose a user icon ..'
            return this.msg
        }
    }
}
</script>

<template>
    <div class="user-info-wrapper">
        <form action="#" class="user-info">
            <button class="login-btn" @click="submit">
                <a href="javascript:;">
                    <i class="fas fa-arrow-right"></i>
                </a>
            </button>
            <input type="text" class="username" v-model="username" :class="{'shake': error_code == 1}">
        </form>
        <span class="error_msg" :style="{'opacity': error_code > 0 ? 1 : 0}">{{error_msg}}</span>
    </div>
</template>

<style scoped>
.user-info-wrapper {
    height: 40px;
    line-height: 40px;
    margin: 20px auto;
    text-align: center;

    position: absolute;
    top: 295px;
    left: 0;
    right: 0;
    margin: auto;
}

.username {
    width: 200px;
    height: 32px;
    line-height: 32px;
    border: 3px solid rgb(124, 179, 255, .3);
    border-radius: 16px;
    background-color: rgba(255, 255, 255, .9);
    overflow: hidden;
    outline: none;
    padding: 0px 10px;

    font-size: 14px;
    line-height: 1.6;
    font-family: inherit;
    text-align: center;

    position: absolute;
    top: calc(50% - 19px);
    left: 0;
    right: 0;
    margin: auto;
    transition: all .3s;
}

.username:focus {
    border-color: rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1);
}

.login-btn {
    width: 34px;
    height: 34px;
    border-radius: 50%;
    border: 2px solid transparent;
    text-align: center;
    background-color: rgba(255, 255, 255, .9);
    overflow: hidden;
    padding: 0;

    position: absolute;
    top: calc(50% - 19px);
    left: 280px;
    right: 0;
    margin: auto;

    box-sizing: content-box;

    box-shadow: 0 0 5px rgb(124, 179, 255, .3);
    outline: none;

    transition: all .3s;
}
.login-btn a{
    width: 34px;
    height: 34px;
    line-height: 34px;
    display: block;
}
.login-btn i {
    color: #424242;
    text-align: center;
    transition: all .3s;
}

.login-btn:hover {
    background-color: rgba(255, 255, 255, 1);
    border-color: rgb(124, 179, 255);
}

.login-btn:hover i {
    color: rgb(124, 179, 255);
}

.login-btn:hover~.username {
    background-color: rgba(255, 255, 255, .6);
}

.error_msg {
    font-size: 12px;
    height: 20px;
    line-height: 20px;
    color: rgba(255, 255, 255, .8);

    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: 40px;

    opacity: 0;
    transition: all .2s;
    ;
}
</style>