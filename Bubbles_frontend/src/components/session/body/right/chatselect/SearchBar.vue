<script>
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue';
import LoadingButton from '../../../../utils/LoadingButton.vue';

export default {
    components: { LoadingButton, HorizontalSplit },
    data() {
        return {
            invite_token: "",
            waiting: false,
            success:false
        };
    },
    methods: {
        join_chatroom() {
            if(this.waiting)
                return
            if(this.invite_token.length == 0){
                this.$store.commit('error/set_error_code', 7004)
                return
            }
            this.success = false
            this.waiting = true
            this.$axios.post(
                '/api/chatroom/join',
                {
                    token: this.invite_token
                },
                {
                    headers: {
                        'token': this.token
                    }
                }
            )
            .then(
                (response) => {
                    let code = response.data.code
                    this.waiting = false
                    if(code == 0){
                        this.success = true;
                        this.invite_token = '';
                        this.$store.dispatch('update_localuser', {
                            on_error:() => {},
                            on_success: () => {}
                        })
                        setTimeout(() => {this.success = false}, 2000)
                    }
                    else if(code == 4)
                        this.$store.commit('error/set_error_code', 7001)
                    else if(code == 9)
                        this.$store.commit('error/set_error_code', 7002)
                    else if(code == 11)
                        this.$store.commit('error/set_error_code', 7003)
                    else if(code == 6)
                        this.$store.commit('error/set_error_code', 7005)
                }
            )
            .catch(
                (e) => {
                    this.waiting = false
                    this.$store.commit('error/set_error_code', 7003)
                }
            )
        }
    },
    computed: {
        token() {
            return this.$store.state.localuser.token
        },
        error_code() {
            return this.$store.state.error.error_code
        },
        error_msg(){
            if(this.success){
                this.msg = 'Success to join chatroom!';
                return this.msg;
            }
            if (this.error_code == 7000)
                this.msg = 'Can not connect to server ...'
            else if (this.error_code == 7001)
                this.msg = 'You are already in this chatroom ...'
            else if (this.error_code == 7002)
                this.msg = 'This chatroom is full already ...'
            else if (this.error_code == 7003)
                this.msg = 'You are banned by this chatroom\'s admin ...'
            else if (this.error_code == 7004)
                this.msg = 'Invite token can not be empty ...'
            else if (this.error_code == 7005)
                this.msg = 'Invite token is not valid ...'
            else if (this.error_code == 0) {
                this.msg = 'Get invite token from the member of chatroom ...'
            }
            return this.msg
        }
    }
}
</script>

<template>
    <div>
        <form onsubmit="return false" class="search-wrapper">
            <input type="text" class="search" v-model="invite_token" :placeholder="'Search Chatroom by Invite Token ...'">
            <button class="search-btn" @click="join_chatroom">
                <LoadingButton :r="28" :signal="waiting" :click="join_chatroom" :fa_icon="'fa-search'"
                    :title="'search'">
                </LoadingButton>
            </button>
        </form>
        <div class="error_msg" :class="{ 'on_error': error_code > 0, 'success': success, 'shake': error_code > 0 }">{{ error_msg }}</div>
        <HorizontalSplit :length="710" :top="80" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
    </div>
</template>

<style scoped>      
.search-wrapper{
    height: 70px;
}
.search {
    width: 90%;
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
    top: 24px;
    left: 12px;

    background-color: rgb(246, 246, 246);
    box-shadow: 0 0px 2px rgba(0, 0, 0, .3);

    transition: box-shadow .3s, background-color .3s;
}

.search-btn {
    padding: 0;
    width: 28px;
    height: 28px;
    border-radius: 14px;
    border: none;
    text-align: center;
    line-height: 28px;
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: transparent;

    position: absolute;
    top: 24px;
    right: 20px;

    overflow: hidden;
    transition: box-shadow .3s, background-color .3s;
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

.search:focus {
    box-shadow: 0 0 7px rgb(124, 179, 255);
    background-color: #fff;
}

.search:hover:not(.message:focus) {
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: #fff;
}

.search-btn:hover {
    box-shadow: 0 0 10px rgb(124, 179, 255);
}
.error_msg {
    position: absolute;
    height: 20px;
    line-height: 20px;
    top: 56px;
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
</style>