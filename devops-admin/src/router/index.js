import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  // 首页
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '自動化運維平台首頁', icon: 'dashboard' }
    }]
  },

  // 視圖
  {
    path: '/view',
    component: Layout,
    redirect: '/view/dashboard',
    name: 'File',
    meta: { title: '視圖', icon: 'guide' },
    children: [
      {
        path: 'dashboard',
        name: 'ViewDashboard',
        component: () => import('@/views/view/dashboard'),
        meta: { title: '儀表板' }
      },
      {
        path: 'operation',
        name: 'ViewOperation',
        component: () => import('@/views/view/operation'),
        meta: { title: '作業' }
      },
      {
        path: 'plan',
        name: 'ViewPlan',
        component: () => import('@/views/view/plan'),
        meta: { title: '計畫' }
      },
      {
        path: 'myview',
        name: 'ViewMyView',
        component: () => import('@/views/view/myview'),
        meta: { title: '我的視圖' }
      }
    ]
  },

  // 資源
  {
    path: '/resource',
    component: Layout,
    redirect: '/resource/list',
    name: 'Src',
    meta: { title: '資源', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'template',
        name: 'SrcTemplate',
        component: () => import('@/views/resource/template'),
        meta: { title: '模板' }
      },
      {
        path: 'credential',
        name: 'SrcCredential',
        component: () => import('@/views/resource/credential'),
        meta: { title: '憑證' }
      },
      {
        path: 'item',
        name: 'SrcItem',
        component: () => import('@/views/resource/item'),
        meta: { title: '項目' }
      },
      {
        path: 'list',
        name: 'SrcList',
        component: () => import('@/views/resource/list'),
        meta: { title: '清單' }
      },
      {
        path: 'script',
        name: 'SrcScript',
        component: () => import('@/views/resource/script'),
        meta: { title: '清單腳本' }
      }
    ]
  },

  // 檔案管理
  {
    path: '/file',
    component: Layout,
    redirect: '/file/list',
    name: 'File',
    meta: { title: '檔案管理', icon: 'documentation' },
    children: [
      {
        path: 'list',
        name: 'FileList',
        component: () => import('@/views/file/list'),
        meta: { title: '檔案列表' }
      },
      {
        path: 'backup',
        name: 'FileCreate',
        component: () => import('@/views/file/backup'),
        meta: { title: '檔案備份' }
      },
      {
        path: 'config',
        name: 'FileWhite',
        component: () => import('@/views/file/config'),
        meta: { title: '許可清單' }
      }
    ]
  },

  // 訪問
  {
    path: '/visit',
    component: Layout,
    redirect: '/visit/list',
    name: 'Visit',
    meta: { title: '訪問', icon: 'lock' },
    children: [
      {
        path: 'agent',
        name: 'VisitAgent',
        component: () => import('@/views/visit/agent'),
        meta: { title: '機構' }
      },
      {
        path: 'user',
        name: 'VisitUser',
        component: () => import('@/views/visit/user'),
        meta: { title: '用戶' }
      },
      {
        path: 'team',
        name: 'VisitTeam',
        component: () => import('@/views/visit/team'),
        meta: { title: '團隊' }
      }
    ]
  },

  // 管理
  {
    path: '/manage',
    component: Layout,
    redirect: '/manage/list',
    name: 'Manage',
    meta: { title: '管理', icon: 'el-icon-s-tools' },
    children: [
      {
        path: 'type',
        name: 'ManageType',
        component: () => import('@/views/manage/type'),
        meta: { title: '憑證類型' }
      },
      {
        path: 'notify',
        name: 'ManageNotify',
        component: () => import('@/views/manage/notify'),
        meta: { title: '通知' }
      },
      {
        path: 'schedule',
        name: 'ManageSchedule',
        component: () => import('@/views/manage/schedule'),
        meta: { title: '管理作業' }
      },
      {
        path: 'instance',
        name: 'ManageInstance',
        component: () => import('@/views/manage/instance'),
        meta: { title: '實例組' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
