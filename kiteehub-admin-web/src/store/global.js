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
import { changeColor } from '@/utils/themeUtil'
import config from '@/config'
import { message } from 'ant-design-vue'
import tool from '@/utils/tool'

const toolDataGet = (key) => {
	return tool.data.get(key)
}

// 获取缓存中的，如果取不到那就用配置的
const getCacheConfig = (value) => {
	const data = toolDataGet(value)
	if (data === null) {
		return config[value]
	}
	return data
}

/**
 * deprecated 请使用 useGlobalStore
 */
export const globalStore = defineStore({
	id: 'global',
	state: () => ({
		// 移动端布局
		ismobile: false,
		// 布局
		layout: getCacheConfig('KITEE_LAYOUT'),
		// 菜单是否折叠 toggle
		menuIsCollapse: getCacheConfig('KITEE_MENU_COLLAPSE'),
		// 侧边菜单是否排他展开
		sideUniqueOpen: getCacheConfig('KITEE_SIDE_UNIQUE_OPEN'),
		// 多标签栏
		layoutTagsOpen: getCacheConfig('KITEE_LAYOUT_TAGS_OPEN'),
		// 是否展示面包屑
		breadcrumbOpen: getCacheConfig('KITEE_BREADCRUMD_OPEN'),
		// 顶栏是否应用主题色
		topHanderThemeColorOpen: getCacheConfig('KITEE_TOP_HANDER_THEME_COLOR_OPEN'),
		// 顶栏主题色通栏
		topHanderThemeColorSpread: getCacheConfig('KITEE_TOP_HANDER_THEME_COLOR_SPREAD'),
		// 模块坞
		moduleUnfoldOpen: getCacheConfig('KITEE_MODULE_UNFOLD_OPEN'),
		// 主题
		theme: getCacheConfig('KITEE_THEME'),
		// 主题颜色
		themeColor: toolDataGet('KITEE_THEME_COLOR') || config.COLOR,
		// 整体表单风格
		formStyle: getCacheConfig('KITEE_FORM_STYLE'),
		// 用户信息
		userInfo: toolDataGet('USER_INFO') || {},
		// 系统配置
		sysBaseConfig: toolDataGet('KITEE_SYS_BASE_CONFIG') || config.SYS_BASE_CONFIG
	}),
	getters: {},
	actions: {
		setIsmobile(key) {
			this.ismobile = key
		},
		setLayout(key) {
			this.layout = key
		},
		setTheme(key) {
			this.theme = key
			const closeMessage = message.loading(`加载中...`)
			changeColor(this.themeColor, key).then(closeMessage)
		},
		setThemeColor(key) {
			this.themeColor = key
			const closeMessage = message.loading(`加载中...`)
			changeColor(key, this.theme).then(closeMessage)
		},
		initTheme() {
			const closeMessage = message.loading(`加载中...`)
			changeColor(this.themeColor, this.theme).then(closeMessage)
		},
		toggleConfig(key) {
			this[key] = !this[key]
		},
		setFormStyle(key) {
			this.formStyle = key
		},
		setUserInfo(key) {
			this.userInfo = key
		},
		setSysBaseConfig(key) {
			this.sysBaseConfig = key
		}
	}
})

export const useGlobalStore = globalStore
