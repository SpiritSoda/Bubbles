<script>
import VerticalSplit from '../../../../utils/VerticalSplit.vue';
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue';
import MessageBar from './message_bar/MessageBar.vue';
import ChatMenu from './chatmenu/ChatMenu.vue';
import Message from './message_list/Message.vue';
export default {
    inject:['background_color', 'shadow_color', 'font_color'],
    data(){
        return {
            msg: 'Fail to connect server...',
            not_at_bottom: true
        }
    },
    components: {
        VerticalSplit,
        HorizontalSplit,
        MessageBar,
        ChatMenu,
        Message
    },
    methods:{
        refresh_message(){
            // console.log('Time to get more message from server...')
            this.$refs.to_top_icon.className="fas fa-undo rotate"
            setTimeout(() => {
                this.$refs.to_top_icon.className="fas fa-arrow-up"
                this.$store.commit('error/set_error_code', 3001)
            }, 2000);
        },
        at_top(){
            return this.$refs.chat_body.scrollTop === 0
        },
        scroll_to_top(){
            this.$refs.chat_body.scrollTop = 0
        },
        scroll_to_bottom(){
            this.$refs.chat_body.scrollTop = this.$refs.chat_body.scrollHeight
        },
        check_at_bottom(){
            this.not_at_bottom = (this.$refs.chat_body.scrollTop + 340 < this.$refs.chat_body.scrollHeight)
        }
    },
    computed:{
        error_msg() {
            if (this.$store.state.error.error_code == 3)
                this.msg = 'Fail to connect server...'
            return this.msg
        },
        refresh_error(){
            return this.$store.state.error.error_code == 3001 ? 1 : 0
        }
    },
    mounted(){
        window.addEventListener('scroll', this.check_at_bottom, true)
        // this.scroll_to_bottom()
    },
    destroyed() {
        window.removeEventListener('scroll', this.check_at_bottom, true)
    }
}
</script>

<template>
    <div class="chatroom-body clearfix">
        <HorizontalSplit :length="687" :top="-126" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>
        <HorizontalSplit :length="687" :top="64" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>

        <div class="message-bar-wrapper">
            <MessageBar :scroll_to_bottom="scroll_to_bottom"></MessageBar>
        </div>

        
        <div class="to-top">
            <div class="error_msg" :class="{'appear-up': refresh_error}" :style="{'opacity': refresh_error}">{{error_msg}}</div>
            <a href="javascript:;" @click="scroll_to_top" :style="{'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color}">
                <i class="fas fa-arrow-up" ref="to_top_icon"></i>
            </a>
        </div>
        
        <div class="menu-wrapper clearfix">
            <ChatMenu></ChatMenu>
        </div>

        <div class="chat-wrapper">
            <div class="messages-wrapper" ref="chat_body">
                <div class="message clearfix" v-for="message in this.$store.state.chatroom.messages">
                    <Message :message="message"></Message>
                </div>
            </div>
        </div>

        <div class="to-bottom" :style="{opacity: not_at_bottom ? 1.0 : 0.0}">
            <a :href="not_at_bottom ? 'javascript:;': none" @click="scroll_to_bottom" :style="{'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color}">
                <i class="fas fa-arrow-down"></i>
            </a>
        </div>
    </div>
</template>

<style scoped>
.chatroom-body {
    height: 100%;
    width: 100%;
    box-sizing: border-box;
    background-color: v-bind(background_color);

    transition: all .3s;
}

.message-bar-wrapper {
    width: 650px;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 30px;
    margin: auto;
}

.chat-wrapper {
    width: 660px;
    height: 340px;
    position: absolute;
    left: 0px;
    right: 0;
    bottom: 132px;
    margin: auto;
    overflow: hidden;
}

.to-top {
    width: 100%;
    height: 30px;
    border-radius: 50%;
    position: absolute;
    top: 50px;
    left: 0;
    right: 0;
    margin: auto;

    z-index: 10000;
}

.to-top a {
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: block;
    box-shadow: 0 0 2px rgba(0, 0, 0, .3);
    background-color: #fff;
    border-radius: 50%;

    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    
    transition: all .2s;
}

.to-top a:hover {
    color: rgb(124, 179, 255) !important;
    box-shadow: 0 0 7px rgb(124, 179, 255) !important;
}

.error_msg{
    width: 180px;
    height: 24px;
    line-height: 24px;

    font-size: 14px;
    text-align: center;
    color: #626262;
    
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: -25px;

    transition: all .3s;
}
.menu{
    position: absolute;
    top: 18px;
    right: 30px;
}
.to-bottom {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    position: absolute;
    bottom: 140px;
    right: 30px;

    z-index: 10000;
    transition: all .2s;
}

.to-bottom a {
    width: 36px;
    height: 36px;
    line-height: 36px;
    text-align: center;
    display: block;
    border-radius: 50%;
    transition: all .2s;
}
.to-bottom a:hover {
    color: rgb(124, 179, 255) !important;
    box-shadow: 0 0 7px rgb(124, 179, 255) !important;
}

.message {
    margin-top: 6px;
}
.message:last-of-type{
    margin-bottom: 12px;
}

.messages-wrapper {
    width: 101%;
    height: 100%;
    box-sizing: border-box;
    padding-right: 5px;
    padding-bottom: 10px;
    overflow-y: scroll;
    scroll-behavior: smooth;
}
</style>