<script>
import RegisterPannel from './RegisterPannel.vue';
export default {
    components: { RegisterPannel }
}
</script>

<template>
    <div>
        <div class="register-pannel-wrapper">
            <RegisterPannel></RegisterPannel>
        </div>
    </div>
</template>

<style scoped>
.register-pannel-wrapper{
    width: 1000px;
    height: 500px;
    /* background-color: #fff; */
    position: absolute;
    left: 0;
    right: 0;
    top: 100px;
    margin: auto;
    animation: appear-up ease 1s;
}

</style>