import { createRouter, createWebHistory } from 'vue-router'
import RegisterLoginScreen from '../views/RegisterLoginScreenView.vue'
import ChatView from '../views/ChatView.vue';
import SupportView from '@/views/SupportView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: RegisterLoginScreen
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
    }
  ]
})

export default router
