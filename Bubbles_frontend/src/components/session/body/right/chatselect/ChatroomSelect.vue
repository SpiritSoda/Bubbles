<script>
import ChatroomList from './ChatroomList.vue';
import SearchBar from './SearchBar.vue';

export default {
    components: { SearchBar, ChatroomList }
}
</script>

<template>
    <div class="select-wrapper">
        <div class="search-bar clearfix">
            <SearchBar></SearchBar>
        </div>

        <div class="chatroom-list clearfix">
            <ChatroomList></ChatroomList>
        </div>
    </div>
</template>

<style scoped>
.select-wrapper{
    width: 103%;
    height: 100%;
    overflow: hidden;
}
</style>