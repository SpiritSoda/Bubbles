import $axios from '../../utils/axios'
export default {
    namespaced: true, // 为每个模块添加一个前缀名，保证模块命明不冲突 
    state: () => {
        return {
            local_id: 0,
            token: localStorage.getItem("token"),
            chatrooms: {
                
            },
            has_new_message: {

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
        },
        clear_new_message(state){
            state.has_new_message = {}
        },
        has_new_message(state, id){
            if(state.has_new_message[id] == undefined)
                state.has_new_message[id] = 0
            state.has_new_message[id] ++
        }
    },
    actions: {
    }
}