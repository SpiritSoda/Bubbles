<script>
import VerticalSplit from '../../../utils/VerticalSplit.vue';
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import ChatroomBody from './chatroom/ChatroomBody.vue';
import ChatroomSelect from './chatselect/ChatroomSelect.vue'
export default {
    components: {
        VerticalSplit,
        HorizontalSplit,
        ChatroomBody,
        ChatroomSelect
    },
    inject: ["background_color"],
    computed: {
        state(){
            return this.$store.state.chatroom.selected_room == 0 ? 0 : 1
        }
    }
}
</script>

<template>
    <div class="right-body clearfix">
        <VerticalSplit :length="510" :left="-5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <VerticalSplit :length="510" :left="1" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <HorizontalSplit :length="687" :top="-5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="687" :top="5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>

        <ChatroomSelect v-if="this.state == 0"></ChatroomSelect>
        <ChatroomBody v-if="this.state == 1"></ChatroomBody>
    </div>
</template>

<style scoped>
.right-body {
    height: 100%;
    width: 70%;
    box-sizing: border-box;

    border-top-right-radius: 10px;
    border-bottom-right-radius: 10px;
    background-color: v-bind(background_color);
    position: absolute;
    right: 0px;
    bottom: 0px;
    overflow: hidden;

    transition: all .3s;
}
</style>