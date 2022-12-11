<script>
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import VerticalSplit from '../../../utils/VerticalSplit.vue';

export default {
    components: { HorizontalSplit, VerticalSplit },
    data() {
        return {
            passport: '',
            verified: false,
            waiting: false,
            msg: ''
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
            else{
                if(!this.verified)
                    this.msg = 'Talk to admin to get a passport ...'
                else
                    this.msg = 'Now let\'s create your chatroom !' 
            }
            return this.msg
        }
    },
    methods: {
        verify_passport() {
            if (this.verified || this.waiting_for_verify)
                return
            this.waiting = true;
            setTimeout(() => {
                // this.verified = true
                // this.waiting = false
                // return
                this.$axios.post('/api/verify_passport',
                    {
                        params: {
                            passport: this.passport
                        },
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
                        }
                        else if (code == 3) {
                            this.$store.commit('error/set_error_code', 5000);
                        }
                    }
                ).catch(
                    e => {
                        this.waiting = false;
                        this.$store.commit('error/set_error_code', 5000);
                    }
                )
            }, 300)
        }
    }
}
</script>

<template>
    <div class="create-chatroom-pannel" @click.stop="() => { }" :class="{verified: verified}">
        <HorizontalSplit :length="600" :top="5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="-5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="45" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="110" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <VerticalSplit :length="400" :left="5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <VerticalSplit :length="400" :left="-5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <div class="title">Create Your Chatroom</div>
        <form onsubmit="return false" class="search-wrapper">
            <input type="text" class="search" v-model="passport" :placeholder="'Enter your passport ...'"
                :disabled="verified">
            <button class="search-btn" @click="verify_passport">
                <a :href="verified ? none : 'javascript:;'" class="search-a">
                    <i class="fas " :class="waiting_for_verify ? 'fa-undo rotate' : 'fa-search'"></i>
                </a>
            </button>
        </form>
        <div class="error_msg" :class="{'on_error': error_code > 0}">{{error_msg}}</div>
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
.search-a i{
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
.error_msg.on_error{
    color: rgb(239, 60, 60);
}
</style>