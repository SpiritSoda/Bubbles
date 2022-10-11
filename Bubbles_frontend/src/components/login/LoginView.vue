<script>
import LoginPanel from './LoginPanel.vue'
export default {
    components: {
        LoginPanel
    },
    data() {
        return {
            
        }
    },
    props:['error_code']
}
</script>
<template>
    <div>
        <LoginPanel :error_code="error_code">

        </LoginPanel>
    </div>
</template>

<style scoped>

</style>