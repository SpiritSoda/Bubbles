<script>
import ChatroomList from './ChatroomList.vue';
import SearchBar from './SearchBar.vue';

export default {
    components: { SearchBar, ChatroomList },
    methods: {
        create_chatroom(){
            this.$bus.emit('popup', 2)
        }
    }
}
</script>

<template>
    <div class="select-wrapper fade-in">
        <div class="search-bar clearfix">
            <SearchBar></SearchBar>
        </div>

        <div class="chatroom-list clearfix">
            <ChatroomList></ChatroomList>
        </div>

        <button class="add-btn" @click="create_chatroom">
            <a href="javascript:;" class="add-a">
                <i class="fas fa-plus"></i>
            </a>
        </button>
    </div>
</template>

<style scoped>
.select-wrapper{
    width: 103%;
    height: 100%;
    overflow: hidden;
}
.add-btn {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    border: none;
    text-align: center;
    line-height: 28px;
    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);
    background-color: #fff;

    position: absolute;
    right: 16px;
    bottom: 16px;

    overflow: hidden;
    transition: box-shadow .3s, background-color .3s;
}
.add-a {
    width: 40px;
    height: 40px;
    display: block;
    position: absolute;
    left: 0;
    top: 0;
    color: #424242;
    line-height: 40px;
    
    font-size: 16px;
}
.add-btn:hover {
    box-shadow: 0 0 10px rgb(124, 179, 255);
}
</style>