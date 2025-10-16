import {createRouter, createWebHistory} from 'vue-router'
import Home from "../view/Home.vue"
import Login from "../view/Login.vue";

const routes = [ {
  path: "/home",
  component: Home,
  // children: [{
  //   path: "filetrans",
  //   component: Filetrans,
  // }]
},{
  path: "/login",
  component: Login
}]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
