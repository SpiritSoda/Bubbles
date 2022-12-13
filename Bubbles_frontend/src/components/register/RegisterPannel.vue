<script>
import AvatarRegister from './avatar/AvatarRegister.vue';
export default {
    components: { AvatarRegister },
    data() {
        return {
            /*
                state:
                    0: filling form 
                    1: chooseing avatar
            */
            state: 0,
            waiting: false,

            avatar: -1,
            username: '',
            password: '',
            confirm_password: '',
            msg: '',
        }
    },
    methods:{
        reverse_state(){
            this.state = !this.state
        },
        submit(){
            if(this.state != 0)
                return;
            if(this.username === '')
                this.$store.commit('error/set_error_code', 2001);
            else if(this.username.length > 10)
                this.$store.commit('error/set_error_code', 2008);
            else if(this.avatar === -1)
                this.$store.commit('error/set_error_code', 2002);
            else  if(this.password === '')
                this.$store.commit('error/set_error_code', 2003); 
            else  if(this.password.length < 6)
                this.$store.commit('error/set_error_code', 2006); 
            else if(this.confirm_password === '')
                this.$store.commit('error/set_error_code', 2005);
            else if(this.password != this.confirm_password)
                this.$store.commit('error/set_error_code', 2004);
            else{
                this.waiting = true;
                this.$axios.post('/api/register', {
                    username: this.username,
                    password: this.$md5(this.password),
                    avatar: this.avatar

                }).then(
                    response => {
                        this.waiting = false;
                        let code = response.data.code;
                        if(code == 3)
                            this.$store.commit('error/set_error_code', 2007);
                        else if(code == 0){
                            this.$store.commit('tx/create_tx', 
                                {
                                    name: 'register_update',
                                    data: {
                                        username: this.username,
                                        avatar: this.avatar
                                    }
                                }
                            );
                            this.back_to_login()
                        }
                        else{
                            this.$store.commit('error/set_error_code', 1000)
                        }
                    }
                ).catch(
                    e => {
                        this.waiting = false;
                        this.$store.commit('error/set_error_code', 1000);
                    }
                )
            }
        },
        set_avatar(id){
            this.avatar = id;
        },
        back_to_login(){
            this.$bus.emit("switch_state", 0);
        }
    },
    computed:{
        error_code(){
            return this.$store.state.error.error_code
        },
        error_msg(){
            if(this.error_code == 1000)
                this.msg = 'Can not connect to server ...'
            else if(this.error_code == 2001)
                this.msg = 'Username can not be empty ...'
            else if(this.error_code == 2002)
                this.msg = "Don't forget to select an avatar ...";
            else  if(this.error_code == 2003)
                this.msg = 'Password can not be empty ...'
            else if(this.error_code == 2004)
                this.msg = "Confirm password not match ...";
            else if(this.error_code == 2005)
                this.msg = 'Confirm password can not be empty ...'
            else if(this.error_code == 2006)
                this.msg = 'Password too short ...'
            else if(this.error_code == 2007)
                this.msg = 'User exists ...'
            else if(this.error_code == 2008)
                this.msg = 'Username should be shorter than 10 chars ...'
            return this.msg;
        }
    }
}
</script>

<template>
    <div>
        <div class="avatar-wrapper">
            <AvatarRegister @reverse_state="reverse_state" @set_avatar="set_avatar" :avatar="this.avatar"></AvatarRegister>
        </div>
        <form onsubmit="return false" class="user-info" :style="{'transform': this.state == 1 ? 'translateX(100px)': ''}">
            <ul class="input">
                <li>
                    <span>Username: </span>
                    <input type="text" class="data" v-model="username" :class="{'shake': error_code == 2001 || error_code == 2007 || error_code == 2008}" autocomplete="off">
                </li>
                <li>
                    <span>Password: </span>
                    <input type="password" class="data" v-model="password" :class="{'shake': error_code == 2003 || error_code == 2006}">
                </li>
                <li>
                    <span>Confirm Password: </span>
                    <input type="password" class="data" v-model="confirm_password" :class="{'shake': error_code == 2004 || error_code == 2005}">
                </li>
            </ul>
            <button class="register-btn btn" @click="submit" :class="{'waiting': waiting}">
                <a href="javascript:;">
                    <i class="fas" :class="{'fa-arrow-right': !waiting, 'fa-undo rotate': waiting}"></i>
                </a>
            </button>
            <button class="back-btn btn" @click="back_to_login">
                <a href="javascript:;">
                    <i class="fas fa-times"></i>
                </a>
            </button>

        </form>
        <span class="error_msg" :style="{'opacity': error_code > 0 ? 1 : 0}">{{error_msg}}</span>
    </div>
</template>

<style scoped>
.avatar-wrapper {
    width: 400px;
    height: 400px;
    position: absolute;
    top: 90px;
    left: 150px;
    bottom: 0;
}

.user-info{
    position: absolute;
    right: 310px;
    top: 110px;
    transition: all .2s;
}

.user-info input {
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

    transition: all .3s;
}

.user-info input:focus {
    border-color: rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1);
}
.user-info input:hover{
    border-color: rgb(124, 179, 255);
}

.input li span{
    display: block;
    color: #fff;
    margin: 8px 0;
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

    box-sizing: content-box;
    position: absolute;
    right: 0px;

    box-shadow: 0 0 5px rgb(124, 179, 255, .3);
    outline: none;

    transition: all .3s;
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
.register-btn{
    bottom: -60px;
}
.back-btn{
    top: -30px;
}
.error_msg {
    text-align: right;
    padding-right: 20px;
    font-size: 14px;
    width: 300px;
    height: 20px;
    line-height: 20px;
    color: rgba(255, 255, 255, .8);

    position: absolute;
    left: 0;
    right: 0;
    bottom: 130px;
    margin: auto;

    opacity: 0;
    transition: all .3s;
}
</style>