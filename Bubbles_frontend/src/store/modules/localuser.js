export default{
    namespaced: true, // 为每个模块添加一个前缀名，保证模块命明不冲突 
    state: () => {
        return {
            local_id: 0,
            token: localStorage.getItem("token"),
            chatrooms: {
                "1": { id: 1, icon: 1, title: "Default Chatroom", total_user: 233, comment: "Default Bubbles chatroom ...", onlines: 7},
                "2": { id: 2, icon: 3, title: "Extra Chatroom", total_user: 233, comment: "Extra Bubbles chatroom ...", onlines: 3}
            }
        }
    },
    mutations: {},
    actions: {}
}