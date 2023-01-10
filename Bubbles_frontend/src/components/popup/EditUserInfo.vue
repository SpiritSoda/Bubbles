<script>
import Button from '../utils/Button.vue';
import Avatar from '../utils/Avatar.vue';
import VerticalSplit from '../utils/VerticalSplit.vue';
import HorizontalSplit from '../utils/HorizontalSplit.vue';
import TextButton from '../utils/TextButton.vue';
import LoadingTextButton from '../utils/LoadingTextButton.vue';
export default {
    components: {
        Button,
        Avatar,
        VerticalSplit,
        HorizontalSplit,
        TextButton,
        LoadingTextButton
    },
    data() {
        return {
            avatar: 0,
            username: '',
            password: '',
            confirm_password: '',

            lock_username: true,
            lock_password: true,

            show_list: false,
            msg: '',
            waiting: false
        }
    },
    methods: {
        unlock_username() {
            this.lock_username = false
            // setTimeout(() => {document.getElementById("username").className = ''}, 800)
        },
        unlock_password() {
            this.lock_password = false
            this.password = ''
            this.confirm_password = ''
            // setTimeout(() => {document.getElementById("password").className = ''}, 800)
            // setTimeout(() => {document.getElementById("confirm_password").className = ''}, 800)
        },
        reverse_list() {
            this.show_list = !this.show_list
        },
        set_avatar(id) {
            this.avatar = id
            this.reverse_list()
        },
        submit() {
            if(this.waiting)
                return;
            if (!this.lock_username) {
                if (this.username.length == 0) {
                    this.$store.commit('error/set_error_code', 4004)
                    return;
                }
            }
            if (!this.lock_password) {
                if (this.password.length == 0) {
                    this.$store.commit('error/set_error_code', 4003)
                    return;
                }
                else if (this.password.length < 6) {
                    this.$store.commit('error/set_error_code', 4001)
                    return;
                }
                else if (this.password != this.confirm_password) {
                    this.$store.commit('error/set_error_code', 4002)
                    return;
                }
            }
            this.waiting = true
            setTimeout(() => {
                this.$store.dispatch('edit_userinfo', {
                    data: {
                        avatar: this.avatar,
                        username: this.lock_username ? '' : this.username,
                        password: this.lock_password ? '' : this.$md5(this.password),
                    },
                    on_success: () => { this.$bus.emit('close_popup'); this.waiting = false },
                    on_error: (code) => { 
                        if(code == 8)
                            this.$store.commit('error/set_error_code', 4005)
                        else
                            this.$store.commit('error/set_error_code', 4000)
                        this.waiting = false 
                    }
                })
            }, 300)
        },
        cancel() {
            this.$bus.emit('close_popup')
        }
    },
    computed: {
        error_code() {
            return this.$store.state.error.error_code
        },
        avatar_count() {
            return this.$store.state.global.avatar_count
        },
        error_msg() {
            if (this.error_code == 4000)
                this.msg = 'Can not connect to server ...'
            else if (this.error_code == 4001)
                this.msg = 'Password too short ...'
            else if (this.error_code == 4002)
                this.msg = 'Confirm password doesn\'t match ...'
            else if (this.error_code == 4003)
                this.msg = 'Password can not be empty ...'
            else if (this.error_code == 4004)
                this.msg = 'Username can not be empty ...'
            else if (this.error_code == 4005)
                this.msg = 'Username exists ...'
            return this.msg
        }
    },
    mounted() {
        if (!this.$store.state.userinfo.userinfo[this.$store.state.localuser.local_id])
            return;
        let user = this.$store.state.userinfo.userinfo[this.$store.state.localuser.local_id];
        this.username = user.username
        this.avatar = user.avatar
        this.password = 'xxxxxx'
        this.confirm_password = 'xxxxxx'
    }
}
</script>

<template>
    <div class="modify-pannel" @click.stop="() => { }">
        <HorizontalSplit :length="600" :top="5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="-5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="45" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <VerticalSplit :length="400" :left="5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <VerticalSplit :length="400" :left="-5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <div class="title">Edit Your Profile</div>
        <!-- select avatar -->
        <div class="avatar-select">
            <a href="javascript:;" 
                class="avatar-show" 
                @click="reverse_list" 
                :class="{ show_list: show_list }">
                <Avatar :avatar="avatar" 
                    :r="130" 
                    :shadow="false">
                </Avatar>
            </a>
            <div class="avatar-list" :class="{ show_list: show_list }">
                <div class="avatar-list-roller">
                    <div class="avatar-icon" 
                        v-for="id in avatar_count"
                        :style="{ 'background-image': 'url(\'avatars/genshin/' + id + '.jpg\')' }"
                        @click="set_avatar(id)">
                    </div>
                </div>
            </div>
        </div>
        <!-- change user info -->
        <form onsubmit="return false" class="user-info">
            <ul class="input">
                <li>
                    <span>Username: </span>
                    <input type="text" id="username" 
                        v-model="username" 
                        :disabled="lock_username"
                        :class="{ unlock: !lock_username, shake: error_code == 4004 || error_code == 4005 }" autocomplete="off">
                    <div class="unlock-btn">
                        <Button :r="30" 
                            :click.once="unlock_username" 
                            :style="{ opacity: lock_username ? 1.0 : 0.0 }"
                            :r_shadow="2" 
                            :fa_icon="'fa-edit'" 
                            :title="'Edit Username'">
                        </Button>
                    </div>
                </li>
                <li>
                    <span>Password: </span>
                    <input type="password" 
                        id="password" 
                        v-model="password" 
                        :disabled="lock_password"
                        :class="{ unlock: !lock_password, shake: error_code == 4001 || error_code == 4003 }">
                    <div class="unlock-btn">
                        <Button :r="30" 
                            :click.once="unlock_password" 
                            :style="{ opacity: lock_password ? 1.0 : 0.0 }"
                            :r_shadow="2" 
                            :fa_icon="'fa-edit'" 
                            :title="'Edit Password'">
                        </Button>
                    </div>
                </li>
                <li>
                    <span>Confirm Password: </span>
                    <input type="password" id="confirm_password" v-model="confirm_password" :disabled="lock_password"
                        :class="{ unlock: !lock_password, shake: error_code == 4002 }">
                </li>
            </ul>
        </form>
        <div class="cancel-btn-wrapper">
            <TextButton 
                :width="90" 
                :height="30" 
                :title="'Cancel'" 
                :click="cancel" 
                :font_color="'rgb(239, 60, 60)'">
            </TextButton>
        </div>
        <div class="submit-btn-wrapper">
            <LoadingTextButton 
                :width="90" 
                :height="30" 
                :title="'Submit'" 
                :click="submit" 
                :signal="waiting">
            </LoadingTextButton>
        </div>
        <span class="error_msg" :style="{ 'opacity': error_code > 0 ? 1 : 0 }">{{ error_msg }}</span>
        <!-- <div class="loading" :style="{ 'opacity': waiting > 0 ? 1 : 0 }">
            <i class="fas fa-undo rotate"></i>
        </div> -->
    </div>
</template>

<style scoped>
.modify-pannel {
    width: 600px;
    height: 425px;
    border-radius: 20px;

    background-color: #fff;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: 50%;
    transform: translateY(-50%);
}

.user-info {
    position: absolute;
    left: 258px;
    top: 85px
}

.user-info input {
    width: 200px;
    height: 32px;
    line-height: 32px;
    border: 2px solid rgba(101, 101, 101, 0.3);
    border-radius: 16px;
    overflow: hidden;
    outline: none;
    padding: 0px 10px;

    font-size: 14px;
    line-height: 1.6;
    font-family: inherit;
    text-align: center;

    transition: all .3s;
}

.unlock {
    border: 2px solid rgb(0, 180, 0) !important;
}

.user-info input:focus {
    border-color: rgb(124, 179, 255) !important;
    background-color: rgba(255, 255, 255, 1);
}

.user-info input:hover {
    border-color: rgb(124, 179, 255) !important;
}

.input li span {
    display: block;
    color: rgb(59, 59, 59);
    margin: 8px 0;
}

.unlock-btn {
    display: inline-block;
    margin-left: 10px;
}

.avatar-select {
    width: 140px;
    height: 140px;
    position: absolute;
    left: 60px;
    top: 135px
}

.avatar-show {
    display: block;
    width: 130px;
    height: 130px;
    background-color: rgba(255, 255, 255, .9);
    margin-bottom: 20px;

    border: 5px double rgb(169, 176, 185);
    border-radius: 50%;
    position: absolute;

    overflow: hidden;
    transition: all .3s;
    z-index: 500;
}
.avatar-show:hover {
    border-color: rgb(124, 179, 255);
}

.avatar-show.show_list {
    border-color: rgb(124, 179, 255) !important;
    transform: translateY(-70px);
}

.avatar-list {
    width: 120px;
    height: 120px;
    border: 2px rgb(124, 179, 255) solid;
    border-radius: 60px;
    background-color: rgb(253, 253, 253);
    position: absolute;
    right: 0;
    left: 0;
    top: 10px;
    margin: auto;
    z-index: 0;
    overflow: hidden;

    transition: all .3s;
}

.avatar-list.show_list {
    height: 270px;
    top:-10px
}

.avatar-list-roller {
    padding-top: 6px;
    margin-top: 82px;
    width: 104%;
    border-radius: 55px;
    height: calc(100% - 90px);
    overflow: auto;
}

.avatar-icon {
    float: left;
    margin-left: 10px;
    margin-bottom: 8px;
    background-clip: padding-box;
    background-size: 96px;
    border: 2px solid rgb(200, 200, 200);
    border-radius: 50%;

    width: 96px;
    height: 96px;

    transition: all .3s;
}

.avatar-icon:hover {
    border-color: rgb(71, 147, 253);
    box-shadow: 0 0 4px rgba(71, 147, 253, .8);
    transform: perspective(800px) translateZ(30px);
}

.title {
    position: absolute;
    height: 40px;
    line-height: 40px;
    top: 7px;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;

    color: rgba(50, 50, 50, .6);
    font-size: 18px;
}

.cancel-btn-wrapper {
    position: absolute;
    left: 258px;
    top: 320px
}

.submit-btn-wrapper {
    position: absolute;
    left: 390px;
    top: 320px
}

.error_msg {
    font-size: 16px;
    height: 30px;
    width: 400px;
    line-height: 30px;
    text-align: center;
    color: rgba(36, 36, 36, 0.8);

    position: absolute;
    left: 174px;
    top: 365px;

    opacity: 0;
    transition: all .2s;
}
</style>