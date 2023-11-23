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
import { nextTick } from 'vue'
import { viewTagsStore } from '@/store'

export function beforeEach(to, from) {
	const adminMain = document.querySelector('#adminui-main')
	if (!adminMain) {
		return false
	}
	const store = viewTagsStore()
	store.updateViewTags({
		fullPath: from.fullPath,
		scrollTop: adminMain.scrollTop
	})
}

export function afterEach(to) {
	const adminMain = document.querySelector('#adminui-main')
	if (!adminMain) {
		return false
	}
	nextTick(() => {
		const store = viewTagsStore()
		const beforeRoute = store.viewTags.filter((v) => v.fullPath == to.fullPath)[0]
		if (beforeRoute) {
			adminMain.scrollTop = beforeRoute.scrollTop || 0
		}
	})
}
