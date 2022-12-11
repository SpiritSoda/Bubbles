<script>
export default{
    props: {
        width: {
            type: Number,
            default: 80
        },
        height: {
            type: Number,
            default: 20
        },
        text: {
            type: String,
            default: '......'
        }
    },
    computed:{
        bar_style(){
            return {
                'width': this.width + 'px', 
                'height': this.height + 'px',
                'line-height': this.height + 'px',
                'border-radius': this.height / 2 + 'px'
            }
        }
    }
}
</script>

<template>
    <div class="display-bar" :style="this.bar_style">
        <span>{{this.text}}</span>
    </div>
</template>

<style scoped>
.display-bar{
    text-align: center;
    box-shadow: 0 0 5px rgba(0, 0, 0, .3);
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
}
</style>