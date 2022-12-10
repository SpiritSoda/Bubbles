import $axios from '../../utils/axios'
export default{
    namespaced: true, // 为每个模块添加一个前缀名，保证模块命明不冲突 
    state: () => {
        return {
            userinfo: {
                "0": { id: 0, avatar: -1, username: 'Unknown'}
            }
        }
    },
    mutations: {
        save_user(state, info){
            state.userinfo[info.id] = info
        }
    },
    actions: {
        fetch_userinfo(context, id){
            let token = localStorage.getItem("token");
            $axios.get('/api/user/userinfo',
            {
                params: {
                    'id': id
                },
                headers: {
                    'token': token
                }
            }).then(
                response => {
                    if(response.data.code != 0){
                        context.commit('save_user', {id: id, avatar: -1, username: "Unknown"})
                    }
                    else{
                        context.commit('save_user', response.data.data)
                    }
                }
            )
        }
    }
}