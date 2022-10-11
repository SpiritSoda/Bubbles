<script>
import LeftBody from './body/left/LeftBody.vue'
import RightBody from './body/right/RightBody.vue'

export default{
    components:{
        LeftBody,
        RightBody
    }
}
</script>

<template>
    <div class="session-wrapper">
        <LeftBody></LeftBody>
        <RightBody></RightBody>
    </div>
</template>

<style scoped>
.session-wrapper{
    width: 1010px;
    height: 545px;
    position: relative;
    margin: 80px auto;
    /* border-radius: 6px; */
    /* background-color: #f1f1f1; */

    animation: appear ease 1s;
}
</style>