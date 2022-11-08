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
            local_id: 7,
            onlines: [1, 2, 3, 4, 5, 6, 7],
            userinfo: {
                "1": { id: 1, icon: 1, username: 'Kazuha' },
                "2": { id: 2, icon: 2, username: 'Ayaka' },
                "3": { id: 3, icon: 3, username: 'Kaeya' },
                "4": { id: 4, icon: 4, username: 'Ganyu' },
                "5": { id: 5, icon: 5, username: 'Tartarlia' },
                "6": { id: 6, icon: 6, username: 'Zhongli' },
                "7": { id: 7, icon: 7, username: 'You Know Who' },
            },
            messages: [
                { id: 7, content: 'Avada Kedavra' },
                { id: 6, content: 'Stabilized!' },
                { id: 4, content: 'XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD' },
                { id: 5, content: '~' },
                { id: 2, content: 'Hehehehehe...' },
                { id: 7, content: '(╯‵□′)╯︵┻━┻' }
            ],
            selected_room: 1,
            chatrooms: {
                "1": { id: 1, icon: 1, title: "Default Chatroom", max_online: 20, comment: "Default Bubbles chatroom ...", onlines: 7},
                "2": { id: 2, icon: 2, title: "Extra Chatroom", max_online: 20, comment: "Extra Bubbles chatroom ...", onlines: 3}
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
    position: relative;
    margin: 80px auto;
    /* border-radius: 6px; */
    /* background-color: #f1f1f1; */

    animation: appear-down ease 1s;
}
</style>