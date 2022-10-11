<script>
export default {
    props: ['avatar'],
}
</script>

<template>
    <div class="avatar-show clearfix">
        <!-- <i class="fas fa-user"></i> -->
        <div class="avatar" :style="{'background-image':'url(\'/avatars/' + this.avatar + '.webp\')'}"></div>
    </div>
</template>

<style scoped>
.avatar-show {
    width: 120px;
    height: 120px;
    background-color: rgba(255, 255, 255, 1);

    /* border: 5px solid rgb(169, 176, 185); */
    border-radius: 50%;
    position: absolute;
    left: 0;
    right: 0;
    margin:auto;

    box-shadow: 0 0px 10px rgba(0, 0, 0, .3);

    overflow: hidden;
    transition: all .3s;

}
.avatar{
    width: calc(100% - 5px);
    height: calc(100% - 5px);
    background-color: rgba(255, 255, 255, 1);
    background-size: calc(100% + 5px);
    background-position: center;

    border-radius: 50%;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: 50%;
    transform: translateY(-50%);

    transition: all .3s;

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
</style>