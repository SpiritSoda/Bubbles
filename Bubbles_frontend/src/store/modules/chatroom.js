import $axios from '../../utils/axios'
export default {
    namespaced: true, 
    state: () => {
        return {
            onlines: [],
            messages: [
                { id: 7, content: 'Now you shall perish !' },
                { id: 6, content: 'Stabilized!' },
                { id: 4, content: 'XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD' },
                { id: 5, content: '~' },
                { id: 2, content: 'Hehehehehe...' },
                { id: 7, content: '(╯‵□′)╯︵┻━┻' }
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
        }
    },
    actions: {
        
    }
}