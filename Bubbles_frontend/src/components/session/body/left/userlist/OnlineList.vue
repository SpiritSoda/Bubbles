<script>
import OnlineUser from "./OnlineUser.vue";
export default {
    components: { OnlineUser },
    computed: {
        in_chatroom(){
            return this.$store.state.chatroom.selected_room > 0
        },
        total_user(){
            return this.in_chatroom ? this.$store.state.localuser.chatrooms[this.$store.state.chatroom.selected_room].total_user : 0
        },
        online_user(){
            return this.in_chatroom ? this.$store.state.chatroom.onlines.length : 0
        }
    }
}
</script>

<template>
    <div>
        <span class="header">Online: {{online_user}}/{{total_user}}</span>
        <ul class="userlist-wrapper fade-in" v-if="in_chatroom">
            <OnlineUser v-for="id in this.$store.state.chatroom.onlines" :id="id" style="margin: 5px"></OnlineUser>
        </ul>
        <span class="empty fade-in" v-else> You're not in a chatroom ...</span>
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
.empty{
    text-align: center;
    position: absolute;
    left: 0;
    right: 0;
    top: 45%;
    transform: translateY(-50%);
    margin: auto;
    color: rgba(53, 53, 53, .8);
    font-weight: bold;
}

</style>