import $bus from '../../utils/eventbus'
export default {
    namespaced: true,
    state: () => {
        return {
            tasks: {},
            upload_id: -1
        }
    },
    mutations: {
        append_task(state, task_info) {
            if (state.tasks[task_info.id])
                return
            let task = {
                filename: task_info.filename,
                type: task_info.type,
                filesize: task_info.filesize,
                id: task_info.id,
                progress: 0
            }
            state.tasks[task_info.id] = task
            $bus.emit('file_task_create', {})
            // console.log("after append" + task_info.id, state.tasks)
        },
        finish_task(state, payload) {
            delete state.tasks[payload.id]
            // console.log("after finish" + payload.id, state.tasks)
        },
        update_progress(state, payload){
            state.tasks[payload.id].progress = payload.progress
        }
    },
    actions: {

    }
}