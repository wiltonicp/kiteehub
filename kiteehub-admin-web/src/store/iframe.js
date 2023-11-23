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

export const iframeStore = defineStore({
	id: 'iframe',
	state: () => ({
		iframeList: []
	}),
	getters: {},
	actions: {
		setIframeList(route) {
			this.iframeList = []
			this.iframeList.push(route)
		},
		pushIframeList(route) {
			const target = this.iframeList.find((item) => item.path === route.path)
			if (!target) {
				this.iframeList.push(route)
			}
		},
		removeIframeList(route) {
			this.iframeList.forEach((item, index) => {
				if (item.path === route.path) {
					this.iframeList.splice(index, 1)
				}
			})
		},
		refreshIframe(route) {
			this.iframeList.forEach((item) => {
				if (item.path === route.path) {
					const url = route.meta.url
					item.meta.url = ''
					setTimeout(() => {
						item.meta.url = url
					}, 200)
				}
			})
		},
		clearIframeList() {
			this.iframeList = []
		}
	}
})
