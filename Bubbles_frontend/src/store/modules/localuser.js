import $axios from '../../utils/axios'
export default {
    namespaced: true, // 为每个模块添加一个前缀名，保证模块命明不冲突 
    state: () => {
        return {
            local_id: 0,
            token: localStorage.getItem("token"),
            chatrooms: {
                
            }
        }
    },
    mutations: {
        save_localuser(state, data){
            state.local_id = data.id;
            state.chatrooms = {}
            let i = 0
            for (i in data.chatrooms){
                let room = data.chatrooms[i]
                state.chatrooms[room.id] = room
            }
        },
        save_token(state, token){
            state.token = token
        },
        reset_localuser(state){
            localStorage.removeItem("token")
            state.token = ''
            state.local_id = 0
            state.chatrooms = []
        }
    },
    actions: {
    }
}