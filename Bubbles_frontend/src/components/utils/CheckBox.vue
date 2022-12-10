<script>
export default {
    props: ['call_back', 'default'],
    data() {
        return {
            state: false
        }
    },
    mounted() {
        this.state = this.default ? true : false
    },
    computed: {
        dot_position() {
            if (this.state) {
                return {
                    'left': '20px'
                }
            }
            else {
                return {
                    'left': 0
                }
            }
        },
    },
    methods: {
        switch_state() {
            this.state = !this.state
            this.call_back()
        }
    }
}
</script>

<template>
    <div>
        <a href="javascript:;" class="button" @click="switch_state" :style="this.state ? {'background-color': 'rgb(124, 179, 255)'} : {}">
            <div class="button-dot" :style="dot_position">

            </div>
        </a>

    </div>

</template>

<style scoped>
.button {
    display: block;
    width: 36px;
    height: 16px;
    border-radius: 8px;
    box-shadow: 0 0 4px rgba(0, 0, 0, .3);
    position: relative;
    background-color: rgb(245, 245, 245);
    transition: all .3s;
}

.button-dot {
    width: 16px;
    height: 16px;
    border-radius: 8px;
    position: absolute;
    top: 0;
    bottom: 0;
    margin: auto;
    background-color: #fff;
    box-shadow: 0 0 2px rgba(0, 0, 0, .6);

    transition: all .3s;
}

.button:hover{
    background-color: rgb(226, 226, 226);
}
</style>