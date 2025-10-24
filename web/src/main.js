import {createApp} from 'vue'
import App from './App.vue'
import './style.css'

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue';
import router from "./router";
import "./utils/request";
import { createPinia } from 'pinia'



const pinia = createPinia()

const app = createApp(App);

app.use(Antd).use(router).use(pinia).mount('#app');

// 注册所有图标
const icons = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}







