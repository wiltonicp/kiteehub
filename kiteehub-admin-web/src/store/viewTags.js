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
import { defineStore } from 'pinia'

export const viewTagsStore = defineStore({
	id: 'viewTags',
	state: () => ({
		viewTags: []
	}),
	getters: {},
	actions: {
		pushViewTags(route) {
			const target = this.viewTags.find((item) => item.fullPath === route.fullPath)
			const isName = route.name
			if (!target && isName) {
				this.viewTags.push(route)
			}
		},
		removeViewTags(route) {
			this.viewTags.forEach((item, index) => {
				if (item.fullPath === route.fullPath) {
					this.viewTags.splice(index, 1)
				}
			})
		},
		updateViewTags(route) {
			this.viewTags.forEach((item) => {
				if (item.fullPath == route.fullPath) {
					Object.assign(item, route)
				}
			})
		},
		updateViewTagsTitle(title = '') {
			const nowFullPath = location.hash.substring(1)
			this.viewTags.forEach((item) => {
				if (item.fullPath == nowFullPath) {
					item.meta.title = title
				}
			})
		},
		clearViewTags() {
			this.viewTags = []
		}
	}
})
