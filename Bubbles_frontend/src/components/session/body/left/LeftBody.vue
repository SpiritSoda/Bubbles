<script>
import VerticalSplit from '../../../utils/VerticalSplit.vue'
import HorizontalSplit from '../../../utils/HorizontalSplit.vue'
import UserBar from './userbar/UserBar.vue'
import OnlineList from './userlist/OnlineList.vue'
export default {
    inject:['onlines', 'local_id'],
    components: {
        VerticalSplit,
        HorizontalSplit,
        UserBar,
        OnlineList,
    },
    methods:{
        isLocal(user){
            return user.id === this.local_id
        }
    },
    computed:{
        local(){
            return this.onlines.findIndex(this.isLocal)
        }
    },
}
</script>

<template>
    <div class="left-body clearfix">
        <VerticalSplit :length="510" :left="-5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <VerticalSplit :length="510" :left="5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <HorizontalSplit :length="260" :top="231" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="260" :top="5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="260" :top="-5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <div class="userbar-wrapper clearfix">
            <UserBar :local_user="this.onlines[this.local]"></UserBar>
        </div>
        <div class="onlinelist-wrapper clearfix">
            <OnlineList :online_max="20" :onlines="this.onlines"></OnlineList>
        </div>
    </div>
</template>

<style scoped>
.left-body {
    height: 100%;
    width: 30%;
    box-sizing: border-box;

    border-top-left-radius: 10px;
    border-bottom-left-radius: 10px;
    background-color: #fff;
    position: absolute;
    left: 0px;
    bottom: 0px;
}

.userbar-wrapper {
    width: 100%;
    height: 230px;
    position: relative;
}

.onlinelist-wrapper {
    width: 100%;
    height: calc(100% - 230px);
    position: relative;
}
</style>