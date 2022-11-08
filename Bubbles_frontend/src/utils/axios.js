import axios from "axios";

const $axios = axios.create({
    baseURL: 'https://b2884t1064.oicp.vip',//'http://127.0.0.1:8081',
    timeout: 2000,
});

export default $axios;