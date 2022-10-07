<script>
    export default{
        data(){
            return {
            }
        },
        props:['show'],
        computed:{
            display_height(){
                if(!this || !this.show){
                    return 0 + 'px';
                }
                if(this.show === true)
                    return 400 + 'px';
                else
                    return 0 + 'px';
            },
            display_border(){
                if(!this || !this.show){
                    return 0 + 'px';
                }
                if(this.show === true)
                    return 5 + 'px';
                else
                    return 0 + 'px';
            }
        },
        methods: {
            reverse_show(){
                this.$emit("reverse_show")
            }
        },
    }
</script>

<template>
    <div class="avatar-list-wrapper" :style="{'height': display_height, 'border-width': display_border}">
        <div class="unshow" @click="reverse_show()">
            <i class="fas fa-chevron-up"></i>
        </div>
    </div>
</template>

<style>
    .avatar-list-wrapper{
        position: absolute;
        overflow: hidden;
        width: 400px;
        height: 0px;
        border: 5px solid rgb(124, 179, 255);
        border-radius: 40px;
        background-color: rgba(255, 255, 255, 1);

        box-sizing: border-box;

        top: 120px;
        left: 0px;
        right: 0;
        margin: auto;

        transition: all .3s;
        z-index: 1000;
    }
    .unshow{
        width: 40px;
        height: 20px;
        line-height: 20px;
        background-color: rgb(124, 179, 255);

        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;

        border-top-left-radius: 8px ;
        border-top-right-radius: 8px ;
        text-align: center;
        z-index: 999;
        transition: all .3s;
    }
    .unshow i{
        color: #fff;
        margin-top: 2px;
    }
    .unshow:hover{
        background-color: rgb(71, 147, 253);
    }
</style>