import { createRouter, createWebHistory } from 'vue-router'
import ChatUIView from '../views/ChatView.vue'
import StartView from "@/views/StartView.vue";
import LoginScreenView from "@/views/LoginScreenView.vue";
import RegisterScreenView from "@/views/RegisterScreenView.vue";
import ShowFishView from "@/views/ShowFishView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/users',
      name: 'Signup',
      component: RegisterScreenView
    },
    {
      path: '/users/:userId',
      name: 'ChatUI',
      component: ChatUIView
    },
    {
      path: '/login',
      name: 'LoginView',
      component: LoginScreenView
    },
    {
      path: '/',
      name: 'Home',
      component: StartView
    },
    {
      path: '/showFish',
      name: 'ShowFish',
      component: ShowFishView
    }
  ]
})

export default router
