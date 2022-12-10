<script>
import LeftBody from './body/left/LeftBody.vue'
import RightBody from './body/right/RightBody.vue'
import { computed } from 'vue';

export default {
    components: {
        LeftBody,
        RightBody
    },
    data() {
        return {
            local_id: 0,
            onlines: [],
            userinfo: {
                "0": { id: 0, avatar: -1, username: 'Unknown'}
            },
            messages: [
                { id: 7, content: 'Avada Kedavra' },
                { id: 6, content: 'Stabilized!' },
                { id: 4, content: 'XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD' },
                { id: 5, content: '~' },
                { id: 2, content: 'Hehehehehe...' },
                { id: 7, content: '(╯‵□′)╯︵┻━┻' }
            ],
            selected_room: 0,
            chatrooms: {
                "1": { id: 1, icon: 1, title: "Default Chatroom", total_user: 233, comment: "Default Bubbles chatroom ...", onlines: 7},
                "2": { id: 2, icon: 2, title: "Extra Chatroom", total_user: 233, comment: "Extra Bubbles chatroom ...", onlines: 3}
            }
        }
    },
    provide() {
        return {
            onlines: computed(() => this.onlines),
            userinfo: computed(() => this.userinfo),
            messages: computed(() => this.messages),
            local_id: computed(() => this.local_id),
            selected_room: computed(() => this.selected_room),
            chatrooms: computed(() => this.chatrooms),
        }
    },
    mounted(){
        // this.$bus.on('send', (content) => { if (content === '') return; else this.messages.push({ id: this.local_id, content: content }) })
        // this.$bus.on('select_chatroom', (id) => { this.select_chatroom(id)} )
        // this.$bus.on('require_userinfo', (id) => { this.fetch_userinfo(id) })
        let token = localStorage.getItem("token");
        if(token != null){
            this.$axios.get('/api/user/selfinfo', { 
                    headers: { 
                        'token': token  
                    }           
                }).then(
                response => {
                    if(response.data.code != 0){
                        localStorage.removeItem("token")
                        this.$bus.emit('switch_state', 0)
                    }
                    else{
                        this.$store.state.localuser.local_id = response.data.data.id;
                        this.$store.state.userinfo.userinfo[this.local_id] = response.data.data
                    }
                }
            ).catch(
                e => {
                    this.$bus.emit('error', 1000);
                }
            )
        }
        else{
            this.$bus.emit('switch_state', 0)
        }
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
.session-wrapper {
    width: 1010px;
    height: 545px;
    position: absolute;
    left: 0;
    right: 0;
    top: 80px;
    margin: auto;
    /* margin: 80px auto; */
    /* border-radius: 6px; */
    /* background-color: #f1f1f1; */

    animation: appear-down ease 1s;
}
</style>