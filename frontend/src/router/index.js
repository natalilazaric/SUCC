import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import VrsteUslugaView from '../views/VrsteUslugaView.vue'
import UslugeView from '@/views/UslugeView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: HomeView
    },
    {
      path: '/vrste-usluga',
      name: 'vrste-usluge',
      component: VrsteUslugaView
    },
    {
      path: '/usluge',
      name: 'usluge',
      component: UslugeView
    }
  ]
})

export default router