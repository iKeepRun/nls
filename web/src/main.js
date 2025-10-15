import {createApp} from 'vue'
import App from './App.vue'

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue';
import router from "./router";

const app = createApp(App);

app.use(Antd).use(router).mount('#app');

// 注册所有图标
const icons = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}


