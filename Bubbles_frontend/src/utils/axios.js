import axios from "axios";

const baseURL = 'https://b2884t1064.oicp.vip'
const timeout = 100000

function post(url, data, config){
    return axios.create({
        baseURL,
        timeout
    }).post(url, data, config)
}
function get(url, config){
    return axios.create({
        baseURL,
        timeout
    }).get(url, config)
}
function upload_with_progress(url, data, config, on_progress){
    return axios.create({
        baseURL,
        timeout,
        onUploadProgress: progressEvent => {
            on_progress(progressEvent)
          }
    }).post(url, data, config)
}
function download_with_progress(url, config, on_progress){
    config.responseType = 'blob'
    return axios.create({
        baseURL,
        timeout,
        onDownloadProgress: function (progressEvent){
            on_progress(progressEvent)
        }
    }).get(url, config)
}
const $axios = {
    baseURL,
    post,
    get,
    upload_with_progress,
    download_with_progress
}

export default $axios;