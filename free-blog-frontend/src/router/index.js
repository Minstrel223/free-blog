import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.路由懒加载
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/catalog',
    name: 'Catalog',
    component: () => import(/* webpackChunkName: "catalog" */ '../views/Catalog.vue')
  },
  {
    path: '/passage',
    name: 'Passage',
    component: () => import(/* webpackChunkName: "passage" */ '../views/Passage.vue')
  },
  {
    path: '/search',
    name: 'Search',
    component: () => import(/* webpackChunkName: "search" */ '../views/Search.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
