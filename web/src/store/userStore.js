import {defineStore} from "pinia";

export const useUserStore = defineStore('user', {
    state: () => ({
        name: localStorage.getItem('userName') || '匿名用户',
        token: localStorage.getItem('token') || '',
    }),
    getters: {
        isLoggedIn: (state) => !!state.token,
    },
    actions: {
        setUserInfo(data) {
            this.token = data.token;
            this.name = data.name;
            // 持久化存储
            localStorage.setItem('token', data.token);
            localStorage.setItem('userName', data.name);
        },
        logout() {
            this.token = '';
            this.name = '匿名用户';
            // 清除持久化存储
            localStorage.removeItem('token');
            localStorage.removeItem('userName');
        }
    }
});
