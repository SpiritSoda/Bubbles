<script>
import ChatroomItem from './ChatroomItem.vue';

export default {
    components: { ChatroomItem }
}
</script>

<template>
    <div>
        <ChatroomItem v-for="chatroom in this.$store.state.localuser.chatrooms" :chatroom="chatroom"></ChatroomItem>
    </div>
</template>

<style scoped>

</style>