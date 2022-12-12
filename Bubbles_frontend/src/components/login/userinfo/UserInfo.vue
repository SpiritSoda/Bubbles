<script>
export default {
    props: ['avatar'],
    data() {
        return {
            username: '',
            password: '',
            data: '',
            msg: '',
            token: '',
            /*
                0: waiting for username input
                1: waiting for password input
            */
            state: 0,
            waiting: false
        }
    },
    methods: {
        submit() {
            if (this.state == 0) {
                if (this.data === '') {
                    this.$store.commit('error/set_error_code', 1001);
                }
                else {
                    this.waiting = true;
                    this.$axios.post('/api/exist', {
                        username: this.data
                    }).then(
                        response => {
                            this.waiting = false;
                            if (!response.data.data.result) {
                                this.$store.commit('error/set_error_code', 1002);
                            }
                            else {
                                this.$emit('set_avatar', response.data.data.avatar)
                                this.switch_to_state_password();
                            }
                        }
                    ).catch(
                        e => {
                            this.waiting = false;
                            this.$store.commit('error/set_error_code', 1000);
                        }
                    )
                }
            }
            else
                if (this.state == 1) {
                    if (this.data === '') {
                        this.$store.commit('error/set_error_code', 1003);
                    }
                    else {
                        // console.log(this.$md5(this.data))
                        this.waiting = true;
                        this.$axios.post('/api/login', {
                            username: this.username,
                            password: this.$md5(this.data),
                            avatar: this.avatar
                        }).then(
                            response => {
                                this.waiting = false;
                                let code = response.data.code;
                                if(code == 0){
                                    this.token = response.data.data.token
                                    this.on_login();
                                }
                                else if(code == 3){
                                    this.$store.commit('error/set_error_code', 1004);
                                }
                            }
                        ).catch(
                            e => {
                                this.waiting = false;
                                this.$store.commit('error/set_error_code', 1000);
                            }
                        )
                    }
                }
        },
        switch_to_state_password() {
            this.username = this.data;
            this.password = '';
            this.state = 1;
            document.getElementById('data').className = 'data fold-back'
            setTimeout(() => {
                document.getElementById('data').className = 'data'
            }, 500)
            this.data = '';
        },
        switch_to_state_username() {
            this.data = this.username;
            this.password = '';
            this.state = 0;
            document.getElementById('data').className = 'data fold-back'
            setTimeout(() => {
                document.getElementById('data').className = 'data'
            }, 500)
        },
        register_update(user_info){
            this.$emit('set_avatar', user_info.avatar);
            this.data = user_info.username 
            console.log(user_info.username, user_info.avatar)
            this.switch_to_state_password()
        },
        on_login(){
            localStorage.setItem("token", this.token)
            this.$store.commit('localuser/save_token', this.token)
            this.$bus.emit('switch_state', 2)
        }
    },
    computed: {
        error_code(){
            return this.$store.state.error.error_code
        },
        error_msg() {
            if (this.error_code == 1000)
                this.msg = 'Can not connect to server ...'
            else if (this.error_code == 1001)
                this.msg = 'Username can not be empty ...'
            else if (this.error_code == 1002)
                this.msg = 'User does not exist ...'
            else if (this.state == 1)
                if (this.error_code == 1003)
                    this.msg = 'Password can not be empty ...'
                else if (this.error_code == 1004)
                    this.msg = 'Password does not match the user ...'
            return this.msg
        },
        info_msg() {
            if (this.state == 0)
                return 
            else if (this.state == 1)
                return 'Hello, ' + username;
        },
        placeholder() {
            if (this.state == 0)
                return 'Username'
            else if (this.state == 1)
                return 'Password';
        },
    },
    mounted(){
        if(this.$store.state.tx.txs.register_update.required){
            this.register_update(this.$store.state.tx.txs.register_update.data);
            this.$store.commit('tx/finish_tx', 'register_update')
        }
    }
}
</script>

<template>
    <div class="user-info-wrapper">
        <div class="info-msg-wrapper">
            <span class="username" :style="{'opacity': 1 - state}" :class="{'reverse': state}">Tell us your name:</span>
            <span class="password" :style="{'opacity': state}" :class="{'reverse': state}">{{'Hello, ' + username}}</span>
        </div>

        <form onsubmit="return false" class="user-info">
            <button class="login-btn btn" @click="submit" :class="{'waiting': waiting}">
                <a href="javascript:;">
                    <i class="fas" :class="{'fa-arrow-right': !waiting, 'fa-undo rotate': waiting}"></i>
                </a>
            </button>
            <button class="back-btn btn" @click="switch_to_state_username" :style="{'opacity': state}">
                <a href="javascript:;">
                    <i class="fas fa-angle-double-left"></i>
                </a>
            </button>
            <input id="data" :type="this.state == 0 ? 'text': 'password'" class="data" v-model="data"
                :class="{'shake': error_code >= 1001 && error_code <= 1004}"
                :placeholder="placeholder">
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

.info-msg-wrapper {
    text-align: center;
    font-size: 24px;
    line-height: 1.6;
    font-family: inherit;
    color: #ffffff;

    position: absolute;
    top: -50px;
    left: 0;
    right: 0;
    margin: auto;
}
.info-msg-wrapper .username{
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
}
.info-msg-wrapper .password{
    width: 300px;
    text-align: center;
    position: absolute;
    left: 250px;
    right: 0;
    margin: auto;
}
.info-msg-wrapper span{
    transition: all .3s;
}
.info-msg-wrapper span.reverse{
    transform: translateX(-150px);
}

.user-info {
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;

}

.data {
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
    left: 0;
    right: 0;
    margin: auto;
    transition: all .3s;
}

.data:focus {
    border-color: rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1);
}
.data:hover {
    border-color: rgb(124, 179, 255);
}

.btn {
    width: 34px;
    height: 34px;
    border-radius: 50%;
    border: 2px solid transparent;
    text-align: center;
    background-color: rgba(255, 255, 255, .9);
    overflow: hidden;
    padding: 0;

    position: absolute;
    right: 0;
    margin: auto;

    box-sizing: content-box;

    box-shadow: 0 0 5px rgb(124, 179, 255, .3);
    outline: none;

    transition: all .3s;
}

.login-btn {
    left: 280px;
}

.back-btn {
    left: -280px
}

.btn a {
    width: 34px;
    height: 34px;
    line-height: 34px;
    display: block;
}

.btn i {
    color: #424242;
    text-align: center;
    transition: all .3s;
}

.btn:hover {
    background-color: rgba(255, 255, 255, 1);
    border-color: rgb(124, 179, 255);
}

.btn:hover i {
    color: rgb(124, 179, 255);
}

.login-btn:hover~.data {
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