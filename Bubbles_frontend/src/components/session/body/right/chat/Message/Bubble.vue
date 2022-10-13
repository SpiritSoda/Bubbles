<script>
export default {
    props: ['content', 'color']
}
</script>

<template>
    <div class="bubble clearfix" :style="{'background-color': this.color}">
        <span class="content">{{this.content}}</span>
    </div>
</template>

<style scoped>
.bubble{
    max-width: 300px;

    margin-top: 12px;
    padding: 10px;

    font-size: 16px;
    border-radius: 10px;
    box-shadow: 0 0 4px rgba(0, 0, 0, .4);

    word-wrap: break-word;
    word-break: break-all;
    overflow: hidden;
    float: left;
}

.content{
    max-width: 300px;
    word-break: keep-all;
}
</style>