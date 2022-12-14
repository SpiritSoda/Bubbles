<script>
import VerticalSplit from '../../../../utils/VerticalSplit.vue';
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue';
import MessageBar from './message_bar/MessageBar.vue';
import ChatMenu from './chatmenu/ChatMenu.vue';
import Message from './message_list/Message.vue';
export default {
    inject: ['background_color', 'shadow_color', 'font_color'],
    data() {
        return {
            msg: 'Fail to connect server...',
            not_at_bottom: true,
            refreshing_message: false,
            refresh_online_timer: null,
            has_new_message: false
        }
    },
    components: {
        VerticalSplit,
        HorizontalSplit,
        MessageBar,
        ChatMenu,
        Message
    },
    methods: {
        refresh_message() {
            if(this.refreshing_message)
                return
            // console.log('Time to get more message from server...')
            this.refreshing_message = true
            setTimeout(() => {
                let message = this.$store.state.chatroom.messages[0]
                let messageId = message ? message.messageId : 0
                this.$store.dispatch("get_message", {
                    startId: messageId,
                    on_success: (cnt) => {
                        if(cnt > 0)
                            this.$nextTick(() => {
                                this.$refs.chat_body.scrollTo({
                                    top: 69 * cnt,
                                    behavior: "auto"
                                })
                                this.$refs.chat_body.scrollTo({
                                    top: 69 * cnt - 20,
                                    behavior: "smooth"
                                })
                            })
                        else
                            this.$store.commit('error/set_error_code', 3002)   
                        this.refreshing_message = false
                    },
                    on_error: () => {
                        this.refreshing_message = false
                        this.$store.commit('error/set_error_code', 3001)
                    }
                })
            }, 500);
        },
        at_top() {
            return this.$refs.chat_body.scrollTop === 0
        },
        scroll_to_top() {
            if (this.at_top()) {
                this.refresh_message()
            }
            else
                this.$refs.chat_body.scrollTo({
                    top: 0,
                    behavior: "smooth"
                })
        },
        scroll_to_bottom() {
            this.$refs.chat_body.scrollTo({
                top: this.$refs.chat_body.scrollHeight,
                behavior: "smooth"
            })
        },
        scroll_to_bottom_immediate() {
            this.$refs.chat_body.scrollTo({
                top: this.$refs.chat_body.scrollHeight,
                behavior: "auto"
            })
        },
        check_at_bottom() {
            let element = document.getElementById('messages-wrapper')
            this.not_at_bottom = (element.scrollTop + 341 < element.scrollHeight)
            if(!this.not_at_bottom){
                this.has_new_message = false
            }
        }
    },
    computed: {
        error_code(){
            return this.$store.state.error.error_code
        },
        error_msg() {
            if (this.error_code == 3001)
                this.msg = 'Fail to connect server...'
            else if (this.error_code == 3002)
                this.msg = 'No more messages ...'
            else if (this.error_code == 3003)
                this.msg = 'Message too long ...'
            return this.msg
        },
        refresh_error() {
            return this.error_code > 3000 && this.error_code < 4000
        },
        chatroom_title(){
            return this.$store.state.localuser.chatrooms[this.$store.state.chatroom.selected_room].title
        }
    },
    mounted() {
        document.getElementById('messages-wrapper').addEventListener('scroll', this.check_at_bottom, true)
        this.$store.dispatch("get_message", {
            on_success: () => { this.$nextTick(() => { this.scroll_to_bottom_immediate(); this.check_at_bottom() }) }
        })
        this.refresh_online_timer = setInterval(
            () => {
                this.$store.dispatch('update_onlines')
            },
            2000
        )
        this.$bus.on('scroll_to_bottom', () => {
            this.$nextTick(() => { this.scroll_to_bottom() })
        })
        this.$bus.on('new_message', (msg) => {
            if(!this.not_at_bottom)
                this.$nextTick(() => { this.scroll_to_bottom() })
            this.has_new_message = true;
        })
        // this.scroll_to_bottom()
    },
    destroyed() {
        document.getElementById('messages-wrapper').removeEventListener('scroll', this.check_at_bottom, true)
        if (this.refresh_online_timer)
            clearInterval(this.refresh_online_timer)
    }
}
</script>

<template>
    <div class="chatroom-body clearfix">
        <HorizontalSplit :length="687" :top="-126" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>
        <HorizontalSplit :length="687" :top="64" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>
        <div class="new-message-tip" :style="{opacity: has_new_message ? 1.0 : 0.0}"></div>
        <div class="header fade-in clearfix">
            <div class="title">
                <i class="icon fas fa-chevron-left"></i>
                <!-- <i class="icon fas fa-caret-left"></i> -->
                <span>{{this.chatroom_title}}</span>
                <!-- <i class="icon fas fa-caret-right"></i> -->
                <i class="icon fas fa-chevron-right"></i>
            </div>
            <ChatMenu></ChatMenu>
        </div>

        <div class="message-bar-wrapper fade-in">
            <MessageBar></MessageBar>
        </div>


        <div class="to-top fade-in">
            <div class="error_msg" :class="{ 'appear-down': refresh_error }" :style="{ 'opacity': refresh_error ? 1.0 : 0.0 }">
                {{ error_msg }}</div>
            <a href="javascript:;" @click="scroll_to_top"
                :style="{ 'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color }">
                <i :class="'fas ' + (this.refreshing_message ? 'fa-undo rotate' : 'fa-arrow-up')"></i>
            </a>
        </div>

        <div class="chat-wrapper fade-in">
            <div class="messages-wrapper" ref="chat_body" id="messages-wrapper">
                <div class="message clearfix" v-for="message in this.$store.state.chatroom.messages">
                    <Message :message="message"></Message>
                </div>
            </div>
        </div>

        <div class="to-bottom" :style="{ opacity: not_at_bottom ? 1.0 : 0.0 }">
            <a :href="not_at_bottom ? 'javascript:;' : null" @click="scroll_to_bottom"
                :style="{ 'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color }">
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
    top: 57px;
    left: 0;
    right: 0;
    margin: auto;
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
    z-index: 99999;
}

.to-top a:hover {
    color: rgb(124, 179, 255) !important;
    box-shadow: 0 0 7px rgb(124, 179, 255) !important;
}

.error_msg {
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
    top: 35px;

    transition: all .3s;
}

.to-bottom {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    position: absolute;
    bottom: 140px;
    right: 30px;

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

.message:last-of-type {
    margin-bottom: 12px;
}

.messages-wrapper {
    width: 101%;
    height: 100%;
    box-sizing: border-box;
    padding-right: 5px;
    padding-bottom: 10px;
    overflow-y: scroll;
    scroll-behavior: unset;
}
.new-message-tip{
    height: 5px;
    width: 637px;

    position: absolute;
    bottom: 127px;
    left: 0;
    right: 0;
    margin: auto;
    /* background: linear-gradient(to left,rgba(255, 255, 255, 0.0) , rgba(124, 179, 255, .6) 20%, rgba(124, 179, 255, .6) 80%, rgba(255, 255, 255, 0.0)); */
    /* background: linear-gradient(to bottom,rgba(255, 255, 255, 0.0) , rgba(124, 179, 255, .3) 80%); */
    background: radial-gradient(ellipse at bottom, rgba(124, 179, 255, .6), transparent);
    /* background-color: rgb(124, 179, 255); */

    /* box-shadow: 0 0 6px rgb(124, 179, 255); */
    transition: all .3s;
}
.header{
    height: 64px;
    position: relative;
}
.title{
    line-height: 20px;
    font-size: 20px;
    text-align: center;
    color: #626262;
    position: absolute;
    top: 24px;
    left: 0;
    right: 0;
    margin: auto;
    transition: all .3s;
}
.title i{
    font-size: 18px;
    color: #8b8b8b;
    display: inline-block;
}
.title span{
    display: inline-block;
    margin: 0 15px;
}
</style>