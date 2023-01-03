<script>
import LeftBody from './body/left/LeftBody.vue'
import RightBody from './body/right/RightBody.vue'

export default {
    components: {
        LeftBody,
        RightBody
    },
    data() {
        return {

        }
    },
    methods: {
        process_self_message(res) {
            let message = JSON.parse(res.body)
            if (message.type == 3) {
                this.$store.commit('logout')
                this.$bus.emit('popup', 5)
                this.$bus.emit("switch_state", 0)
            }
            else if(message.type == 4){
                this.$store.commit("chatroom/reset_chatroom")
                this.$bus.emit('popup', 6)
            }
        }
    },
    mounted() {
        let token = localStorage.getItem("token");
        if (token != null) {
            this.$store.dispatch('update_localuser', {
                on_success: () => {
                    this.$socket.connect(token, () => {
                        console.log("Connected to Bubbles chat manager ...")
                        this.$store.dispatch('update_localuser', { on_success: () => { }, on_error: () => { } })
                        this.$socket.subscribe_self(this.$store.state.localuser.local_id, (res) => {
                            this.process_self_message(res)
                        })
                    })
                },
                on_error: () => {
                    localStorage.removeItem("token")
                    this.$bus.emit('error', 1000)
                    this.$bus.emit('switch_state', 0)
                }
            });
        }
        else {
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