<script>
import Avatar from '../../../../utils/Avatar.vue';
import Edit from './Edit.vue';
import DisplayBar from '../../../../utils/DisplayBar.vue'
import VerticalSplit from '../../../../utils/VerticalSplit.vue'
import HorizontalSplit from '../../../../utils/HorizontalSplit.vue'
export default {
    props:['local_user'],
    components: {
        Avatar,
        Edit,
        DisplayBar,
        VerticalSplit,
        HorizontalSplit
    },
}
</script>
<template>
    <div class="user-bar clearfix">
        <div class="avatar-wrapper">
            <Avatar :avatar="this.local_user.icon" :r="120" :shadow="true"></Avatar>
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