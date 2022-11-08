<script>
import Avatar from '../../../../utils/Avatar.vue';
import Edit from './Edit.vue';
import DisplayBar from '../../../../utils/DisplayBar.vue'
import VerticalSplit from '../../../../utils/VerticalSplit.vue'
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue'
export default {
    inject:["userinfo", 'local_id'],
    components: {
        Avatar,
        Edit,
        DisplayBar,
        VerticalSplit,
        HorizontalSplit
    },
    computed: {
        local_user(){
            let user = this.userinfo[this.local_id]
            if(!user){
                this.$bus.emit('require_userinfo', this.local_id)
                return {id: "0", username: "Unknown", avatar: -1}
            }
            return user;
        }
    }
}
</script>
<template>
    <div class="user-bar clearfix">
        <div class="avatar-wrapper">
            <Avatar :avatar="this.local_user.avatar" :r="120" :shadow="true"></Avatar>
        </div>
        <div class="edit-wrapper clearfix">
            <Edit></Edit>
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

.edit-wrapper {
    position: absolute;
    left: 230px;
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