<script>
export default {
    data() {
        return {
        }
    },
    props: ['show'],
    computed: {
        avatar_count(){
            return this.$store.state.global.avatar_count
        },
        display_height() {
            if (!this || !this.show) {
                return 0 + 'px';
            }
            if (this.show === true)
                return 340 + 'px';
            else
                return 0 + 'px';
        },
        display_border() {
            if (!this || !this.show) {
                return 0 + 'px';
            }
            if (this.show === true)
                return 5 + 'px';
            else
                return 0 + 'px';
        },
        display_padding() {
            if (!this || !this.show) {
                return '0px';
            }
            if (this.show === true)
                return '20px';
            else
                return '0px';
        }
    },
    methods: {
        reverse_show() {
            this.$emit("reverse_show")
        },
        set_avatar(id) {
            this.reverse_show()
            this.$emit("set_avatar", id)
        }
    },
}
</script>

<template>
    <div class="avatar-list-wrapper"
        :style="{'height': display_height, 'border-width': display_border, 'padding': display_padding + ' 0px'}">
        <div class="avatar-list-roller">
            <div class="avatar-icon" v-for="id in avatar_count"
                :style="{'background-image':'url(\'/avatars/genshin/' + id + '.jpg\')'}" @click="set_avatar(id)">
            </div>
        </div>

        <div class="unshow" @click="reverse_show()">
            <i class="fas fa-chevron-up"></i>
        </div>
    </div>
</template>

<style scoped>
.avatar-list-wrapper {
    position: absolute;
    overflow: hidden;
    width: 361px;
    height: 0px;
    border: 5px solid rgb(124, 179, 255);
    border-radius: 40px;
    background-color: rgba(255, 255, 255, 1);
    padding-top: 0px;

    box-sizing: border-box;

    top: 60px;
    left: 0px;
    right: 0;
    margin: auto;

    transition: all .3s;
    z-index: 1000;
}
.avatar-list-roller{
    width: 103%;
    height: 100%;
    overflow: auto;
}
.unshow {
    width: 40px;
    height: 20px;
    line-height: 20px;
    background-color: rgb(124, 179, 255);

    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;

    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
    text-align: center;
    z-index: 999;
    transition: all .3s;
}

.unshow i {
    color: #fff;
    margin-top: 2px;
}

.unshow:hover {
    background-color: rgb(71, 147, 253);
}

.avatar-icon {
    float: left;
    margin: 10px 10px;
    background-clip: padding-box;
    background-size: 93px;
    border: 2px solid rgb(71, 147, 253);
    border-radius: 50%;

    width: 93px;
    height: 93px;

    transition: all .3s;
}

.avatar-icon:hover {
    border-color: rgb(71, 147, 253);
    box-shadow: 0 0 15px rgba(71, 147, 253, .8);
    transform: perspective(800px) translateZ(80px);
}
</style>