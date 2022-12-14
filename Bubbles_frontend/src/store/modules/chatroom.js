import $axios from '../../utils/axios'
import $bus from '../../utils/eventbus'
export default {
    namespaced: true, 
    state: () => {
        return {
            onlines: [],
            messages: [
                // { senderId: 7, content: 'Now you shall perish !' },
                // { senderId: 6, content: 'Stabilized!' },
                // { senderId: 4, content: 'XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD' },
                // { senderId: 5, content: '~' },
                // { senderId: 2, content: 'Hehehehehe...' },
                // { senderId: 7, content: '(╯‵□′)╯︵┻━┻' }
            ],
            selected_room: 0,
        }
    },
    mutations: {
        reset_chatroom(state) {
            state.total_user = 0
            state.selected_room = 0
            state.messages = []
            state.onlines = []
        },
        save_message(state, msg){
            state.messages.push(msg)
        },
        save_messages_to_front(state, msgs){
            msgs.sort((msg) => msg.senderId)
            let msg = {}
            for(msg of msgs)
                state.messages.unshift(msg)
        },
        save_messages_to_back(state, msgs){
            let msg = {}
            for(msg of msgs)
                state.messages.push(msg)
        }
    },
    actions: {
    }
}