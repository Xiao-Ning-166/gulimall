import { getUserMenuList } from '@/api/menu'
import { constantRoutes } from '@/router/index'
import Layout from '@/layout'

function hasPermission(roles, route) {
  if (route.meta && route.meta.role) {
    return roles.some(role => route.meta.role.indexOf(role) >= 0)
  } else {
    return true
  }
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  // 设置路由
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {

  // 获取用户信息
  getMenuList({ commit, state }) {
    return new Promise((resolve, reject) => {
      // 查询用户的菜单列表
      getUserMenuList().then(response => {
        const { data } = response
        // 创建动态路由
        console.log('菜单', data)
        // 生成路由树
        const accessedRoutes = generateRoutes(data)
        // if (roles.includes('admin')) {
        //   accessedRoutes = asyncRoutes || []
        // } else {
        // accessedRoutes = routers
        // }
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      }).catch(error => {
        reject(error)
      })
    })
  }

}

/**
 * 生成动态路由表
 *
 * @param {*} menus 用户可访问的菜单
 */
function generateRoutes(menus) {
  const routes = []
  menus.forEach(menu => {
    let route = {
      path: `${menu.path}`,
      // component: component,
      // name: `${menu.name}`,
      meta: {
        title: `${menu.name}`,
        icon: `${menu.icon}`
      },
      children: []
    }

    if (menu.component === 'Layout') {
      route.component = Layout
    } else {
      try {
        // 引入组件（开发环境可用）
        route.component = resolve => require(['@/views' + menu.component + '.vue'], resolve).default
      } catch (error) {
        console.log(error)
        // 导入首页
        route.component = resolve => require(['@/views/dashboard/index'], resolve).default
      }
    }

    if (menu.children && menu.children.length > 0) {
      route.children = generateRoutes(menu.children)
    } else {
      delete menu.children
    }

    routes.push(route)
  })

  return routes
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
