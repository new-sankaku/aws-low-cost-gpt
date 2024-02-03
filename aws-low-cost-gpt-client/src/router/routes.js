import { createRouter, createWebHistory } from 'vue-router';


const routes = [
  {
    path: '/',
    name: 'UserLogin',
    component: () => import('pages/UserLogin.vue')
  },
  {
    path: '/UserChat',
    name: 'UserChat',
    component: () => import('pages/UserChat.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
];

export default routes;
