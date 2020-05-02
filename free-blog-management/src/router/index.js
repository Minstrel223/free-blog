import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import(/* webpackChunkName: "newArticle" */ '../views/NewArticle.vue')
  },
  {
    path: '/ArticleManagement',
    name: 'article-management',
    component: () => import(/* weebpackChunkName: "articleManagement" */ '../views/ArticleManagement.vue')
  },
  {
    path: '/CategoryManagement',
    name: 'category-management',
    component: () => import(/* webpackChunkName: "categoryManagement" */ '../views/CategoryManagement.vue')
  },
  {
    path: '/CommentsManagement',
    name: 'comments-management',
    component: () => import(/* webpackChunkName: "commentsManagement" */ '../views/CommentsManagement.vue')
  },
  {
    path: '/SiteInfo',
    name: 'site-info',
    component: () => import(/* webpackChunkName: "siteInfo" */ '../views/SiteInfo.vue')
  },
  {
    path: '/Login',
    name: 'login',
    component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
