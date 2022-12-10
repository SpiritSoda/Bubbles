<script>
import OnlineUser from "./OnlineUser.vue";
export default {
    components: { OnlineUser }
}
</script>

<template>
    <div>
        <span class="header">Online: {{this.$store.state.chatroom.onlines.length}}/{{this.$store.state.chatroom.total_user}}</span>
        <ul class="userlist-wrapper">
            <OnlineUser v-for="id in this.$store.state.chatroom.onlines" :id="id" style="margin: 5px"></OnlineUser>
        </ul>
    </div>
</template>

<style scoped>
.userlist-wrapper {
    width: 100%;
    position: absolute;
    top: 35px;
    height: 250px;
    margin-left: 15px;
    overflow: auto;
}

.header {
    position: absolute;
    text-align: center;
    display: block;
    top: 15px;
    left: 0;
    right: 0;
    margin: auto;
}

</style>