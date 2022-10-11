<script>
import VerticalSplit from '../../../utils/VerticalSplit.vue';
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import MessageBar from './message_bar/MessageBar.vue';
export default{
    components:{
    VerticalSplit,
    HorizontalSplit,
    MessageBar
}
}
</script>

<template>
    <div class="right-body clearfix">
        <VerticalSplit :length="510" :left="-5" :color="'rgb(150, 150, 150)'"></VerticalSplit>
        <HorizontalSplit :length="697" :top="-5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>
        <HorizontalSplit :length="697" :top="5" :color="'rgb(150, 150, 150)'"></HorizontalSplit>

        <HorizontalSplit :length="697" :top="-126" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>
        <HorizontalSplit :length="697" :top="64" :color="'rgba(219, 222, 226, .8)'"></HorizontalSplit>

        <div class="message-wrapper">
            <MessageBar></MessageBar>
        </div>

        <div class="to-top">
            <a href="javascript:;">
                <i class="fas fa-arrow-up"></i>
            </a>
        </div>

        <div class="receive-wrapper">

        </div>
    </div>
</template>

<style scoped>
.right-body{
    height: 100%;
    width: 70%;
    box-sizing: border-box;
    
    border-top-right-radius: 10px;
    border-bottom-right-radius: 10px;
    background-color: #fff;
    position: absolute;
    right: 0px;
    bottom: 0px;
}
.message-wrapper{
    width: 650px;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 30px;
    margin: auto;
}
.receive-wrapper{
    width: 650px;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 126px;
    margin: auto;
}

.to-top{
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background-color: #fff;
    position: absolute;
    top: 40px;
    left: 0;
    right: 0;
    margin: auto;
}
.to-top a{
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: block;
    transition: color .2s, box-shadow .2s;
    box-shadow: 0 0 2px rgba(0, 0, 0, .3);
    border-radius: 50%;

    color: rgba(0, 0, 0, .4);
}
.to-top a:hover{
    color: rgb(124, 179, 255);
    box-shadow: 0 0 7px rgb(124, 179, 255);
}
    
</style>