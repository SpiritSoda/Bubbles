<script>
import Button from '../../../../../utils/Button.vue';
import ConfirmButton from '../../../../../utils/ConfirmButton.vue';
export default {
    components: {
        Button,
        ConfirmButton
    },
    data(){
        return {
            show_menu: false
        }
    },
    methods: {
        change_menu(){
            this.show_menu = !this.show_menu
        },
        back_to_select(){
            this.$store.commit('reset_chatroom', {})
        },
        generate_invite(){
            this.$bus.emit('popup', 3)
        }
    }
}
</script>

<template>
    <div class="menu clearfix" :style="{width: show_menu ? '140px': '34px'}">
        <div class="menu-entry">
            <Button :r="35" :click="change_menu" :fa_icon="'fa-window-maximize'" :title="'Menu'"></Button>
        </div>
        <div class="menu-list">
            <div class="menu-item" :style="{left: 34 * 0 + 'px'}">
                <Button :r="30" :click="back_to_select" :fa_icon="'fa-home'" :title="'Home'"></Button>
            </div>
            <div class="menu-item" :style="{left: 34 * 1 + 'px'}">
                <Button :r="30" :click="generate_invite" :fa_icon="'fa-users'" :title="'Invite Friend'"></Button>
            </div>
            <div class="menu-item" :style="{left: 34 * 2 + 'px'}">
                <ConfirmButton :r="30" :click="generate_invite" :fa_icon="'fa-running'" :title="'Quit Chatroom'"></ConfirmButton>
            </div>
        </div>
    </div>
</template>

<style scoped>
.menu{
    height: 34px;
    box-shadow: 0 0 4px rgba(0, 0, 0, .3);
    border-radius: 34px;
    background-color: rgb(244, 244, 244);

    transition: all .3s;
    overflow: hidden;
    position: relative;
}
.menu-entry{
    position: absolute;
    right: 0;
    z-index: 101;
}
.menu-list{
    height: 36px;
    width: 100px;
    float: left;

    overflow: hidden;
}
.menu-item{
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    margin-left: 3px;
    z-index: 100;
}
</style>