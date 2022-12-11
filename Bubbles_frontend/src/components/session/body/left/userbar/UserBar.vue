<script>
import Avatar from '../../../../utils/Avatar.vue';
import DisplayBar from '../../../../utils/DisplayBar.vue'
import VerticalSplit from '../../../../utils/VerticalSplit.vue'
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue'
import Button from '../../../../utils/Button.vue';
import ConfirmButton from '../../../../utils/ConfirmButton.vue';
export default {
    components: {
        Avatar,
        DisplayBar,
        VerticalSplit,
        HorizontalSplit,
        Button,
        ConfirmButton
    },
    computed: {
        local_user(){
            let user = this.$store.state.userinfo.userinfo[this.$store.state.localuser.local_id]
            if(!user){
                this.$store.dispatch('userinfo/fetch_userinfo', this.$store.state.localuser.local_id)
                return {id: "0", username: "Unknown", avatar: -1}
            }
            return user;
        }
    },
    methods: {  
        exit(){
            localStorage.removeItem("token");
            this.$bus.emit("switch_state", 0);
        },
        edit(){
            this.$bus.emit("popup", 1);
        }
    }
}
</script>
<template>
    <div class="user-bar clearfix">
        <div class="avatar-wrapper">
            <Avatar :avatar="this.local_user.avatar" :r="120" :shadow="true"></Avatar>
        </div>
        <div class="exit-wrapper clearfix">
            <ConfirmButton :r="40" :click="exit" :fa_icon="'fa-door-open'" :title="'Log Out'"></ConfirmButton>
        </div>
        <div class="edit-wrapper clearfix">
            <Button :r="40" :click="edit" :fa_icon="'fa-edit'" :title="'Edit Profile'"></Button>
        </div>
        <div class="username">
            <DisplayBar :width="160" :height="30" :text="this.local_user.username"></DisplayBar>
        </div>
    </div>
</template>
<style scoped>
.user-bar {
    width: 100%;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
}

.avatar-wrapper {
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    margin-top: 30px;
}

.exit-wrapper {
    position: absolute;
    left: 230px;
    margin-top: 80px;
}
.edit-wrapper {
    position: absolute;
    left: 30px;
    margin-top: 80px;
}

.username {
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    margin-top: 170px;
}
</style>