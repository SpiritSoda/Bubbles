<script>
import Avatar from '../../../../../utils/Avatar.vue';
import Bubble from './Bubble.vue';
export default {
    components: { Avatar, Bubble },
    props: ['message'],
    computed: {
        local_id(){
            return this.$store.state.localuser.local_id
        },
        userinfo(){
            return this.$store.state.userinfo.userinfo
        },
        user() {
            let user = this.userinfo[this.message.id]
            if(!user){
                this.$store.dispatch('userinfo/fetch_userinfo', this.message.id)
                return {id: "0", username: "Unknown", avatar: -1}
            }
            return user
        },
        is_local(){
            return this.message.id === this.local_id
        },
        bubble_color(){
            return this.is_local ? 'rgb(124, 179, 255, .1)' : 'rgba(255, 255, 255, .1)'
        },
    }
}
</script>

<template>
    <div v-if="this.user" class="clearfix" style="padding-bottom: 3px; width: 100%;">
        <div class="clearfix" v-if="this.is_local" style="position: relative">
            <div class="avatar-wrapper right">
                <Avatar :avatar="this.user.avatar" :r="56"></Avatar>
            </div>
            <div class="right clearfix" style="right: 13px">
                <div class="username right">
                    {{this.user.username}}
                </div>
                <div class="bubble-wrapper clearfix right">
                    <Bubble :content="this.message.content" :color="this.bubble_color"></Bubble>
                </div>
            </div>
        </div>

        <div class="clearfix" v-else style="position: relative">
            <div class="avatar-wrapper left">
                <Avatar :avatar="this.user.avatar" :r="56"></Avatar>
            </div>
            <div class="left clearfix" style="left: 13px">
                <div class="username left">
                    {{this.user.username}}
                </div>
                <div class="bubble-wrapper clearfix left">
                    <Bubble :content="this.message.content" :color="this.bubble_color"></Bubble>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.avatar-wrapper {
    width: 56px;
    height: 56px;
    position: relative;
}
.bubble-wrapper{
    position: relative;
}
.left{
    position: relative;
    float: left;
}
.right{
    position: relative;
    float: right;
}
.username{
    position: relative;
    width: 100%;
    top: 6px;
    font-size: 12px;
}
.username.left{
    text-align: left;
}
.username.right{
    text-align: right;
}
</style>