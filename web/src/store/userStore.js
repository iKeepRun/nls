import {defineStore} from "pinia";

export const useUserStore = defineStore('user', {
    state: () =>(
        {
            name: '匿名用户',
            token: '',
        }
    ) ,
    actions:{
        setUserInfo(data){
            this.token=data.token
            this.name=data.name
        }
    }
})