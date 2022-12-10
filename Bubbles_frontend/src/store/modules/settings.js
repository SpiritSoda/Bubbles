export default {
    namespaced: true, // 为每个模块添加一个前缀名，保证模块命明不冲突 
    state: () => {
        return {
            settings: {
                dark_mode: { name: 'Dark Mode', value: false },
                hide_username: { name: 'Hide timestamp', value: false }
            }
        }
    },
    mutations: {
        setting(state, option) {
            state.settings[option].value = !state.settings[option].value
        }
    },
    actions: {},
    getters: {
        color_style(state) {
            if (state.settings.dark_mode.value) {
                return {
                    content_background_color: '#222222',
                    content_font_color: '#e2e2e2',
                    content_shadow_color: 'rgba(255, 255, 255, .6)'
                }
            }
            else {
                return {
                    content_background_color: '#fff',
                    content_font_color: '#000',
                    content_shadow_color: 'rgba(0, 0, 0, .3)'
                }
            }
        }
    }
}