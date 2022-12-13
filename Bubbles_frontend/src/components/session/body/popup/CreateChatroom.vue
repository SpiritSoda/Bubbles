<script>
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import Icon from '../../../utils/Icon.vue';
import LoadingTextButton from '../../../utils/LoadingTextButton.vue';
import TextButton from '../../../utils/TextButton.vue';
import VerticalSplit from '../../../utils/VerticalSplit.vue';

export default {
    components: { HorizontalSplit, VerticalSplit, TextButton, Icon, LoadingTextButton },
    data() {
        return {
            passport: '',
            verified: false,
            waiting: false,
            show_list: false,
            msg: '',

            max_user_limit: 30,
            max_user: 30,
            title: '',
            comment: '',
            icon: 0
        }
    },
    computed: {
        waiting_for_verify() {
            return !this.verified && this.waiting
        },
        waiting_for_create() {
            return this.verified && this.waiting
        },
        token() {
            return this.$store.state.localuser.token
        },
        error_code() {
            return this.$store.state.error.error_code
        },
        error_msg() {
            if (this.error_code == 5000)
                this.msg = 'Can not connect to server ...'
            else if(this.error_code == 5001){
                this.msg = 'Passport can not be empty ...'
            }
            else if(this.error_code == 5002){
                this.msg = 'Title can not be empty ...'
            }
            else if(this.error_code == 5003){
                this.msg = 'Max user is not valid (less than 2 or greater than ' + this.max_user_limit + ') ...'
            }
            else if(this.error_code == 5004){
                this.msg = 'Don\'t forget to choose an icon ...'
            }
            else if(this.error_code == 5005){
                this.msg = 'Comment can not be empty ...'
            }
            else if(this.error_code == 5006){
                this.msg = 'Passport not valid ...'
            }
            else if(this.error_code == 5007){
                this.msg = 'Passport expired ...'
            }
            else if(this.error_code == 0){
                if (!this.verified)
                    this.msg = 'Talk to admin to get a passport ...'
                else
                    this.msg = 'Now let\'s create your chatroom !'
            }
            return this.msg
        },
        icon_size() {
            return this.show_list ? 105 : 140
        },
        icon_count() {
            return this.$store.state.global.icon_count
        }
    },
    methods: {
        verify_passport() {
            if (this.verified || this.waiting_for_verify)
                return
            if(this.passport.length == ''){
                this.$store.commit('error/set_error_code', 5001)
                return
            }
            this.waiting = true;
            setTimeout(() => {
                // this.verified = true
                // this.waiting = false
                // return
                this.$axios.post('/api/chatroom/verifyPassport',
                    {
                        'token': this.passport
                    },
                    {
                        headers: {
                            'token': this.token
                        }
                    }
                ).then(
                    response => {
                        this.waiting = false;
                        let code = response.data.code;
                        if (code == 0) {
                            this.verified = true
                            this.max_user_limit = response.data.data.max_user
                            this.max_user = this.max_user_limit
                        }
                        else if(code == 7){
                            this.$store.commit('error/set_error_code', 5007);
                        }
                        else {
                            this.$store.commit('error/set_error_code', 5006);
                        }
                    }
                ).catch(
                    e => {
                        this.waiting = false;
                        this.$store.commit('error/set_error_code', 5000);
                    }
                )
            }, 300)
        },
        submit() {
            if (this.waiting)
                return;
            if(this.title.length == 0){
                this.$store.commit('error/set_error_code', 5002)
                return
            }
            else if(this.max_user < 2 || this.max_user > this.max_user_limit){
                this.$store.commit('error/set_error_code', 5003)
                return
            }
            else if(this.icon == 0){
                this.$store.commit('error/set_error_code', 5004)
                return
            }
            if(this.comment.length == 0){
                this.$store.commit('error/set_error_code', 5005)
                return
            }
            this.waiting = true
            setTimeout(() => {
                this.$axios.post(
                    '/api/chatroom/createChatroom',
                    {
                        title: this.title,
                        icon: this.icon,
                        comment: this.comment,
                        max_user: this.max_user,
                        passport: this.passport
                    },
                    {
                        headers:{
                            'token': this.token
                        }
                    }
                ).then((response) => {
                    this.waiting = false;
                    let code = response.data.code;
                    console.log(response)
                    if(code == 0){
                        this.$store.dispatch('update_localuser', {
                            on_error: () => {},
                            on_success: () => {}
                        });
                        this.$bus.emit('close_popup')
                    } 
                    else if(code == 6){
                        this.$store.commit('error/set_error_code', 5006);
                    }
                })
                .catch((e) => {
                    this.waiting = false;
                    this.$store.commit('error/set_error_code', 5000)
                })
            }, 300)
        },
        cancel() {
            this.$bus.emit('close_popup')
        },
        reverse_list() {
            this.show_list = !this.show_list
        },
        set_icon(id) {
            this.icon = id
            this.reverse_list()
        },
        scroll_icon_list(event) {
            var element = document.getElementById("icon-list-roller")
            var detail = event.wheelDelta || event.detail;
            var moveForwardStep = -1;
            var moveBackStep = 1;
            var step = 0;
            if (detail > 0) {
                step = moveForwardStep * 50;
            } else {
                step = moveBackStep * 50;
            }
            element.scrollLeft += step;
        }
    },
    mounted() {
        document.getElementById("icon-list-roller").addEventListener('DOMMouseScroll', this.scroll_icon_list, false)
        document.getElementById("icon-list-roller").addEventListener('mousewheel', this.scroll_icon_list, false)
    },
    destroyed() {
        document.getElementById("icon-list-roller").removeEventListener('DOMMouseScroll', this.scroll_icon_list, false)
        document.getElementById("icon-list-roller").removeEventListener('mousewheel', this.scroll_icon_list, false)
    }
}
</script>

<template>
    <div class="create-chatroom-pannel" @click.stop="() => { }" :class="{verified: verified}">
        <HorizontalSplit :length="600" :top="5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="-5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="45" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="110" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <VerticalSplit :length="verified ? 400 : 100" :left="5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <VerticalSplit :length="verified ? 400 : 100" :left="-5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <div class="title">Create Your Chatroom</div>
        <!-- enter passport -->
        <form onsubmit="return false" class="search-wrapper">
            <input type="text" class="search" v-model="passport" :placeholder="'Enter your passport ...'"
                :disabled="verified" autocomplete="off">
            <button class="search-btn" @click="verify_passport">
                <a :href="verified ? null : 'javascript:;'" class="search-a">
                    <i class="fas " :class="waiting_for_verify ? 'fa-undo rotate' : 'fa-search'"></i>
                </a>
            </button>
        </form>
        <!-- error message -->
        <div class="error_msg" :class="{ 'on_error': error_code > 0, 'shake': error_code > 0}">{{ error_msg }}</div>
        <!-- select chatroom icon -->
        <div class="icon-select">
            <a href="javascript:;" class="icon-show" @click="reverse_list"
                :style="{ width: icon_size + 'px', height: icon_size + 'px' }" :class="{ 'show-list': show_list, 'shake': error_code == 5004}">
                <Icon :icon="icon" :r="icon_size" :shadow="false"></Icon>
            </a>
            <div class="icon-list" :class="{ 'show-list': show_list }">
                <div class="icon-list-roller" id="icon-list-roller">
                    <div class="icon-item" v-for="id in icon_count"
                        :style="{ 'background-image': 'url(\'/chatroom/' + id + '.jpg\')' }" @click="set_icon(id)">
                    </div>
                </div>
            </div>
        </div>
        <!-- enter chatroom infomation -->
        <form onsubmit="return false" class="chatroom-info">
            <ul class="input">
                <li>
                    <span>Title: </span>
                    <input type="text" id="title" v-model="title" :class="{ shake: error_code == 5002 }" autocomplete="off">
                </li>
                <li>
                    <span>Comment: </span>
                    <textarea id="comment" v-model="comment"
                        :class="{ shake: error_code == 5005 }" maxlength="40">
                    </textarea>
                </li>
                <li>
                    <span>Max Users: (&le; {{ max_user_limit }})</span>
                    <input type="number" id="max_user" v-model="max_user" :max="max_user_limit" :class="{ shake: error_code == 5003 }" autocomplete="off">
                </li>
            </ul>
        </form>
        <div class="cancel-btn-wrapper">
            <TextButton :width="90" :height="30" :title="'Cancel'" :click="cancel" :font_color="'rgb(239, 60, 60)'">
            </TextButton>
        </div>
        <div class="submit-btn-wrapper">
            <LoadingTextButton :width="90" :height="30" :title="'Submit'" :click="submit" :signal="verified && waiting"></LoadingTextButton>
        </div>
    </div>
</template>

<style scoped>
.create-chatroom-pannel {
    width: 600px;
    height: 125px;
    border-radius: 20px;

    background-color: #fff;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: 50%;
    transform: translateY(-50%);
    overflow: hidden;

    transition: all .3s;
}

.create-chatroom-pannel.verified {
    height: 425px;
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

.search-wrapper {
    width: 100%;
    height: 70px;
    position: absolute;
    top: 55px;
}

.search {
    width: 89%;
    height: 28px;
    line-height: 28px;

    border-radius: 14px;

    padding-left: 18px;
    padding-right: 50px;
    box-sizing: border-box;

    font-size: 12px;
    outline: none;
    border: none;

    position: absolute;
    left: 12px;

    background-color: rgb(246, 246, 246);
    box-shadow: 0 0px 2px rgba(0, 0, 0, .3);

    transition: all .3s;
}

.search-btn {
    width: 28px;
    height: 28px;
    border-radius: 14px;
    border: none;
    text-align: center;
    line-height: 28px;
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: rgb(249, 249, 249);

    position: absolute;
    right: 16px;

    overflow: hidden;
    transition: all .3s;
}

.search-a {
    width: 28px;
    height: 28px;
    display: block;
    position: absolute;
    left: 0;
    top: 0;
    color: #424242;

    font-size: 12px;
}

.search-a i {
    transition: all .3s;
}

.search:focus {
    box-shadow: 0 0 7px rgb(124, 179, 255);
    background-color: #fff;
}

.search:hover:not(.message:focus) {
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
}

.search-btn:hover {
    box-shadow: 0 0 6px rgb(124, 179, 255);
    background-color: #fff;
}

.error_msg {
    position: absolute;
    height: 20px;
    line-height: 20px;
    top: 88px;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;

    color: rgba(50, 50, 50, .6);
    font-size: 12px;

    transition: all .3s;
}

.error_msg.on_error {
    color: rgb(239, 60, 60);
}

.chatroom-info {
    position: absolute;
    left: 305px;
    top: 117px
}

.chatroom-info input {
    width: 200px;
    height: 24px;
    line-height: 24px;
    border: 2px solid rgba(101, 101, 101, 0.3);
    border-radius: 16px;
    overflow: hidden;
    outline: none;
    padding: 0px 10px;

    font-size: 14px;
    line-height: 1.6;
    font-family: inherit;
    text-align: center;
    color: rgba(50, 50, 50, .9);

    transition: all .3s;
    margin-bottom: 10px;
}


#comment {
    width: 200px;
    height: 80px;
    line-height: 24px;
    border: 2px solid rgba(101, 101, 101, 0.3);
    border-radius: 16px;
    overflow: hidden;
    outline: none;
    padding: 5px 10px;

    font-size: 14px;
    line-height: 1.6;
    font-family: inherit;
    color: rgba(50, 50, 50, .9);
    resize: none;

    transition: all .3s;
    margin-bottom: 10px;
}

.chatroom-info input:focus {
    border-color: rgb(124, 179, 255) !important;
    background-color: rgba(255, 255, 255, 1);
}

.chatroom-info input:hover {
    border-color: rgb(124, 179, 255) !important;
}

.chatroom-info textarea:focus {
    border-color: rgb(124, 179, 255) !important;
    background-color: rgba(255, 255, 255, 1);
}

.chatroom-info textarea:hover {
    border-color: rgb(124, 179, 255) !important;
}

.input li span {
    display: block;
    color: rgba(50, 50, 50, .8);
    margin: 8px 0;
}

.cancel-btn-wrapper {
    position: absolute;
    left: 45px;
    top: 355px
}

.submit-btn-wrapper {
    position: absolute;
    left: 165px;
    top: 355px
}

.icon-select {
    width: 240px;
    height: 140px;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    transform: translateX(-150px);
    top: 165px;
    transition: all .3s;
}

.icon-show {
    width: 120px;
    height: 120px;
    background-color: rgba(255, 255, 255, .9);
    margin-bottom: 20px;

    border: 5px double rgb(169, 176, 185);
    border-radius: 50%;
    position: absolute;
    left: 0;
    right: 0;
    top: -10px;
    margin: auto;

    overflow: hidden;
    transition: all .3s;
    z-index: 500;
}

.icon-show.show-list {
    top: -35px;
}

.icon-show:hover {
    border-color: rgb(124, 179, 255);
}

.icon-show.show-list {
    border-color: rgb(124, 179, 255) !important;
}

.icon-list {
    height: 90px;
    width: 0px;
    border-radius: 50px;

    box-shadow: 0 0 5px rgba(71, 147, 253, .8);

    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: 1px;
    transition: all .3s;
    overflow: hidden;
}

.icon-list.show-list {
    width: 240px;
    top: 75px
}

.icon-list-roller {
    height: 101%;
    padding-top: 10px;
    padding-left: 8px;
    padding-right: 3px;
    border-radius: 45px;
    overflow-x: scroll;
    white-space: nowrap;

    scroll-behavior: smooth;
    transition: all .3s;
}

.icon-item {
    display: inline-block;
    margin-right: 8px;
    background-clip: padding-box;
    background-size: 70px;
    box-shadow: 2px 2px 5px rgba(0, 0, 0, .6);
    border-radius: 50%;

    width: 70px;
    height: 70px;

    transition: all .3s;
}

.icon-item:hover {
    border-color: rgb(71, 147, 253);
    box-shadow: 0 0 6px rgba(71, 147, 253, 1);
    transform: perspective(800px) translateZ(40px);
}
</style>