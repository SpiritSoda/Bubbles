<script>
import OnlineUser from "./OnlineUser.vue";
export default {
    props: ["online_max", "onlines"],
    inject: ["userinfo"],
    components: { OnlineUser }
}
</script>

<template>
    <div>
        <span class="header">Online: {{onlines.length}}/{{online_max}}</span>
        <ul class="userlist-wrapper">
            <OnlineUser v-for="id in onlines" :userinfo="this.userinfo[id]" style="margin: 5px"></OnlineUser>
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