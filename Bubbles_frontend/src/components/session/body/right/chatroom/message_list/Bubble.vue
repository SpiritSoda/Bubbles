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
                info: {
                    filename: this.filename,
                    filesize: this.filesize
                }
                
            })
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
            let dot = content.indexOf('.')
            const size_filename = content.substr(dot + 1)
            dot = size_filename.indexOf('.')
            const filename = size_filename.substr(dot + 1)
            return filename
        },
        filesize(){
            if(this.type != 1)
                return 0;
            const content = this.message.content
            let dot = content.indexOf('.')
            const size_filename = content.substr(dot + 1)
            dot = size_filename.indexOf('.')
            const size = size_filename.substr(0, dot)
            if(size > 1024 * 1024)
                return (size / (1024 * 1024)).toFixed(2) + 'MB'
            else if(size > 1024)
                return (size / 1024).toFixed(2) + 'KB'
            else
                return size + 'B'

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
            <div class="fileinfo" style="color:black">  
                <div class="filename">{{this.filename}}</div>
                <HorizontalSplit :length="150" :top="24" :color="'rgba(110, 123, 141, .6)'"></HorizontalSplit>
                <div class="filesize">{{this.filesize}}</div>
            </div>
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
    width: 260px;
    height: 80px;
    border-radius: 10px;
    border: 2px solid rgb(110, 123, 141, .6);
    background-color: rgb(255, 255, 255, .8);

}
.file-logo{
    position: absolute;
    top: 10px;
    left: 15px;

    color: rgb(240, 193, 74);
    font-size: 56px;
    text-align: center;
}
.fileinfo{
    width: 150px;
    height: 45px;
    position: absolute;
    top: 20px;
    left: 90px;
    text-align: center;
    
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.filename{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.filesize{
    position: absolute;
    bottom: 0px;
    left: 0;
    right: 0;
    margin: auto;
    font-size: 12px;
    text-align: center;
}
</style>