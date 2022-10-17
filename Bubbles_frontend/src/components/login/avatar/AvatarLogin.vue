<script>
import Avatar from '../../utils/Avatar.vue'
export default {
    props: ['avatar'],
    components: {
        Avatar
    }
}
</script>

<template>
    <div class="avatar-wrapper">
        <div class="avatar-show">
            <i class="fas fa-user"></i>
            <div class="avatar">
                <Avatar :avatar="avatar" :r="150"></Avatar>
            </div>
        </div>
    </div>
</template>

<style scoped>
.avatar-show {
    width: 150px;
    height: 150px;
    background-color: rgba(255, 255, 255, .9);
    margin: 60px auto;
    margin-bottom: 20px;

    border: 10px double rgb(169, 176, 185);
    border-radius: 50%;
    position: relative;

    overflow: hidden;
    transition: all .3s;

    z-index: 9999;
}
.avatar-show:hover{ 
    width: 150px;
    height: 150px; 
    border: 5px solid rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1)
}

.avatar-show .fa-user {
    font-size: 60px;
    color: rgb(111, 113, 115);
    text-align: center;

    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    margin: auto;
    transform: translateY(-60%);
    transition: all .3s;
}
.avatar{
    width: 145px;
    height: 145px;
    background-color: rgba(255, 255, 255, 1);
    background-size: 155px;
    background-position: center;

    border-radius: 50%;
    position: absolute;
    left: -5px;
    right: 0;
    margin: auto;

    transition: all .3s;

}
</style>