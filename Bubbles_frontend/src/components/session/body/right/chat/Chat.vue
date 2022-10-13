<script>
import Message from './Message/Message.vue';
export default {
    inject: [
        "messages",
    ],
    components: { Message }
}
</script>

<template>
    <div class="messages-wrapper">
        <div class="message clearfix" v-for="message in this.messages">
            <Message :message="message"></Message>
        </div>
    </div>
</template>

<style scoped>
.message {
    margin-top: 6px;
}

.messages-wrapper {
    width: 101%;
    height: 100%;
    padding-bottom: 10px;
    overflow-y: scroll;
}
</style>