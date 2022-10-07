<script>
import AvatarList from "./AvatarList.vue"
export default {
    components: {
        AvatarList
    },
    data() {
        return {
            show_list: false
        }
    },
    computed: {
        color() {
            return this.show_list ? 'rgb(124, 179, 255)' : '#424242';
        },
        border_color() {
            return this.show_list ? 'rgb(124, 179, 255)' : 'rgb(169, 176, 185)';
        }
    }
}
</script>

<template>
    <div class="avatar-wrapper">
        <div class="avatar-show" @click="show_list=!show_list" :style="{'border-color': border_color}">
            <i class="fas" :class="{'fa-user':!show_list, 'fa-edit':show_list}" :style="{'color': color}"></i>
            <div class="avatar"></div>
        </div>
        <AvatarList :show=show_list></AvatarList>
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

    transition: all .3s;
}

.avatar-show:hover {
    width: 150px;
    height: 150px;
    border: 5px solid rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1);
}

.avatar-show:hover i {
    color: rgb(124, 179, 255);
}

.avatar-show i {
    font-size: 60px;
    color: #424242;
    text-align: center;

    position: absolute;
    top: 50%;
    transform: translateY(-60%);
    left: 0;
    right: 0;
    margin: auto;
    transition: all .3s;
}
</style>