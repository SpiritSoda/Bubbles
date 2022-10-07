<template>
    <div class="user-info-wrapper">
        <form action="#" class="user-info">
            <button class="login-btn">
                <a href="javascript:;">
                    <i class="fas fa-arrow-right"></i>
                </a>
            </button>
            <input type="text" class="username">
        </form>

    </div>
</template>

<style>
.user-info-wrapper {
    height: 40px;
    line-height: 40px;
    margin: 20px auto;
    text-align: center;

    position: absolute;
    top: 295px;
    left: 0;
    right: 0;
    margin: auto;
}
.username {
    width: 200px;
    height: 32px;
    line-height: 32px;
    border: 3px solid rgb(124, 179, 255, .3);
    border-radius: 16px;
    background-color: rgba(255, 255, 255, .9);
    overflow: hidden;
    outline: none;
    padding: 0px 10px;

    font: 200 normal 14px/1.6 Avant Garde, Avantgarde, Century Gothic, CenturyGothic, AppleGothic, sans-serif;
    text-align: center;

    position: absolute;
    top: 50%;
    transform: translate(0, -50%);
    left: 0;
    right: 0;
    margin: auto;
    transition: all .3s;
}

.username:focus {
    border-color: rgb(124, 179, 255);
    background-color: rgba(255, 255, 255, 1);
}

.login-btn {
    width: 38px;
    height: 38px;
    border-radius: 50%;
    border: 2px solid transparent;
    text-align: center;
    background-color: rgba(255, 255, 255, .9);

    position: absolute;
    top: 50%;
    transform: translate(0, -50%);
    left: 0;
    right: 0;
    margin: auto;

    left: 280px;

    box-shadow: 0 0 5px rgb(124, 179, 255, .3);
    outline: none;

    transition: all .3s;
    ;
}

.login-btn i {
    color: #424242;
    text-align: center;
    transition: all .3s;
    ;
}

.login-btn:hover {
    background-color: rgba(255, 255, 255, 1);
    border-color: rgb(124, 179, 255);
}

.login-btn:hover i {
    color: rgb(124, 179, 255);
}

.login-btn:hover~.username {
    height: 0px;
    border: none;
}
</style>