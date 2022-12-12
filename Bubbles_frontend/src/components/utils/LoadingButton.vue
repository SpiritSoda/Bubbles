<script>
export default {
    props: {
        r:{
            type: Number,
            default: 10
        },
        fa_icon: {
            type: String,
            default: 'fa-edit'
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
            default: null
        },
        signal: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        btn_size(){
            return {
                'width': this.r + 'px',
                'height': this.r + 'px',
                'line-height': this.r + 'px',
                'text-align': 'center',
                'border-radius': '50%',
                'transition': 'all .2s',
                'font-size': 0.36 * this.r + 'px',
            }
        }
    }
}
</script>

<template>
    <div class="btn" :style="this.btn_size">
        <a href="javascript:;" :style="this.btn_size" @click="click()" :title="title">
            <i :class="'fas ' + signal ? 'fa-undo rotate': fa_icon"></i>
        </a>
    </div>
</template>

<style scoped>
.btn a{
    display: block;
    box-shadow: 0 0 4px rgba(0, 0, 0, .3);
    background-color: #fff;
}
.btn a:hover {
    color: rgb(124, 179, 255) !important;
    box-shadow: 0 0 7px rgb(124, 179, 255) !important;
}

</style>