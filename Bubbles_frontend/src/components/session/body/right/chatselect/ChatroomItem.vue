<script>
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue';


export default {
    props: ["chatroom"],
    components: { HorizontalSplit },
    methods: {
        select_chatroom(){
            this.$store.commit('chatroom/select_chatroom', this.chatroom.id);
        }
    },
    computed:{
        chatroom_background(){
            return {
                'background-image': 'url(\'/chatroom/' + this.chatroom.icon + '.jpg\')',
                'background-size': 'cover'
            }
        }
    }
}
</script>

<template>
    <div class="chatroom-wrapper clearfix fade-in">
        <div class="chatroom-icon" :style="this.chatroom_background">
            <a href="javascript:;" class="chatroom-icon-inner" @click="select_chatroom"></a>
        </div>
        <div class="chatroom-info">
            <a href="javascript:;" class="chatroom-title" @click="select_chatroom">{{this.chatroom.title}}</a>
            <div class="underline"></div>
            <div class="chatroom-comment">{{this.chatroom.comment}}</div>
            <div class="chatroom-online">{{"Onlines: " + chatroom.onlines + "/" + chatroom.total_user}}</div>
        </div>
    </div>
</template>

<style scoped>
.chatroom-icon {
    width: 56px;
    height: 56px;
    border-radius: 50%;

    background-color: rgba(255, 255, 255, 1);
    background-position: center;

    position: relative;
    left: 12px;

    margin-bottom: 18px;

    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);

    overflow: hidden;
    transition: all .3s;
    
    float: left;
    transition: all .3s;
}
.chatroom-icon:hover{
    box-shadow: 0 0px 10px rgb(124, 179, 255);
    transform: perspective(50px) translateZ(3px);
}
.chatroom-icon-inner{
    width: 100%;
    height: 100%;
    display: block;
}
.chatroom-info{
    height: 56px;
    width: 560px;

    position: relative;
    left: 30px;

    float: left;
}
.chatroom-title{
    color: #000;
    font-size: 18px;
    font-weight: bold;

    position: absolute;
    top: 6px;

    transition: all .3s;
}
.chatroom-title:hover{
    color: rgb(124, 179, 255);
}
.chatroom-comment{
    color: rgb(119, 119, 119);
    font-size: 12px;

    position: absolute;
    top: 34px;
    
}
.underline{
    width: 150px;
    height: 1px;
    position: absolute;
    top: 30px;
    margin: auto;
    background: linear-gradient(to left,rgba(255, 255, 255, 0.0), rgba(150, 150, 150, 1) 70%, rgba(150, 150, 150, 1) 92%, rgba(255, 255, 255, 0.0));
}
.chatroom-online{
    position: absolute;
    top: 20px;
    right: 0px;
}
</style>