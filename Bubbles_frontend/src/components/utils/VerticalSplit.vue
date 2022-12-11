<script>
export default{
    props: {
        length:{
            type: Number,
            default: 10
        },
        left: {
            type: Number,
            default: 0
        },
        color:{
            type: String,
            default: '#000'
        }
    },
    computed:{
        line_style(){
            let style = {'height': this.length + 'px'}
            let bg_color = 'rgba(0, 0, 0, 1)'
            if(this.color != undefined)
                bg_color = this.color
            if(this.left > 0)
                style['left'] = this.left + 'px'
            else
                style['right'] = -this.left + 'px'
            style['background'] = 'linear-gradient(rgba(255, 255, 255, 0.0) , ' + bg_color + ' 10%, '+ bg_color + '90%, rgba(255, 255, 255, 0.0)) !important' 
            return style 
        }
    }
}
</script>

<template>
    <div class="vertical-split" :style="line_style"></div>
</template>

<style scoped>
.vertical-split{
    width: 1.5px;
    position: absolute;
    top: 0;
    bottom: 0;
    margin: auto 0;
}
</style>