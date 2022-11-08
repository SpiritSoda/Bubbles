<script>
import VerticalSplit from '../../../../utils/VerticalSplit.vue';
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue';
import MessageBar from './message_bar/MessageBar.vue';
import MessageList from './message_list/MessageList.vue'
export default {
    inject:['error_code','background_color', 'shadow_color', 'font_color'],
    data(){
        return {
            msg: 'Fail to connect server...'
        }
    },
    components: {
        VerticalSplit,
        HorizontalSplit,
        MessageBar,
        MessageList
    },
    methods:{
        refresh_message(){
            // console.log('Time to get more message from server...')
            this.$refs.to_top_icon.className="fas fa-undo rotate"
            setTimeout(() => {
                this.$refs.to_top_icon.className="fas fa-arrow-up"
                this.$bus.emit('error', 3001)
            }, 2000);
        },
        scroll_to_top(){
            this.$refs.chat_body.scroll_to_top()
            if(this.$refs.chat_body.at_top()){
                this.refresh_message()
            }
        },
        scroll_to_bottom(){
            this.$refs.chat_body.scroll_to_bottom()
        }
    },
    computed:{
        error_msg() {
            if (this.error_code == 3)
                this.msg = 'Fail to connect server...'
            return this.msg
        },
        show_error(){
            return this.error_code == 3001 ? 1 : 0
        }
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
            <div class="error_msg" :class="{'appear-up': show_error}" :style="{'opacity': show_error}">{{error_msg}}</div>
            <a href="javascript:;" @click="scroll_to_top" :style="{'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color}">
                <i class="fas fa-arrow-up" ref="to_top_icon"></i>
            </a>
        </div>

        <div class="chat-wrapper">
            <MessageList ref="chat_body"></MessageList>
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
</style>