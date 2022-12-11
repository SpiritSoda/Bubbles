<script>
export default {
    props: {
        width:{
            type: Number,
            default: 20
        },
        height: {
            type: Number,
            default: 10
        },
        click: {
            type: Function,
            default: () => {}
        },
        r_shadow: {
            type: Number,
            default: 4
        },
        title: {
            type: String,
            default: 'Click'
        },
        font_color: {
            type: String,
            default: 'rgba(59, 59, 59, 0.7)'
        },
        color:{
            type: String,
            default: '#fff'
        },
    },
    computed: {
        btn_style(){
            return {
                'width': this.width + 'px',
                'height': this.height + 'px',
                'line-height': this.height + 'px',
                'border-radius': Math.min(this.width, this.height) * 0.5 + 'px',
                'box-shadow': '0 0 ' + this.r_shadow + 'px ' + this.font_color,
                'background-color': this.color,
                'transition': 'all .2s',
            }
        },
        a_style(){
            return {
                'display': 'block',
                'width': this.width + 'px',
                'height': this.height + 'px',
                'line-height': this.height + 'px',
                'font-size': 0.5 * Math.min(this.width, this.height) + 'px !important',
                'text-align': 'center',
                'color': this.font_color,
                'transition': 'all .2s',
            }
        }
    }
}
</script>

<template>
    <div class="text-btn" :style="btn_style">
        <a href="javascript:;" class="text-btn-a" :style="a_style" @click="click">
            {{this.title}}
        </a>
    </div>
</template>

<style scoped>
.text-btn:hover{
    box-shadow: 0 0 6px rgba(71, 147, 253, .8) !important;
}
.text-btn a{
    font-weight: bold;
}
.text-btn a:hover{
    color: rgba(71, 147, 253, .8) !important;
}
</style>