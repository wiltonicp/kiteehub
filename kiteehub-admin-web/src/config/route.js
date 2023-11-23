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
// 静态路由配置
const routes = {
	// 默认模块，仅限于后端未添加任何单页配置，用此路由
	module: [
		{
			id: '01',
			name: 'homeModule',
			path: '/homeModule',
			component: '',
			meta: {
				title: '默认',
				type: 'module',
				icon: 'bank-outlined'
			},
			children: []
		}
	],
	// 默认首页、个人中心
	menu: [
		{
			id: '001',
			name: 'index',
			path: '/index',
			component: 'index/index',
			meta: {
				title: '首页',
				type: 'menu',
				icon: 'bank-outlined',
				affix: true
			},
			children: []
		},
		{
			id: '002',
			name: 'userCenter',
			path: '/userCenter',
			component: 'sys/user/userCenter',
			meta: {
				title: '个人中心',
				type: 'menu',
				hidden: true
			},
			children: []
		}
	]
}

export default routes
