<script>
export default {
    props: {
        icon: {
            type: Number,
            default: 0
        },
        r: {
            type: Number,
            default: 20
        },
        shadow: {
            type: Boolean,
            default: false
        }
    },
    computed:{
        icon_border_style(){
            let style = {
                'width': this.r + 'px', 
                'height': this.r + 'px',
            }
            if(this.shadow){
                style['box-shadow'] = '0 0px 10px rgba(0, 0, 0, .3)';
            }
            return style
        },
        icon_style(){
            return {
                'width': (this.r - 5) + 'px', 
                'height': (this.r - 5) + 'px', 
                'background-image':'url(\'chatroom/' + this.icon + '.jpg\')',
                'background-size': (this.r - 5) + 'px',
                'opacity': this.icon > 0 ? 1.0 : 0.0
            }
        }
    }
}
</script>

<template>
    <div class="icon-border" :style="this.icon_border_style">
        <div class="icon" :style="this.icon_style" v-if="this.icon > 0"></div>
        <i class="fas fa-home" :style="{'font-size': (this.r / 2) - 8 + 'px'}" v-if="this.icon <= 0"></i>
    </div>
</template>

<style scoped>
.icon-border{
    background-color: rgba(255, 255, 255, 1);
    border-radius: 50%;
    position: absolute;
    left: 0;
    right: 0;
    margin:auto;

    overflow: hidden;
    transition: all .3s;

}
.icon {
    background-color: rgba(255, 255, 255, 1);
    background-position: center;
    background-clip: padding-box;

    border-radius: 50%;
    position: absolute;
    left: 0;
    right: 0;
    margin:auto;
    top: 50%;
    transform: translateY(-50%);

    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);

    overflow: hidden;
    transition: all .3s;
}
.icon-border .fa-home {
    width: 100%;
    height: 100%;

    color: rgb(111, 113, 115);
    text-align: center;

    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    transform: translateY(25%);
    transition: all .3s;
}
.icon-border .fa-home:hover {
    color: rgb(124, 179, 255);
}
</style>