<script>
import AvatarList from "./AvatarList.vue"
export default {
    components: {
        AvatarList
    },
    data() {
        return {
            avatar_hover: false,
            show_list: false
        }
    },
    computed: {
        avatar_show() {
            let style = this.show_list || this.avatar_hover ? { 'width': '150px', 'height': '150px', 'border': '5px solid rgb(124, 179, 255)', 'background-color': 'rgba(255, 255, 255, 1)' } : {};
            if(this.show_list){
                style['transform'] = 'translateY(-80px)';
            }
            return style;
        },
        avatar_icon() {
            let style = this.show_list ? { 'color': 'rgb(124, 179, 255)', 'transform': 'translate(140px, -34px)' } : (this.avatar_hover ? { 'color': 'rgb(124, 179, 255)'} : {});
            return style;
        }
    },
    methods: {
        reverse_show(){
            this.show_list = !this.show_list
        }
    },
}
</script>

<template>
    <div class="avatar-wrapper">
        <div class="avatar-show" @click="reverse_show();" @mouseenter="avatar_hover=true"
            @mouseleave="avatar_hover=false" :style="avatar_show">
            <i class="fas fa-user" :style="avatar_icon"></i>
            <i class="fas fa-edit" :style="avatar_icon"></i>
            <div class="avatar"></div>
        </div>
        <AvatarList :show=show_list @reverse_show=reverse_show></AvatarList>
    </div>
</template>

<style>
.avatar-show {
    width: 140px;
    height: 140px;
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

/* .avatar-show:hover {
    width: 150px ;
    height: 150px;
    border: 5px solid rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1);
}

.avatar-show:hover i {
    color: rgb(124, 179, 255);
} */

.avatar-show .fa-user {
    font-size: 60px;
    color: #424242;
    text-align: center;

    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    margin: auto;
    transform: translateY(-60%);
    transition: all .3s;
}
.avatar-show .fa-edit {
    font-size: 60px;
    color: #424242;
    text-align: center;

    position: absolute;
    top: 50%;
    left: calc(-200% + 20px);
    right: 0;
    margin: auto;
    transform: translateY(-60%);
    transition: all .3s;
}
</style>