<script>
import Avatar from '../../../../utils/Avatar.vue';
export default{
    inject: ["userinfo", "onlines"],
    props: ["id"],
    components: { Avatar },
    computed: {
        user(){
            let user = this.userinfo[this.id]
            if(!user){
                this.$bus.emit('require_userinfo', this.id)
                return {id: "0", username: "Unknown", avatar: -1}
            }
            return user;
        }
    }
}
</script>

<template>
    <li class="online-wrapper">
        <div class="avatar-wrapper">
            <Avatar :avatar="this.user.avatar" :r="44"></Avatar>
            <span class="username">{{this.user.username}}</span>
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
</style>