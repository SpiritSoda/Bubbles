<script>
import VerticalSplit from '../../../utils/VerticalSplit.vue';
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import MessageBar from './message_bar/MessageBar.vue';
import Chat from './chat/Chat.vue'
import $bus from '../../../../utils/eventbus';
export default {
    inject:['error_code'],
    data(){
        return {
            msg: 'Fail to connect server...'
        }
    },
    components: {
        VerticalSplit,
        HorizontalSplit,
        MessageBar,
        Chat
    },
    methods:{
        refresh_message(){
            // console.log('Time to get more message from server...')
            this.$refs.to_top_icon.className="fas fa-undo rotate"
            setTimeout(() => {
                this.$refs.to_top_icon.className="fas fa-arrow-up"
                $bus.emit('error', 3)
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
            return this.error_code == 3 ? 1 : 0
        }
    }
}
</script>

<template>
    <div class="right-body clearfix">
        <VerticalSplit :length="510" :left="-5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <!-- <VerticalSplit :length="510" :left="5" :color="'rgb(150, 150, 150)'"></VerticalSplit> -->
        <HorizontalSplit :length="687" :top="-5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="687" :top="5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>

        <HorizontalSplit :length="687" :top="-126" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>
        <HorizontalSplit :length="687" :top="64" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>

        <div class="message-bar-wrapper">
            <MessageBar :scroll_to_bottom="scroll_to_bottom"></MessageBar>
        </div>

        
        <div class="to-top">
            <div class="error_msg" :class="{'appear-up': show_error}" :style="{'opacity': show_error}">{{error_msg}}</div>
            <a href="javascript:;" @click="scroll_to_top">
                <i class="fas fa-arrow-up" ref="to_top_icon"></i>
            </a>
        </div>

        <div class="chat-wrapper">
            <Chat ref="chat_body"></Chat>
        </div>
    </div>
</template>

<style scoped>
.right-body {
    height: 100%;
    width: 70%;
    box-sizing: border-box;

    border-top-right-radius: 10px;
    border-bottom-right-radius: 10px;
    background-color: #fff;
    position: absolute;
    right: 0px;
    bottom: 0px;

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
    width: 650px;
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
}

.to-top a {
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: block;
    transition: color .2s, box-shadow .2s;
    box-shadow: 0 0 2px rgba(0, 0, 0, .3);
    background-color: #fff;
    border-radius: 50%;

    position: absolute;
    left: 0;
    right: 0;
    margin: auto;

    color: rgba(0, 0, 0, .4);
}

.to-top a:hover {
    color: rgb(124, 179, 255);
    box-shadow: 0 0 7px rgb(124, 179, 255);
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