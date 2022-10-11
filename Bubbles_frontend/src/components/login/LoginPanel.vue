<script>
import UserInfo from './userinfo/UserInfo.vue';
import Avatar from './avatar/Avatar.vue'
export default {
    components: {
        UserInfo,
        Avatar
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
    },
    props:['error_code']
}
</script>

<template>
    <div class="login-panel clearfix">
        <Avatar @set_avatar="set_avatar" :error_code="error_code" :avatar="avatar"></Avatar>

        <div class="info-msg">
            <span>Tell us your name: </span>
        </div>

        <!-- pass avatar to userinfo components -->
        <UserInfo :avatar="avatar" :error_code="error_code"></UserInfo>

    </div>
</template>

<style scoped>
.login-panel {
    width: 500px;
    height: 500px;
    background-color: transparent;
    margin: 100px auto;
    position: relative;

    animation: appear ease 1s;
}

.info-msg {
    text-align: center;
    font-size: 24px;
    line-height: 1.6;
    font-family: inherit;
    color: #ffffff;

    position: absolute;
    top: 235px;
    left: 0;
    right: 0;
    margin: auto;
}
</style>