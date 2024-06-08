import { createRouter, createWebHistory } from 'vue-router'

import ChatView from '../views/ChatView.vue';
import SupportView from '@/views/SupportView.vue'
import StartView from "@/views/StartView.vue";
import LoginScreenView  from "@/views/LoginScreenView.vue";
import RegisterScreenView from "@/views/RegisterScreenView.vue";



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginScreenView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterScreenView
    },

    {
      path: '/chat',
      name: 'chat',
      component: ChatView
    },
    {
      path: '/support',
      name: 'support',
      component: SupportView
    },
    {
      path: '/',
      name: 'home',
      component: StartView
    },
  ]
})

export default router