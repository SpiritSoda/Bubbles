<script>
import VerticalSplit from '../../../utils/VerticalSplit.vue'
import HorizontalSplit from '../../../utils/HorizontalSplit.vue'
import UserBar from './userbar/UserBar.vue'
import UserList from './userlist/UserList.vue'
export default{
    components: {
    VerticalSplit,
    HorizontalSplit,
    UserBar,
    UserList,
}
}
</script>

<template>
    <div class="left-body clearfix">
        <VerticalSplit :length="500" :left="300" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <VerticalSplit :length="500" :left="5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <HorizontalSplit :length="240" :top="231" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="240" :top="5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="240" :top="-5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <div class="userbar-wrapper clearfix">
            <UserBar></UserBar>
        </div>
        <div class="userlist-wrapper clearfix">
            <UserList></UserList>
        </div>
    </div>
</template>

<style scoped>
.left-body{
    height: 100%;
    width: 30%;
    box-sizing: border-box;
    
    /* border: 2px solid black; */
    background-color: #fff;
    position: absolute;
    left: 0px;
    bottom: 0px;
}
.userbar-wrapper{
    width: 100%;
    height: 230px;
    position: relative;
}
.userlist-wrapper{
    width: 100%;
    height: calc(100% - 230px);
    position: relative;
}
</style>