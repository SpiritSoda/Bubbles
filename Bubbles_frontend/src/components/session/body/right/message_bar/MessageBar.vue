<script>
export default{
    props:['scroll_to_bottom'],
    methods:{
        send_message(){
            this.scroll_to_bottom()
        }
    }
}
</script>

<template>
    <div class="message-bar clearfix">
        <ul class="toolbar">
            <li class="emoji">
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
                <a href="javascript:;">
                    <i class="fas fa-wrench"></i>
                </a>
            </li>
        </ul>
        <form action="#" class="message-wrapper">
            <input type="text" class="message">
            <button class="send-btn" @click="send_message">
                <a href="javascript:;" class="send-a">
                    <i class="fas fa-share"></i>
                </a>
            </button>
        </form>
    </div>
</template>

<style scoped>
.message-bar{
    width: 100%;
    height: 46px;
    border-radius: 23px;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;
}
.message{
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

    transition: box-shadow .3s,background-color .3s;
}
.send-btn{
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
    transition: box-shadow .3s,background-color .3s;
}
.send-a{
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

.send-btn:hover{
    box-shadow: 0 0 10px rgb(124, 179, 255);
}

.toolbar{
    position: absolute;
    bottom: 56px;
    padding: 0 6px;
}
.toolbar li{
    float: left;
    font-size: 18px;
    margin-right: 11px;
    position: relative;
}
.toolbar a{
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: block;
    transition: color .2s, box-shadow .2s;
    border-radius: 50%;
}
.toolbar a:hover{
    color: rgb(124, 179, 255);
    box-shadow: 0 0 7px rgb(124, 179, 255);
}
</style>