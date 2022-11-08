<script>
import ChatroomItem from './ChatroomItem.vue';

export default {
    inject: ["chatrooms"],
    components: { ChatroomItem }
}
</script>

<template>
    <div>
        <ChatroomItem v-for="chatroom in chatrooms" :chatroom="chatroom"></ChatroomItem>
    </div>
</template>

<style scoped>

</style>