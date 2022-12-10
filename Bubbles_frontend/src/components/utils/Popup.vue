<script>
export default {
    data(){
        return{
            /*
             * 0: hidden
             * 1: fading
             * 2: display 
             */
            state: 0
        }
    },
    methods:{
        hide(){
            if(this.state == 0){
                this.show()
            }
            else if(this.state == 2){
                this.state = 1
                setTimeout(() => {this.state = 0}, 300)
            }
        },
        show(){
            this.state = 1
                setTimeout(() => {this.state = 2}, 2)
        }
    },
    computed:{
        popup_style(){
            return {
                'display': this.state > 0 ? 'block' : 'none',
                'opacity': this.state == 2 ? 1.0 : 0.0
            }
        }
    }
}
</script>

<template>
    <div class="popup-wrapper" :style="popup_style" @click="hide">
        <slot/>
    </div>
</template>

<style scoped>
.popup-wrapper{
    width: 100%;
    height: 100%;
    background-color: rgba(35, 35, 35, 0.6);
    position: absolute;

    top: 0;

    z-index: 999999;
    transition: opacity .3s;
}
</style>