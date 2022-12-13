<script>
import HorizontalSplit from '../../../utils/HorizontalSplit.vue';
import VerticalSplit from '../../../utils/VerticalSplit.vue';

export default {
    components: { HorizontalSplit, VerticalSplit },
    data(){
        return {
            token: ''
        }
    },
    mounted(){
        this.$axios.post(
            '/api/chatroom/inviteToken',
            {
                id: this.$store.state.chatroom.selected_room
            },
            {
                headers: {
                    token: this.$store.state.localuser.token
                }
            }
        )
        .then(
            (reponse) => {
                this.token = reponse.data.data.token
            }
        )
    }
}
</script>

<template>
    <div class="invite-token-pannel" @click.stop="() => { }">
        <HorizontalSplit :length="600" :top="5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <HorizontalSplit :length="600" :top="-5" :color="'rgba(150, 150, 150, 1)'"></HorizontalSplit>
        <VerticalSplit :length="100" :left="5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <VerticalSplit :length="100" :left="-5" :color="'rgba(150, 150, 150, 1)'"></VerticalSplit>
        <HorizontalSplit :length="600" :top="45" :color="'rgba(224, 224, 224, 0.9)'"></HorizontalSplit>
        <div class="title">Send Invite Token to Your Friends</div>
        <input type="text" class="token" v-model="token" :placeholder="'...'" readonly autocomplete="off">
    </div>
</template>

<style scoped>
.invite-token-pannel {
    width: 600px;
    height: 125px;
    border-radius: 20px;

    background-color: #fff;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: 50%;
    transform: translateY(-50%);
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

    color: rgba(50, 50, 50, .6);
    font-size: 18px;
}
.token{
    width: 93%;
    height: 28px;
    line-height: 28px;

    border-radius: 14px;

    padding-left: 18px;
    padding-right: 18px;
    box-sizing: border-box;

    font-size: 12px;
    outline: none;
    border: none;

    position: absolute;
    left: 0;
    right: 0;
    top: 65px;
    margin: auto;

    background-color: rgb(246, 246, 246);
    box-shadow: 0 0px 2px rgba(0, 0, 0, .3);

    transition: all .3s;
}
.token:focus {
    box-shadow: 0 0 7px rgb(124, 179, 255);
    background-color: #fff;
}

.token:hover {
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
}
</style>