<script>
export default {
    data(){
        return {
            chatroom_no: ""
        }
    }
}
</script>

<template>
    <div>
        <form onsubmit="return false" class="search-wrapper">
            <input type="number" class="search" v-model="chatroom_no" :placeholder="'Search Chatroom by number ...'">
            <button class="search-btn" @click="send_message">
                <a href="javascript:;" class="search-a">
                    <i class="fas fa-search"></i>
                </a>
            </button>
        </form>
    </div>
</template>

<style scoped>      
.search-wrapper{
    height: 70px;
}
.search {
    width: 90%;
    height: 28px;
    line-height: 28px;

    border-radius: 14px;

    padding-left: 18px;
    padding-right: 50px;
    box-sizing: border-box;

    font-size: 12px;
    outline: none;
    border: none;
    
    position: absolute;
    top: 24px;
    left: 12px;

    background-color: rgb(246, 246, 246);
    box-shadow: 0 0px 2px rgba(0, 0, 0, .3);

    transition: box-shadow .3s, background-color .3s;
}

.search-btn {
    width: 28px;
    height: 28px;
    border-radius: 14px;
    border: none;
    text-align: center;
    line-height: 28px;
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: #fff;

    position: absolute;
    top: 24px;
    right: 20px;

    overflow: hidden;
    transition: box-shadow .3s, background-color .3s;
}

.search-a {
    width: 28px;
    height: 28px;
    display: block;
    position: absolute;
    left: 0;
    top: 0;
    color: #424242;
    
    font-size: 12px;
}

.search:focus {
    box-shadow: 0 0 7px rgb(124, 179, 255);
    background-color: #fff;
}

.search:hover:not(.message:focus) {
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: #fff;
}

.search-btn:hover {
    box-shadow: 0 0 10px rgb(124, 179, 255);
}
</style>