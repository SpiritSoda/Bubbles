export default{
    namespaced: true,
    state: () => {
        return {
            // transactions that need to be done
            txs:{
                // update login pannel after register success
                register_update: {
                  required: false,
                  data: null
                }

            }
        }
    },
    getters: {
      
    },
    mutations: {
        create_tx(state, payload){
            if(!state.txs[payload.name])
                return
            state.txs[payload.name].required = true
            state.txs[payload.name].data = payload.data
        },
        finish_tx(state, name){
            state.txs[name].required = false
            state.txs[name].data = null
        }
    },
    actions: {
      
    }
  }