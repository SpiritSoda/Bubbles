<script>
import LeftBody from './body/left/LeftBody.vue'
import RightBody from './body/right/RightBody.vue'

export default{
    components:{
        LeftBody,
        RightBody
    },
    data(){
        return {
            local_id: 7,
            onlines:[
                {id: 1, icon: 1, username: 'Kazuha'},
                {id: 2, icon: 2, username: 'Ayaka'},
                {id: 3, icon: 3, username: 'Kaeya'},
                {id: 4, icon: 4, username: 'Ganyu'},
                {id: 5, icon: 5, username: 'Tartarlia'},
                {id: 6, icon: 6, username: 'Zhongli'},
                {id: 7, icon: 7, username: 'You Know Who'},
            ]
        }
    },
    methods:{
        isLocal(user){
            return user.id === this.local_id
        }
    },
    computed:{
        local(){
            return this.onlines.findIndex(this.isLocal)
        }
    }
}
</script>

<template>
    <div class="session-wrapper">
        <LeftBody :onlines="this.onlines" :local="this.local"></LeftBody>
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