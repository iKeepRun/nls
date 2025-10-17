import {createRouter, createWebHistory} from 'vue-router'
import Home from "../view/Home.vue"
import Login from "../view/Login.vue";
import Register from "../view/Register.vue";
import Reset from "../view/Reset.vue";

const routes = [ {
  path: "/",
  redirect: '/login',
  component: Login,},
  {
  path: "/home",
  component: Home,
  // children: [{
  //   path: "filetrans",
  //   component: Filetrans,
  // }]
},{
  path: "/login",
  component: Login
},{
    path: "/register",
    component: Register
  },{
    path: "/reset",
    component: Reset
  }]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
