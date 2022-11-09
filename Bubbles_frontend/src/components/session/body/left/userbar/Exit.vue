<script>
export default{
    inject: ['background_color', 'shadow_color'],
    methods: {
        exit(){
            localStorage.removeItem("token");
            this.$bus.emit("switch_state", 0);
        }
    }
}
</script>

<template>
    <div class="exit" :style="{'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color}">
        <a href="javascript:;" @click="exit">
            <i class="fas fa-door-open"></i>
        </a>
    </div>
</template>

<style scoped>
.exit {
    background-color: #fff;
    width: 40px;
    height: 40px;
    line-height: 40px;
    position: absolute;
    text-align: center;
    border-radius: 50%;
    box-shadow: 0 0 5px rgba(0, 0, 0, .3);

    transition: all .3s;
}
.edit a {
    width: 100%;
    height: 100%;
    display: block;
}

.edit i {
    font-size: 14px;
}
.edit:hover {
    color: rgb(124, 179, 255) !important;
    box-shadow: 0 0 10px rgb(124, 179, 255) !important;
}
</style>