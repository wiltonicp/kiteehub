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
const constRouters = [
	{
		path: '/findpwd'
	},
	{
		path: '/callback'
	},
	{
		path: '/other',
		name: 'other',
		component: () => import('@/views/other/index.vue'),
		meta: {
			title: '其他'
		}
	}
]
/**
 * 路由白名单（数组形式）
 *
 * 如果组件像登录一样，那就简单的写一个path，即可实现放开，
 * 如果组件不在这边的，需要手动添加组件，就像other一样，
 * 因为没登陆你没法拿到后端给你返回的那一坨，当然就找不到component
 *
 * @author yubaoshan
 */
export default constRouters
