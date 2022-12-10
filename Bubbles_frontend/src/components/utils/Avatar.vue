<script>
export default {
    props: ['avatar', 'r', 'shadow'],
    computed:{
        avatar_border_style(){
            let style = {
                'width': this.r + 'px', 
                'height': this.r + 'px',
            }
            if(this.shadow){
                style['box-shadow'] = '0 0px 10px rgba(0, 0, 0, .3)';
            }
            return style
        },
        avatar_style(){
            return {
                'width': (this.r - 5) + 'px', 
                'height': (this.r - 5) + 'px', 
                // 'background-image':'url(\'/avatars/' + this.avatar + '.webp\')',
                'background-image':'url(\'/avatars/genshin/' + this.avatar + '.jpg\')',
                'background-size': (this.r - 5) + 'px',
                'opacity': this.avatar > 0 ? 1.0 : 0.0
            }
        }
    }
}
</script>

<template>
    <div class="avatar-border" :style="this.avatar_border_style">
        <div class="avatar" :style="this.avatar_style"></div>
        <i class="fas fa-user" :style="{'font-size': (this.r / 2) + 'px'}" v-if="this.avatar <= 0"></i>
    </div>
</template>

<style scoped>
.avatar-border{
    background-color: rgba(255, 255, 255, 1);
    border-radius: 50%;
    position: absolute;
    left: 0;
    right: 0;
    margin:auto;

    overflow: hidden;
    transition: all .3s;

}
.avatar {
    background-color: rgba(255, 255, 255, 1);
    background-position: center;
    background-clip: padding-box;

    border-radius: 50%;
    position: absolute;
    left: 0;
    right: 0;
    margin:auto;
    top: 50%;
    transform: translateY(-50%);

    box-shadow: 0 0px 5px rgba(0, 0, 0, .3);

    overflow: hidden;
    transition: all .3s;
}
.avatar-border .fa-user {
    width: 100%;
    height: 100%;

    color: rgb(111, 113, 115);
    text-align: center;

    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    transform: translateY(25%);
    transition: all .3s;
}
</style>