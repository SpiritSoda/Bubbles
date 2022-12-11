import $axios from '../../utils/axios'
export default{
    namespaced: true, // 为每个模块添加一个前缀名，保证模块命明不冲突 
    state: () => {
        return {
            onlines: [1, 2, 6, 4, 5, 10],
            total_user: 20,
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
        select_chatroom(state, id){
            state.selected_room = id;
        },
        reset_chatroom(state){
            state.total_user = 0
            state.selected_room = 0
            state.messages = []
            state.onlines = []
        }
    },
    actions: {}
}