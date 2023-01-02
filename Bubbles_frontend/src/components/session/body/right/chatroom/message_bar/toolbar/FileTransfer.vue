<script>
import HorizontalSplit from '../../../../../../utils/HorizontalSplit.vue';
import FileTransferTask from './FileTransferTask.vue';

export default {
    components: { FileTransferTask, HorizontalSplit }
}
</script>

<template>
    <div class="file-transfer">
        <div class="task-wrapper" v-if="Object.keys(this.$store.state.file.tasks).length > 0" v-for="task in this.$store.state.file.tasks">
            <FileTransferTask :task="task"></FileTransferTask>
            <HorizontalSplit :length="260" :top="-4" :color="'rgba(110, 123, 141, .2)'"></HorizontalSplit>
        </div>
        <div class="no-task" v-else>
            <i class="fas fa-folder-plus"></i>
            <span>{{ 'No upload or download task ...' }}</span>
        </div>
    </div>
</template>

<style scoped>
.file-transfer {
    width: 260px;
    height: 195px;
    background-color: #fff;
    padding: 10px;
    padding-top: 10px;

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
.task-wrapper{
    width: 260px;
    height: 52px;
    position: relative;
}
.no-task{
    text-align: center;
    font-size: 16px;
    line-height: 195px;
}
.no-task i{
    display: block;
    position: absolute;
    left: 0;
    right: 0;
    top: 50px;
    margin: auto;
    color: gray;
    font-size: 40px;
}
</style>