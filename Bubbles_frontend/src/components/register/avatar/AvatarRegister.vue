<script>
import AvatarList from "./AvatarList.vue"
import Avatar from '../../utils/Avatar.vue'
export default {
    props: ['avatar'],
    components: {
        AvatarList,
        Avatar
    },
    data() {
        return {
            avatar_hover: false,
            show_list: false,
            avatar_count: 12
        }
    },
    computed: {
        error_code(){
            return this.$store.state.error.error_code
        },
        avatar_show() {
            let style = this.show_list || this.avatar_hover ? { 'width': '130px', 'height': '130px', 'border': '5px solid rgb(124, 179, 255)', 'background-color': 'rgba(255, 255, 255, 1)' } : {};
            if(this.show_list){
                style['transform'] = 'translateY(-120px)';
            }
            return style;
        },
        avatar_icon() {
            let style = this.show_list ? { 'color': 'rgb(124, 179, 255)', 'transform': 'translate(124px, -30px)' } : (this.avatar_hover ? { 'color': 'rgb(124, 179, 255)'} : {});
            return style;
        },
        avatar_selected(){
            return this.avatar > 0
        }
    },
    methods: {
        reverse_show(){
            this.show_list = !this.show_list
            this.$emit('reverse_state')
        },
        set_avatar(id){
            this.$emit('set_avatar', id)
        }
    },
}
</script>

<template>
    <div>
        <div class="avatar-show" @click="reverse_show();" @mouseenter="avatar_hover=true"
            @mouseleave="avatar_hover=false" :style="avatar_show" :class="{'shake': error_code == 2002}">
            <i class="fas fa-user" :style="avatar_icon"></i>
            <i class="fas fa-edit" :style="avatar_icon"></i>
            <div class="avatar" v-if="avatar_selected">
                <Avatar :avatar="avatar" :r="130"></Avatar>
            </div>
        </div>
        <AvatarList :show=show_list @set_avatar=set_avatar @reverse_show=reverse_show></AvatarList>
    </div>
</template>

<style scoped>
.avatar-show {
    width: 130px;
    height: 130px;
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

.avatar-show .fa-user {
    font-size: 50px;
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
.avatar-show .fa-edit {
    font-size: 50px;
    color: rgb(111, 113, 115);
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