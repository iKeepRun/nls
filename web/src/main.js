import {createApp} from 'vue'
import App from './App.vue'
import './style.css'

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue';
import router from "./router";
import axios from "axios";

const app = createApp(App);

app.use(Antd).use(router).mount('#app');

// 注册所有图标
const icons = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}


axios.interceptors.request.use(config => {
    // config.headers['Authorization'] = localStorage.getItem('token');
    console.log("requrst inteceptor",config);
    return config;
}, error => {
    return Promise.reject(error);
})

axios.interceptors.response.use(response => {
    console.log("response inteceptor",response);
    return response;
}, error => {
    if (error.response.status === 401) {
        localStorage.removeItem('token');
        router.push('/login');
    }
    return Promise.reject(error);
});


// console.log("默认服务地址：", import.meta.env.VITE_SERVER);

axios.defaults.baseURL = import.meta.env.VITE_SERVER;

