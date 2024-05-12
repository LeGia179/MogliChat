import { createRouter, createWebHistory } from 'vue-router'
import RegisterLoginScreen from '../views/RegisterLoginScreenView.vue'
import ChatView from '../views/ChatView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: RegisterLoginScreen
    },
    {
      path: '/chat',
      name: 'chat',
      component: ChatView
    },
  ]
})

export default router
