<script>
export default {
    props: {
        r:{
            type: Number,
            default: 10
        },
        fa_icon: {
            type: String,
            default: 'fa_edit'
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
                'background-color': this.state ? 'rgb(228, 45, 45)' : 'white',
                'color': this.state ? 'white' : '#7e8c8d'
            }
        },
        icon(){
            return !this.state ? this.fa_icon : 'fa-check'
        }
    },
    data(){
        return {
            state: false,
            timer: 0
        }
    },
    methods: {
        on_click(){
            if(!this.state){
                this.state = true;
                this.timer = setTimeout(() => {this.state = false}, 2000)
            }
            else{
                clearTimeout(this.timer)
                this.state = false;
                this.click()
            }
        }
    }
}
</script>

<template>
    <div class="btn" :style="this.btn_size">
        <a href="javascript:;" :style="this.btn_size" @click="on_click()" :title="title">
            <i :class="'fas ' + icon"></i>
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
    color: rgb(124, 179, 255);
    box-shadow: 0 0 7px rgb(124, 179, 255) !important;
}

</style>