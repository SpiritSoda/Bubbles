<script>
import Avatar from '../../../utils/Avatar.vue';
import ConfirmButton from '../../../utils/ConfirmButton.vue';
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import LoadingButton from '../../../utils/LoadingButton.vue';
import VerticalSplit from '../../../utils/VerticalSplit.vue';

export default {
    components: { HorizontalSplit, VerticalSplit, LoadingButton, Avatar, ConfirmButton },
    data() {
        return {
            username: '',
            searched_username: '',
            id: '',
            avatar: 0,
            search_waiting: false,
            invite_waiting: false,
            searched: false,

            success: false
        }
    },
    methods: {
        search_friend() {
            if(this.search_waiting || this.success)
                return
            if(this.username.length == 0){
                this.$store.commit('error/set_error_code', 6006);
                return
            }
            this.search_waiting = true;
            setTimeout(() => {
                this.$axios.post('/api/exist', {
                    username: this.username
                }).then(
                    response => {
                        this.search_waiting = false;
                        let code = response.data.code;
                        if (code == 0) {
                            this.id = response.data.data.id;
                            this.avatar = response.data.data.avatar
                            this.searched = true
                            this.searched_username = this.username;
                        }
                        else if (code == 2)
                            this.$store.commit('error/set_error_code', 6001);
                        else if (code == 10)
                            this.$store.commit('error/set_error_code', 6005);
                        else {
                            this.$store.commit('error/set_error_code', 6000);
                        }
                    }
                ).catch(
                    e => {
                        this.search_waiting = false;
                        this.$store.commit('error/set_error_code', 6000);
                    }
                )
            }, 500)
        },
        invite_friend(){
            if(this.invite_waiting || this.success)
                return;
            this.invite_waiting = true;
            setTimeout(() => {
                this.$axios.post('/api/chatroom/invite', {
                    userId: this.id,
                    chatroomId: this.$store.state.chatroom.selected_room
                },
                {
                    headers: {
                        'token': this.token
                    }
                }).then(
                    response => {
                        this.invite_waiting = false;
                        let code = response.data.code;
                        console.log(response)
                        if (code == 0) {
                            this.success = true
                            setTimeout(() => {this.$bus.emit('close_popup')}, 800)
                        }
                        else if (code == 2)
                            this.$store.commit('error/set_error_code', 6001);
                        else if (code == 4)
                            this.$store.commit('error/set_error_code', 6002);
                        else if (code == 9)
                            this.$store.commit('error/set_error_code', 6003);
                        else if (code == 11)
                            this.$store.commit('error/set_error_code', 6004);
                        else if (code == 10)
                            this.$store.commit('error/set_error_code', 6005);
                        else {
                            this.$store.commit('error/set_error_code', 6000);
                        }
                    }
                ).catch(
                    e => {
                        this.invite_waiting = false;
                        this.$store.commit('error/set_error_code', 6000);
                    }
                )
            }, 500)
        }
    },
    computed: {
        token() {
            return this.$store.state.localuser.token
        },
        error_code() {
            return this.$store.state.error.error_code
        },
        error_msg() {
            if(this.success){
                this.msg = 'Success to invite friend !';
                return this.msg;
            }
            if (this.error_code == 6000)
                this.msg = 'Can not connect to server ...'
            else if (this.error_code == 6001)
                this.msg = 'User not exists ...'
            else if (this.error_code == 6002)
                this.msg = 'User already in chatroom ...'
            else if (this.error_code == 6003)
                this.msg = 'This chatroom is full already ...'
            else if (this.error_code == 6004)
                this.msg = 'User just got banned by admin ...'
            else if (this.error_code == 6005)
                this.msg = 'You are not in this chatroom now...'
            else if (this.error_code == 6006)
                this.msg = 'Username can not be empty'
            else if (this.error_code == 0) {
                if(!this.searched)
                    this.msg = 'Search your friends by their username ...'
                else
                    this.msg = 'If this is your friend, click button to confirm ...'
            }
            return this.msg
        }
    }
}
</script>

<template>
    <div class="invite-friend-pannel" @click.stop="() => { }" :class="{'searched': searched}">
        <HorizontalSplit :length="600" :top="5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="-5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="45" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="110" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <VerticalSplit :length="searched ? 320 : 100" :left="5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <VerticalSplit :length="searched ? 320 : 100" :left="-5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <div class="title">Invite Your Friends</div>
        <!-- error message -->
        <div class="error_msg" :class="{ 'on_error': error_code > 0, 'success': success, 'shake': error_code > 0 }">{{ error_msg }}</div>
        <!-- search username -->
        <form onsubmit="return false" class="search-wrapper">
            <input type="text" class="search" v-model="username" :placeholder="'Enter your friend\'s username ...'">
            <button class="search-btn" @click="search_friend">
                <LoadingButton :r="28" :signal="search_waiting" :click="search_friend" :fa_icon="'fa-search'"
                    :title="'search'">
                </LoadingButton>
            </button>
        </form>
        <!-- display friend info -->
        <div class="friend">
            <div class="friend-avatar">
                <Avatar :r="120" :avatar="avatar"></Avatar>
            </div>
            <div class="friend-name">
                {{this.searched_username}}
                <HorizontalSplit :length="120" :top="24" :color="'rgba(190, 190, 190, 1)'"></HorizontalSplit>
            </div>
            <div class="invite-btn">              
                <LoadingButton :r="30" :click="invite_friend" :fa_icon="'fa-check'" :signal="invite_waiting"></LoadingButton>
            </div>
        </div>
    </div>
</template>

<style scoped>
.invite-friend-pannel {
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
.invite-friend-pannel.searched {
    height: 320px;
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
    padding: 0;
    border: none;
    background-color: transparent;
    position: absolute;
    right: 16px;
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
.error_msg.success {
    color: green;
}
.friend{
    width: 100%;
    height: calc(100% - 110px);
    position: absolute;
    top: 110px;
    left: 0;
    right: 0;
    margin: auto;
}
.friend-avatar{
    width: 120px;
    height: 120px;

    border-radius: 50%;
    border: 5px double rgb(169, 176, 185);
    position: absolute;
    left: 150px;
    top: 35px;

    transition: all .3s;
}
.friend-name{
    width: 120px;
    height: 20px;
    line-height: 20px;
    font-size: 20px;
    color: rgb(92, 92, 92);
    text-align: center;

    position: absolute;
    left: 310px;
    top: 55px;
    transition: all .3s;
}
.invite-btn{
    position: absolute;
    left: 355px;
    top: 95px;
    transition: all .3s;
}
</style>