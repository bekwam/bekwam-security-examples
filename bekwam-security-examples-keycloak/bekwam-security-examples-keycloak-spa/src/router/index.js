import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginLanding from "../views/LoginLanding.vue";
import Home from '../views/Home.vue'
import LogoutLanding from "../views/LogoutLanding.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "Login",
    component: LoginLanding
  },
  {
    path: "/home",
    name: "Home",
    component: Home
  },
  {
    path: "/logout",
    name: "Logout",
    component: LogoutLanding
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
