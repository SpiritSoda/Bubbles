<script>
import Avatar from '../../../../utils/Avatar.vue';
import ConfirmButton from '../../../../utils/ConfirmButton.vue';
export default{
    props: ['id'],
    components: { Avatar, ConfirmButton },
    computed: {
        user(){
            let user = this.$store.state.userinfo.userinfo[this.id]
            if(!user){
                this.$store.dispatch('userinfo/fetch_userinfo', this.id)
                return {id: "0", username: "Unknown", avatar: -1}
            }
            return user;
        },
        can_ban(){
            // console.log(this.$store.state.localuser.chatrooms[this.$store.state.chatroom.selected_room])
            // console.log(this.$store.state.localuser.local_id == this.$store.state.localuser.chatrooms[this.$store.state.chatroom.selected_room].admin)
            return this.$store.state.localuser.local_id == this.$store.state.localuser.chatrooms[this.$store.state.chatroom.selected_room].admin && this.id != this.$store.state.localuser.local_id
        }
    },
    methods:{
        ban_user(){
            this.$store.dispatch('ban_user', {
                userId: this.id,
                chatroomId: this.$store.state.chatroom.selected_room,
                on_error: () => {
                    this.$store.commit("error/set_error_code", 3001)
                }
            })
        }
    }
}
</script>

<template>
    <li class="online-wrapper">
        <div class="avatar-wrapper">
            <Avatar :avatar="this.user.avatar" :r="44"></Avatar>
        </div>
        <div class="username">{{this.user.username}}</div>
        <div class="ban-button" v-if="can_ban">
            <ConfirmButton :r="30" fa_icon="fa-times" :r_shadow="0" :title="'Kick'" :click="ban_user"></ConfirmButton>
        </div>
    </li>
</template>

<style scoped>
.online-wrapper{
    margin-top: 5px;
    border-radius: 24px;
    position: relative;
    height: 44px;
    width: 260px;
    line-height: 44px;
    transition: all .2s;
}
.online-wrapper:hover{
    box-shadow: 0 0 5px rgb(124, 179, 255);
    transform: perspective(50px) translateZ(1px);
}
.avatar-wrapper{
    width: 44px;
    height: 44px;
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    line-height: 44px;
}
.username{
    position: absolute;
    width: 200px;
    height: 44px;
    left: 50px;
}
.ban-button{
    position: absolute;
    right: 10px;
    top: 7px
}
</style>