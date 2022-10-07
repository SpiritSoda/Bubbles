<script>
import UserInfo from './userinfo/UserInfo.vue';
import Avatar from './avatar/Avatar.vue'
export default {
    components: {
        UserInfo,
        Avatar
    }
}
</script>

<template>
    <div class="login-panel clearfix">
        <Avatar></Avatar>

        <div class="info-msg">
            <span>Tell us your name: </span>
        </div>

        <UserInfo></UserInfo>

    </div>
</template>

<style scoped>
.login-panel {
    width: 500px;
    height: 500px;
    background-color: transparent;
    margin: 100px auto;
    position: relative;
}

.info-msg {
    text-align: center;
    font: 300 normal 24px/1.6 Avant Garde, Avantgarde, Century Gothic, CenturyGothic, AppleGothic, sans-serif;
    color: #ffffff;

    position: absolute;
    top: 235px;
    left: 0;
    right: 0;
    margin: auto;
}
</style>