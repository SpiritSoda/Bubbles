<script>
import HorizontalSplit from '../../../../../utils/HorizontalSplit.vue';
export default {
    props: ['message', 'color'],
    components:{
        HorizontalSplit
    },
    methods:{
        download(){
            this.$store.dispatch("download_file", {
                id: this.message.messageId,
                filename: this.filename
            })
            console.log("dowloading ...")
        }
    },
    computed:{
        type(){
            return this.message.type ? this.message.type : 0
        },
        text(){
            return this.type == 0 ? this.message.content : '-NOT TEXT MESSAGE-'
        },
        filename(){
            if(this.type != 1)
                return '-NOT FILE MESSAGE-';
            const content = this.message.content
            const dot = content.indexOf('.')
            const date = content.substr(0, dot)
            const filename = content.substr(dot + 1)
            return filename
        },
        is_local(){
            return this.message.senderId == this.$store.state.localuser.local_id
        }
    }
}
</script>

<template>
    <div class="bubble clearfix" :style="{'background-color': this.color}">
        <span class="text" v-if="this.type == 0">{{this.text}}</span>
        <a href="javascript:;" class="file" v-else-if="this.type == 1" :title="this.filename" @click="download">
            <div class="file-logo">
                <i class="fas fa-folder"></i>
            </div>
            <HorizontalSplit :length="90" :top="80" :color="'rgb(110, 123, 141, .6)'"></HorizontalSplit>
            <span class="filename" style="color:black">{{this.filename}}</span>
        </a>
    </div>
</template>

<style scoped>
.bubble{
    max-width: 300px;

    margin-top: 12px;
    padding: 10px;

    font-size: 16px;
    border-radius: 10px;
    box-shadow: 0 0 4px rgba(0, 0, 0, .4);

    word-wrap: break-word;
    word-break: break-all;
    overflow: hidden;
    float: left;
}

.text{
    max-width: 300px;
    word-break: keep-all;
}

.file{
    display: block;
    position: relative;
    width: 100px;
    height: 110px;
    border-radius: 10px;
    border: 2px solid rgb(110, 123, 141, .6);
    background-color: rgb(255, 255, 255, .8);

    text-align: center;
}
.file-logo{
    position: absolute;
    top: 10px;
    left: 0;
    right: 0;
    margin: auto;

    color: rgb(240, 193, 74);
    font-size: 60px;
}
.filename{
    width: 90px;
    position: absolute;
    bottom: 8px;
    left: 0;
    right: 0;
    margin: auto;
    
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>