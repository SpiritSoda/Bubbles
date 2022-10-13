<script>
import VerticalSplit from '../../../utils/VerticalSplit.vue';
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import MessageBar from './message_bar/MessageBar.vue';
import Chat from './chat/Chat.vue'
export default {
    components: {
        VerticalSplit,
        HorizontalSplit,
        MessageBar,
        Chat
    },
    methods:{
        refresh_message(){
            console.log('Time to get more message from server...')
            this.$refs.to_top_icon.className="fas fa-undo"
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
    }
}
</script>

<template>
    <div class="right-body clearfix">
        <VerticalSplit :length="510" :left="-5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <VerticalSplit :length="510" :left="5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <HorizontalSplit :length="687" :top="-5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="687" :top="5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>

        <HorizontalSplit :length="687" :top="-126" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>
        <HorizontalSplit :length="687" :top="64" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>

        <div class="message-bar-wrapper">
            <MessageBar :scroll_to_bottom="scroll_to_bottom"></MessageBar>
        </div>

        <div class="to-top">
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
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background-color: #fff;
    position: absolute;
    top: 40px;
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
    border-radius: 50%;

    color: rgba(0, 0, 0, .4);
}

.to-top a:hover {
    color: rgb(124, 179, 255);
    box-shadow: 0 0 7px rgb(124, 179, 255);
}
</style>