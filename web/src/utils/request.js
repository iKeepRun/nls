// src/utils/request.js
import axios from 'axios';
import router from "../router";


axios.defaults.baseURL = import.meta.env.VITE_SERVER;
axios.defaults.timeout = 5000;
// axios.defaults.withCredentials = true;

// 请求拦截器
axios.interceptors.request.use(config => {
    console.log("request interceptor", config);
    return config;
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
axios.interceptors.response.use(response => {
    console.log("response interceptor", response);
    return response;
}, error => {
    if (error.response.status === 401) {
        localStorage.removeItem('token');
        router.push('/login');
    }
    return Promise.reject(error);
});

export default axios;
