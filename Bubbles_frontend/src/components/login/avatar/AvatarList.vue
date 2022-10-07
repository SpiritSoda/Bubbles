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
                    return 300 + 'px';
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
        }
    }
</script>

<template>
    <div class="avatar-list-wrapper" :style="{'height': display_height, 'border-width': display_border}">
        
    </div>
</template>

<style>
    .avatar-list-wrapper{
        position: absolute;
        overflow: auto;
        width: 240px;
        height: 0px;
        border: 5px solid rgb(124, 179, 255);
        border-radius: 40px;
        background-color: rgba(255, 255, 255, 1);

        box-sizing: border-box;

        top: 50px;
        left: 500px;
        margin: auto;

        transition: all .3s;
    }
</style>