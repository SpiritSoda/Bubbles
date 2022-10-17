<script>
import UserInfo from './userinfo/UserInfo.vue';
import AvatarLogin from './avatar/AvatarLogin.vue';
export default {
    components: {
    UserInfo,
    AvatarLogin
},
    data(){
        return {
            avatar: -1,
        }
    },
    methods:{
        set_avatar(avatar_id){
            this.avatar = avatar_id
        }
    }
}
</script>

<template>
    <div class="login-panel clearfix">
        <AvatarLogin @set_avatar="set_avatar" :avatar="avatar"></AvatarLogin>

        <!-- pass avatar to userinfo components -->
        <UserInfo @set_avatar="set_avatar" :avatar="avatar"></UserInfo>

    </div>
</template>

<style scoped>
.login-panel {
    width: 500px;
    height: 500px;
    background-color: transparent;
    margin: 100px auto;
    position: relative;

    animation: appear-down ease 1s;
}

</style>