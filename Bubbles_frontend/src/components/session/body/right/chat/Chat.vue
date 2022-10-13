<script>
import Message from './Message/Message.vue';
export default {
    inject: [
        "messages",
    ],
    components: { Message },
    methods:{
        at_top(){
            return this.$refs.chat_body.scrollTop === 0
        },
        scroll_to_top(){
            this.$refs.chat_body.scrollTop = 0
        },
        scroll_to_bottom(){
            this.$refs.chat_body.scrollTop = this.$refs.chat_body.scrollHeight
        }
    }
}
</script>

<template>
    <div class="messages-wrapper" ref="chat_body">
        <div class="message clearfix" v-for="message in this.messages">
            <Message :message="message"></Message>
        </div>
    </div>
</template>

<style scoped>
.message {
    margin-top: 6px;
}
.message:last-of-type{
    margin-bottom: 12px;
}

.messages-wrapper {
    width: 101%;
    height: 100%;
    padding-bottom: 10px;
    overflow-y: scroll;
    scroll-behavior: smooth;
}
</style>