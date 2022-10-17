<script>
export default{
    props: ['length', 'top', 'color'],
    computed:{
        line_style(){
            let style = {'width': this.length + 'px'}
            let bg_color = 'rgba(0, 0, 0, 1)'
            if(this.color != undefined)
                bg_color = this.color
            if(this.top > 0)
                style['top'] = this.top + 'px'
            else
                style['bottom'] = -this.top + 'px'
            style['background'] = 'linear-gradient(to left,rgba(255, 255, 255, 0.0) , ' + bg_color + ' 10%, '+ bg_color + '90%, rgba(255, 255, 255, 0.0))' 
            return style 
        }
    }
}
</script>

<template>
    <div class="horizontal-split" :style="line_style"></div>
</template>

<style scoped>
.horizontal-split{
    height: 1.5px;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
}
</style>