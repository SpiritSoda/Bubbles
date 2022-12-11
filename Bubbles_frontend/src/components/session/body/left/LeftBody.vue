<script>
import VerticalSplit from '../../../utils/VerticalSplit.vue'
import HorizontalSplit from '../../../utils/HorizontalSplit.vue'
import UserBar from './userbar/UserBar.vue'
import OnlineList from './userlist/OnlineList.vue'
export default {
    inject:['background_color'],
    components: {
        VerticalSplit,
        HorizontalSplit,
        UserBar,
        OnlineList,
    }
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
            <UserBar></UserBar>
        </div>
        <div class="onlinelist-wrapper clearfix">
            <OnlineList></OnlineList>
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
    background-color: v-bind(background_color);
    
    position: absolute;
    left: 0px;
    bottom: 0px;

    transition: all .3s;
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