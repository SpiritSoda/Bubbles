<script>
export default {
    props: ['task']
}
</script>

<template>
    <div class="task-item">
        <div class="task-icon">
            <i class="fas" :class="task.type == 'download' ? 'fa-download' : 'fa-upload'"></i>
        </div>
        <div class="task-name">
            {{ task.filename }}
        </div>
        <progress class="task-progress-bar" :value="task.progress"></progress>
    </div>
</template>

<style scoped>
.task-item{
    width: 260px;
    height: 40px;
}
.task-icon{
    width: 36px;
    height: 36px;
    line-height: 36px;
    border-radius: 50%;
    box-shadow: 0 0 6px rgba(0, 0, 0, .3);

    position: absolute;
    top: 2px;
    left: 5px;

    text-align: center;
}
.task-name{
    width: 140px;
    font-size: 12px;

    position: absolute;
    top: 5px;
    left: 60px;

    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.task-progress-bar{
    width: 180px;
    height: 20px;

    position: absolute;
    top: 20px;
    left: 60px;
}
</style>