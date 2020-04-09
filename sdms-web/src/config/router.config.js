// eslint-disable-next-line
import { UserLayout, BasicLayout, RouteView, BlankLayout, PageView } from '@/layouts'
import { bxAnaalyse } from '@/core/icons'

export const asyncRouterMap = [

  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: '首页' },
    redirect: '/dashboard/workplace',
    children: [
      // dashboard
      {
        path: '/dashboard',
        name: 'dashboard',
        redirect: '/dashboard/workplace',
        component: RouteView,
        meta: { title: '工作台', keepAlive: true, icon: bxAnaalyse, permission: [ 'dashboard' ] },
        hideChildrenInMenu: true,
        children: [
          {
            path: '/dashboard/workplace',
            name: 'Workplace',
            component: () => import('@/views/dashboard/Workplace'),
            meta: { title: '工作台', keepAlive: true, permission: [ 'dashboard' ] }
          }
        ]
      },
      // 成长档案管理
      {
        path: '/studentDoc',
        name: 'studentDoc',
        redirect: '/studentDoc/study',
        component: RouteView,
        meta: { title: '成长档案管理', keepAlive: true, icon: bxAnaalyse, permission: [ 'student' ] },
        children: [
          {
            path: '/studentDoc/study',
            name: 'study',
            component: () => import('@/views/studentDoc/study/Index'),
            meta: { title: '考核与成绩', hideHeader: true, permission: [ 'user' ] },
            redirect: '/studentDoc/study/termStudy',
            hideChildrenInMenu: true,
            children: [
              {
                path: '/studentDoc/study/termStudy',
                name: 'termStudy',
                component: () => import('@/views/studentDoc/study/termStudy'),
                meta: { title: '学期记录', permission: [ 'user' ] }
              },
              {
                path: '/studentDoc/study/evaluate',
                name: 'evaluate',
                component: () => import('@/views/studentDoc/study/evaluate'),
                meta: { title: '学习评价', keepAlive: true, permission: [ 'user' ] }
              }
            ]
          },
          // {
          //   path: '/studentDoc/practice',
          //   name: 'practice',
          //   component: () => import('@/views/studentDoc/practice'),
          //   meta: { title: '履历中心', keepAlive: true, permission: [ 'profile' ] }
          // },
          {
            path: '/studentDoc/money',
            name: 'money',
            component: () => import('@/views/studentDoc/money'),
            meta: { title: '奖学金', keepAlive: true, permission: [ 'profile' ] }
          },
          {
            path: '/studentDoc/punishment',
            name: 'punishment',
            component: () => import('@/views/studentDoc/punishment'),
            meta: { title: '处分记录', keepAlive: true, permission: [ 'profile' ] }
          }
        ]
      },
      // forms
      {
        path: '/form',
        redirect: '/form/base-form',
        component: PageView,
        meta: { title: '入团入党', icon: 'profile', permission: [ 'student' ] },
        children: [
          {
            path: '/form/base-form',
            name: 'BaseForm',
            component: () => import('@/views/form/leagueApply/leagueApply'),
            meta: { title: '入团申请', keepAlive: true, permission: [ 'form' ] }
          },
          {
            path: '/form/step-form',
            name: 'StepForm',
            component: () => import('@/views/form/partApply/partApply'),
            meta: { title: '入党申请', keepAlive: true, permission: [ 'form' ] }
          }
          // {
          //   path: '/form/advanced-form',
          //   name: 'AdvanceForm',
          //   component: () => import('@/views/form/advancedForm/AdvancedForm'),
          //   meta: { title: '高级表单', keepAlive: true, permission: [ 'form' ] }
          // }
        ]
      },

      // list
      // {
      //   path: '/list',
      //   name: 'list',
      //   component: PageView,
      //   redirect: '/list/table-list',
      //   meta: { title: '列表页', icon: 'table', permission: [ 'table' ] },
      //   children: [
      //     {
      //       path: '/list/table-list/:pageNo([1-9]\\d*)?',
      //       name: 'TableListWrapper',
      //       hideChildrenInMenu: true, // 强制显示 MenuItem 而不是 SubMenu
      //       component: () => import('@/views/list/TableList'),
      //       meta: { title: '查询表格', keepAlive: true, permission: [ 'table' ] }
      //     },
      //     {
      //       path: '/list/basic-list',
      //       name: 'BasicList',
      //       component: () => import('@/views/list/StandardList'),
      //       meta: { title: '标准列表', keepAlive: true, permission: [ 'table' ] }
      //     },
      //     {
      //       path: '/list/card',
      //       name: 'CardList',
      //       component: () => import('@/views/list/CardList'),
      //       meta: { title: '卡片列表', keepAlive: true, permission: [ 'table' ] }
      //     },
      //     {
      //       path: '/list/search',
      //       name: 'SearchList',
      //       component: () => import('@/views/list/search/SearchLayout'),
      //       redirect: '/list/search/article',
      //       meta: { title: '搜索列表', keepAlive: true, permission: [ 'table' ] },
      //       children: [
      //         {
      //           path: '/list/search/article',
      //           name: 'SearchArticles',
      //           component: () => import('../views/list/search/Article'),
      //           meta: { title: '搜索列表（文章）', permission: [ 'table' ] }
      //         },
      //         {
      //           path: '/list/search/project',
      //           name: 'SearchProjects',
      //           component: () => import('../views/list/search/Projects'),
      //           meta: { title: '搜索列表（项目）', permission: [ 'table' ] }
      //         },
      //         {
      //           path: '/list/search/application',
      //           name: 'SearchApplications',
      //           component: () => import('../views/list/search/Applications'),
      //           meta: { title: '搜索列表（应用）', permission: [ 'table' ] }
      //         }
      //       ]
      //     }
      //   ]
      // },

      // competition
      {
        path: '/competition',
        name: 'competition',
        component: PageView,
        redirect: '/competition/game',
        meta: { title: '办事指南', icon: 'profile', permission: [ 'user' ] },
        children: [
          {
            path: '/competition/game',
            name: 'competitionGame',
            component: RouteView,
            meta: { title: '学科竞赛', permission: [ 'manager' ] },
            hideChildrenInMenu: true,
            redirect: '/competition/game/Index',
            children: [
              {
                path: '/competition/game/Index',
                name: 'competitionGame',
                component: () => import('@/views/competition/game/Index'),
                meta: { title: '学科竞赛', permission: [ 'user' ] }
              },
              {
                path: '/competition/game/detail',
                name: 'competitionDetail',
                component: () => import('@/views/competition/game/detail'),
                meta: { title: '竞赛详细', permission: [ 'user' ] }
              }
            ]
          },
          {
            path: '/competition/gameStu',
            name: 'competitionGameStu',
            component: RouteView,
            meta: { title: '学科竞赛', permission: [ 'student' ] },
            hideChildrenInMenu: true,
            redirect: '/competition/gameStu/Index',
            children: [
              {
                path: '/competition/gameStu/Index',
                name: 'competitionGame',
                component: () => import('@/views/competition/gameStu/Index'),
                meta: { title: '学科竞赛', permission: [ 'user' ] }
              }
            ]
          },
          {
            path: '/competition/activity',
            name: 'activity',
            component: RouteView,
            meta: { title: '活动中心', permission: [ 'manager' ] },
            hideChildrenInMenu: true,
            redirect: '/competition/activity/Index',
            children: [
              {
                path: '/competition/activity/Index',
                name: 'activity',
                component: () => import('@/views/competition/activity/Index'),
                meta: { title: '活动中心', permission: [ 'user' ] }
              },
              {
                path: '/competition/activity/detail',
                name: 'activityDetail',
                component: () => import('@/views/competition/activity/detail'),
                meta: { title: '活动详情', permission: [ 'user' ] }
              }
            ]
          },
          {
            path: '/competition/activityStu',
            name: 'activityStu',
            component: RouteView,
            meta: { title: '活动中心', permission: [ 'student' ] },
            hideChildrenInMenu: true,
            redirect: '/competition/activityStu/Index',
            children: [
              {
                path: '/competition/activityStu/Index',
                name: 'activityStu',
                component: () => import('@/views/competition/activityStu/Index'),
                meta: { title: '活动中心', permission: [ 'user' ] }
              },
              {
                path: '/competition/activityStu/detail',
                name: 'activityDetailStu',
                component: () => import('@/views/competition/activityStu/detail'),
                meta: { title: '活动详情', permission: [ 'user' ] }
              }
            ]
          },
          {
            path: '/competition/punishment',
            name: 'punishmentQuery',
            component: () => import('@/views/competition/punishment/Index'),
            meta: { title: '处分录入', permission: [ 'manager' ] }
          },
          {
            path: '/competition/club',
            name: 'club',
            component: () => import('@/views/competition/club/Index'),
            meta: { title: '学生天地', hiddenHeaderContent: true, permission: [ 'student' ] }
          }
        ]
      },

      // profile
      // {
      //   path: '/profile',
      //   name: 'profile',
      //   component: RouteView,
      //   redirect: '/profile/basic',
      //   meta: { title: '详情页', icon: 'profile', permission: [ 'profile' ] },
      //   children: [
      //     {
      //       path: '/profile/basic',
      //       name: 'ProfileBasic',
      //       component: () => import('@/views/profile/basic/Index'),
      //       meta: { title: '基础详情页', permission: [ 'profile' ] }
      //     },
      //     {
      //       path: '/profile/advanced',
      //       name: 'ProfileAdvanced',
      //       component: () => import('@/views/profile/advanced/Advanced'),
      //       meta: { title: '高级详情页', permission: [ 'profile' ] }
      //     }
      //   ]
      // },

      // result
      {
        path: '/result',
        name: 'result',
        component: PageView,
        hidden: true,
        redirect: '/result/success',
        meta: { title: '结果页', icon: 'check-circle-o', permission: [ 'result' ] },
        children: [
          {
            path: '/result/success',
            name: 'ResultSuccess',
            component: () => import(/* webpackChunkName: "result" */ '@/views/result/Success'),
            meta: { title: '成功', keepAlive: false, hiddenHeaderContent: true, permission: [ 'result' ] }
          },
          {
            path: '/result/fail',
            name: 'ResultFail',
            component: () => import(/* webpackChunkName: "result" */ '@/views/result/Error'),
            meta: { title: '失败', keepAlive: false, hiddenHeaderContent: true, permission: [ 'result' ] }
          }
        ]
      },

      // account
      {
        path: '/account',
        component: RouteView,
        redirect: '/account/settings',
        name: 'account',
        meta: { title: '个人设置', icon: 'user', keepAlive: true, permission: [ 'user' ] },
        hideChildrenInMenu: true,
        children: [
          {
            path: '/account/settings',
            name: 'settings',
            component: () => import('@/views/account/settings/Index'),
            meta: { title: '个人设置', hideHeader: true, permission: [ 'user' ] },
            redirect: '/account/settings/base',
            hideChildrenInMenu: true,
            children: [
              {
                path: '/account/settings/base',
                name: 'BaseSettings',
                component: () => import('@/views/account/settings/BaseSetting'),
                meta: { title: '基本设置', permission: [ 'user' ] }
              },
              {
                path: '/account/settings/security',
                name: 'SecuritySettings',
                component: () => import('@/views/account/settings/Security'),
                meta: { title: '安全设置', keepAlive: true, permission: [ 'user' ] }
              },
              {
                path: '/account/settings/custom',
                name: 'CustomSettings',
                component: () => import('@/views/account/settings/Custom'),
                meta: { title: '个性化设置', keepAlive: true, permission: [ 'user' ] }
              },
              {
                path: '/account/settings/binding',
                name: 'BindingSettings',
                component: () => import('@/views/account/settings/Binding'),
                meta: { title: '账户绑定', keepAlive: true, permission: [ 'user' ] }
              },
              {
                path: '/account/settings/notification',
                name: 'NotificationSettings',
                component: () => import('@/views/account/settings/Notification'),
                meta: { title: '管理员邀请码', keepAlive: true, permission: [ 'user' ] }
              }
            ]
          }
          // {
          //   path: '/account/manager',
          //   name: 'manager',
          //   component: () => import('@/views/account/manager/Index'),
          //   meta: { title: '系统用户管理', keepAlive: true, permission: [ 'user' ] }
          // }
        ]
      },

      // todo
      {
        path: '/standby',
        component: RouteView,
        redirect: '/standby/manager',
        name: 'standby',
        meta: { title: '管理中心', icon: 'user', keepAlive: true, permission: [ 'manager' ] },
        children: [
          {
            path: '/standby/manager',
            name: 'manager',
            component: () => import('@/views/standby/manager/Index'),
            meta: { title: '系统用户管理', keepAlive: true, permission: [ 'manager' ] }
          },
          {
            path: '/standby/todo',
            name: 'todo',
            component: () => import('@/views/standby/todo/Index'),
            meta: { title: '待办待阅', keepAlive: true, permission: [ 'manager' ] }
          }
        ]
      }

      // other
      // {
      //   path: '/other',
      //   name: 'otherPage',
      //   component: PageView,
      //   meta: { title: '其他组件', icon: 'slack', permission: [ 'dashboard' ] },
      //   redirect: '/other/icon-selector',
      //   children: [
      //     {
      //       path: '/other/icon-selector',
      //       name: 'TestIconSelect',
      //       component: () => import('@/views/other/IconSelectorView'),
      //       meta: { title: 'IconSelector', icon: 'tool', keepAlive: true, permission: [ 'dashboard' ] }
      //     },
      //     {
      //       path: '/other/list',
      //       component: RouteView,
      //       meta: { title: '业务布局', icon: 'layout', permission: [ 'support' ] },
      //       redirect: '/other/list/tree-list',
      //       children: [
      //         {
      //           path: '/other/list/tree-list',
      //           name: 'TreeList',
      //           component: () => import('@/views/other/TreeList'),
      //           meta: { title: '树目录表格', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/edit-table',
      //           name: 'EditList',
      //           component: () => import('@/views/other/TableInnerEditList'),
      //           meta: { title: '内联编辑表格', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/user-list',
      //           name: 'UserList',
      //           component: () => import('@/views/other/UserList'),
      //           meta: { title: '用户列表', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/role-list',
      //           name: 'RoleList',
      //           component: () => import('@/views/other/RoleList'),
      //           meta: { title: '角色列表', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/system-role',
      //           name: 'SystemRole',
      //           component: () => import('@/views/role/RoleList'),
      //           meta: { title: '角色列表2', keepAlive: true }
      //         },
      //         {
      //           path: '/other/list/permission-list',
      //           name: 'PermissionList',
      //           component: () => import('@/views/other/PermissionList'),
      //           meta: { title: '权限列表', keepAlive: true }
      //         }
      //       ]
      //     }
      //   ]
      // }
    ]
  },
  {
    path: '*', redirect: '/dashboard/workplace', hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Register')
      },
      {
        path: 'register-result',
        name: 'registerResult',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/RegisterResult')
      },
      {
        path: 'recover',
        name: 'recover',
        component: undefined
      }
    ]
  },

  {
    path: '/test',
    component: BlankLayout,
    redirect: '/test/home',
    children: [
      {
        path: 'home',
        name: 'TestHome',
        component: () => import('@/views/Home')
      }
    ]
  },

  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }

]
