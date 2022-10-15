<script>
import { Picker, EmojiIndex } from "emoji-mart-vue-fast/src";
import data from "emoji-mart-vue-fast/data/all.json";
import "emoji-mart-vue-fast/css/emoji-mart.css";
import $bus from "../../../../../utils/eventbus";
import Button from '../../../../utils/Button.vue'

export default {
    props: ['scroll_to_bottom'],
    inject: ['background_color', 'settings', 'shadow_color'],
    components: {
        Picker,
        Button
    },
    data() {
        return {
            emoji_index: new EmojiIndex(data),
            emojis_output: "",
            content: ''
        };
    },
    methods: {
        send_message() {
            $bus.emit('send', this.content)
            this.content = ''
            this.$nextTick(() => { this.scroll_to_bottom() })
        },
        append_emoji(e) {
            this.content += e.native
        },
        switch_setting(key) {
            $bus.emit('setting', key)
        },
        logout(obj) {
            console.log(obj)
        }
    },
    computed: {
        emoji_picker_style() {
            return {
                'width': '300px',
                'height': '245px'
            }
        },
    }
}
</script>

<template>
    <div class="message-bar clearfix">
        <ul class="toolbar">
            <li class="emoji">
                <div class="emoji-selector-wrapper">
                    <div class="emoji-selector" :style="{'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color}">
                        <Picker :data="emoji_index" :show-categories="true" :picker-styles="emoji_picker_style"
                            :show-preview="false" @select="append_emoji" :color="'rgb(124, 179, 255)'"></Picker>
                    </div>
                </div>
                <a href="javascript:;">
                    <i class="fas fa-grin"></i>
                </a>
            </li>
            <li class="image">
                <a href="javascript:;">
                    <i class="fas fa-image"></i>
                </a>
            </li>
            <li class="file">
                <a href="javascript:;">
                    <i class="fas fa-file"></i>
                </a>
            </li>
            <li class="history">
                <a href="javascript:;">
                    <i class="fas fa-history"></i>
                </a>
            </li>
            <li class="setting">
                <div class="setting-menu-wrapper">
                    <div class="setting-menu" :style="{'background-color': this.background_color, 'box-shadow': '0 0 5px ' + this.shadow_color}">
                        <div class="option" v-for="key in Object.keys(settings)">
                            <span>{{settings[key].name}}</span>
                            <div class="button-wrapper">
                                <Button :call_back="() => {switch_setting(key)}"
                                    :default="settings[key].value"></Button>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="javascript:;">
                    <i class="fas fa-wrench"></i>
                </a>
            </li>
        </ul>
        <form class="message-wrapper">
            <input type="text" class="message" v-model="content">
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

.toolbar a:hover {
    color: rgb(124, 179, 255);
    box-shadow: 0 0 7px rgb(124, 179, 255);
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
</style>