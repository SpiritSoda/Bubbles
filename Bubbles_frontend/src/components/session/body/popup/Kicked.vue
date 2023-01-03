<script>
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import VerticalSplit from '../../../utils/VerticalSplit.vue';

export default {
    components: { HorizontalSplit, VerticalSplit },
    data(){
        return {
            token: ''
        }
    }
}
</script>

<template>
    <div class="kicked-pannel shake" @click.stop="() => { }">
        <HorizontalSplit :length="600" :top="45" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>

        <HorizontalSplit :length="600" :top="2" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="-2" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <div class="title">Oops! You are kicked by the chatroom admin ...</div>
    </div>
</template>

<style scoped>
.kicked-pannel {
    width: 600px;
    height: 65px;
    border-radius: 20px;

    background-color: #fff;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: calc(50% - 32px);
    overflow: hidden;

    transition: all .3s;
}
.title {
    position: absolute;
    height: 40px;
    line-height: 40px;
    top: 7px;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;

    /* color: rgb(239, 60, 60); */
    color: rgba(50, 50, 50, .8);
    font-size: 18px;
}
</style>