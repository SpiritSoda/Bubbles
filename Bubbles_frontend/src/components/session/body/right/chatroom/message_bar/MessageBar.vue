<script>
import { Picker, EmojiIndex } from "emoji-mart-vue-fast/src";
import data from "emoji-mart-vue-fast/data/all.json";
import "emoji-mart-vue-fast/css/emoji-mart.css";
import CheckBox from '../../../../../utils/CheckBox.vue'
import FileTransfer from "./toolbar/FileTransfer.vue";

export default {
    inject: ['background_color', 'settings', 'shadow_color'],
    components: {
    Picker,
    CheckBox,
    FileTransfer
},
    data() {
        return {
            emoji_index: new EmojiIndex(data),
            emojis_output: "",
            content: '',
            has_new_task: false,
            has_new_task_timer: 0
        };
    },
    methods: {
        send_message() {
            if(this.content.length == 0)
                return
            if(this.content.length > 60){
                this.$store.commit('error/set_error_code', 3003)
                return
            }
            this.$store.dispatch('send_message', this.content)
            this.content = ''
        },
        append_emoji(e) {
            this.content += e.native
        },
        switch_setting(key) {
            this.$bus.emit('setting', key)
        },
        logout(obj) {
            console.log(obj)
        },
        upload_file(event) {
            const file = event.target.files[0]
            if(!file)
                return
            const size = file.size
            // console.log(file, this.$store.state.global.max_file_size)
            if(size > this.$store.state.global.max_file_size){
                this.$store.commit("error/set_error_code", 3004)
                document.getElementById("file-select").value = ""
                return
            }
            const formData = new FormData();
            formData.append("file", file);
            formData.append("chatroom", this.$store.state.chatroom.selected_room);
            formData.append("type", 1)
            this.$store.dispatch("upload_file", 
                {
                    info: {
                        filename: file.name,
                        filesize: file.size
                    },
                    data: formData
                }
            )
        },
        upload_image(event) {
            const file = event.target.files[0]
            if(!file)
                return
            const size = file.size
            // console.log(file, this.$store.state.global.max_file_size)
            if(size > this.$store.state.global.max_image_size){
                this.$store.commit("error/set_error_code", 3007)
                document.getElementById("image-select").value = ""
                return
            }
            const formData = new FormData();
            formData.append("file", file);
            formData.append("chatroom", this.$store.state.chatroom.selected_room);
            formData.append("type", 2)
            this.$store.dispatch("upload_file", 
                {
                    info: {
                        filename: file.name,
                        filesize: file.size
                    },
                    data: formData
                }
            )
        }
    },
    computed: {
        emoji_picker_style() {
            return {
                'width': '300px',
                'height': '245px'
            }
        },
    },
    mounted(){
        this.$bus.on('file_task_create', () => {
            this.has_new_task = true;
            if(this.has_new_task_timer){
                clearTimeout(this.has_new_task_timer)
            }
            this.has_new_task_timer = setTimeout(() => {this.has_new_task = false}, 1000)
        })
    }
}
</script>

<template>
    <div class="message-bar clearfix">
        <ul class="toolbar">
            <li class="emoji">
                <div class="emoji-selector-wrapper">
                    <div class="emoji-selector"
                        :style="{ 'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color }">
                        <Picker :data="emoji_index" :show-categories="true" :picker-styles="emoji_picker_style"
                            :show-preview="false" :show-search="false" @select="append_emoji"
                            :color="'rgb(124, 179, 255)'"></Picker>
                    </div>
                </div>
                <a href="javascript:;">
                    <i class="fas fa-grin"></i>
                </a>
            </li>
            <li class="image">
                <a href="javascript:;">
                    <input id="image-select" class="file-input" type="file" @change="upload_image" title="Send Image" accept=".png,.jpg">
                    <i class="fas fa-image"></i>
                </a>
            </li>
            <li class="file">
                <a href="javascript:;">
                    <input id="file-select" class="file-input" type="file" @change="upload_file" title="Send File">
                    <i class="fas fa-folder-open"></i>
                </a>
            </li>
            <li class="history">
                <div class="file-transfer-wrapper">
                    <FileTransfer></FileTransfer>
                </div>
                <a href="javascript:;" :class="{has_new_task: has_new_task}">
                    <i class="fas" :class="has_new_task ? 'fa-exclamation': 'fa-history'"></i>
                </a>
            </li>
            <li class="setting">
                <div class="setting-menu-wrapper">
                    <div class="setting-menu"
                        :style="{ 'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color }">
                        <div class="option" v-for="key in Object.keys(settings)">
                            <span>{{ settings[key].name }}</span>
                            <div class="button-wrapper">
                                <CheckBox :call_back="() => { switch_setting(key) }" :default="settings[key].value">
                                </CheckBox>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="javascript:;">
                    <i class="fas fa-wrench"></i>
                </a>
            </li>
        </ul>
        <form onsubmit="return false" class="message-wrapper">
            <input type="text" class="message" v-model="content" autocomplete="off">
            <button class="send-btn" @click="send_message">
                <a href="javascript:;" class="send-a">
                    <i class="fas fa-share"></i>
                </a>
            </button>
        </form>
    </div>
</template>

<style scoped>
.message-bar {
    width: 100%;
    height: 46px;
    border-radius: 23px;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;
}

.message {
    width: 100%;
    height: 46px;
    line-height: 46px;

    border-radius: 23px;

    padding-left: 20px;
    padding-right: 50px;
    box-sizing: border-box;

    font-size: 18px;
    outline: none;
    border: none;

    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    background-color: rgb(246, 246, 246);
    box-shadow: 0 0px 2px rgba(0, 0, 0, .3);

    transition: box-shadow .3s, background-color .3s;
}

.send-btn {
    width: 46px;
    height: 46px;
    border-radius: 23px;
    position: absolute;
    right: 0px;
    border: none;
    text-align: center;
    line-height: 46px;
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: #fff;

    overflow: hidden;
    transition: box-shadow .3s, background-color .3s;
}

.send-a {
    width: 46px;
    height: 46px;
    display: block;
    position: absolute;
    left: 0;
    top: 0;
    color: #424242;
}

.message:focus {
    box-shadow: 0 0 7px rgb(124, 179, 255);
    background-color: #fff;
}

.message:hover:not(.message:focus) {
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: #fff;
}

.send-btn:hover {
    box-shadow: 0 0 10px rgb(124, 179, 255);
}

.toolbar {
    position: absolute;
    bottom: 56px;
    padding: 0 6px;
}

.toolbar li {
    width: 30px;
    height: 30px;
    float: left;
    font-size: 18px;
    margin-right: 11px;
    position: relative;
}

.toolbar a {
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: block;
    transition: color .2s, box-shadow .2s;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    border-radius: 50%;
}
.toolbar a i{
    transition: all .2s;
}
.toolbar a:hover {
    color: rgb(124, 179, 255);
    box-shadow: 0 0 7px rgb(124, 179, 255);
}
.has_new_task{
    color: green;
    box-shadow: 0 0 7px green;
}

.emoji:hover .emoji-selector-wrapper {
    height: 285px;
    top: -285px;
}

.emoji-selector-wrapper {
    width: 330px;
    height: 0px;
    background-color: transparent;

    position: absolute;
    left: -20px;
    right: 0;
    /* top: -215px; */
    top: 0;
    overflow: hidden;

    transition: all .3s;
}

.emoji-selector {
    width: 300px;
    height: 250px;
    background-color: #fff;
    padding: 10px;

    font-size: 10px;
    overflow: auto;
    font: '';

    border-radius: 12px;
    box-shadow: 0 0 5px rgba(0, 0, 0, .3);

    position: absolute;
    top: 5px;
    left: 5px;
    right: 5px;
    position: absolute;
    z-index: 10000;

    transition: all .3s;
}



.setting:hover .setting-menu-wrapper {
    height: 230px;
    top: -230px;
}

.setting-menu-wrapper {
    width: 200px;
    height: 0px;
    background-color: transparent;

    position: absolute;
    left: -80px;
    right: 0;
    top: 0;
    overflow: hidden;

    transition: all .3s;
}

.setting-menu {
    width: 160px;
    height: 200px;
    background-color: #fff;
    padding: 10px;
    padding-top: 6px;

    font-size: 10px;
    overflow: auto;

    border-radius: 12px;
    box-shadow: 0 0 5px rgba(0, 0, 0, .3);

    position: absolute;
    top: 5px;
    left: 5px;
    right: 5px;
    margin: auto;
    z-index: 10000;

    transition: all .3s;
}

.option {
    height: 20px;
    line-height: 20px;
    font-size: 14px;
    margin-top: 5px;
    position: relative;
}

.button-wrapper {
    height: 16px;
    position: absolute;
    top: 0;
    bottom: 0;
    right: 5px;
    margin: auto;
}
.file-input{
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: inline;
    transition: color .2s, box-shadow .2s;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    border-radius: 50%;
    opacity: 0;
}
.history:hover .file-transfer-wrapper {
    height: 230px;
    top: -230px;
}

.file-transfer-wrapper {
    width: 300px;
    height: 0px;
    background-color: transparent;

    position: absolute;
    left: -150px;
    right: 0;
    top: 0;
    overflow: hidden;

    transition: all .3s;
}
</style>