import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // 设置页面标题
  document.title = getPageTitle(to.meta.title)

  // 确定用户是否已登录
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // 如果已登录，则重定向到主页
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name
      // 判断用户信息是否已存在
      if (hasGetUserInfo) {
        // 当有用户信息的时候，说明所有可访问路由已生成 如访问没权限的全面会自动进入404页面
        next()
      } else {
        try {
          // 获取用户信息
          await store.dispatch('user/getInfo')
          // 获取用户菜单信息
          const accessedRoutes = await store.dispatch('menu/getMenuList')
          console.log('======用户菜单======', accessedRoutes)
          // 将用户菜单添加到路由表，在最后加入404页面
          router.addRoutes([...accessedRoutes, { path: '*', redirect: '/404', hidden: true }])
          console.log('======动态路由======', router)

          // hack method to ensure that addRoutes is complete
          // set the replace: true, so the navigation will not leave a history record
          next({ ...to, replace: true })
        } catch (error) {
          // 删除token并转到登录页面重新登录
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
