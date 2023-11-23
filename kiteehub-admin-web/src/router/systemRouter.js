/*
 * Copyright [2022] [https://www.kiteehub.com]
 *
 * Kiteehub是内部代码，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Kiteehub源码头部的版权声明。
 * 3.本项目代码使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.kiteehub.com
 * 5.本项目只可用于内部开发，如有问题可联系团队wilton.icp@gmail.com商议合作。
 */
import config from '@/config'
import tool from '@/utils/tool'

// 系统路由
const routes = [
	{
		name: 'layout',
		path: '/',
		component: () => import('@/layout/index.vue'),
		redirect: tool.data.get('MENU') ? tool.data.get('MENU')[0].children[0].path : config.DASHBOARD_URL,
		children: []
	},
	{
		path: '/login',
		component: () => import('@/views/auth/login/login.vue'),
		meta: {
			title: '登录'
		}
	},
	{
		path: '/findpwd',
		component: () => import('@/views/auth/findPwd/index.vue'),
		meta: {
			title: '找回密码'
		}
	},
	{
		path: '/callback',
		component: () => import('@/views/auth/login/callback.vue'),
		meta: {
			title: '三方登录'
		}
	}
]

export default routes
