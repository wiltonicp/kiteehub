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
import { generate } from '@ant-design/colors';
import tool from '../utils/tool'
import config from '../config'
import { ThemeModeEnum } from './enum'

const changeColor = (newPrimaryColor, theme, darkClass = 'snowy-theme-dark') => {
	return new Promise((resolve) => {
		const themeEleId = 'snowy-theme-var'
		const themeEle = document.querySelector(`#${themeEleId}`)
		if (themeEle && themeEle.parentNode) {
			themeEle.parentNode.removeChild(themeEle)
		}
		const isRealDark = theme === ThemeModeEnum.REAL_DARK
		if (newPrimaryColor) {
			const colors = generate(newPrimaryColor, isRealDark ? { theme: 'dark' } : {})
			const rootClass = isRealDark ? `.${darkClass}` : ':root'
			const styleElement = document.createElement('style')
			styleElement.id = themeEleId
			styleElement.setAttribute('type', 'text/css')
			styleElement.innerHTML = `${rootClass}{${colors
				.map((c, i) => {
					return `--primary-${i + 1}:${c};`
				})
				.concat([`--primary-color:${newPrimaryColor};`])
				.join('')}}`
			document.head.appendChild(styleElement)
		} else {
			document.body.removeAttribute('snowy-theme')
		}
		if (isRealDark) {
			document.body.classList.add(darkClass)
		} else {
			document.body.classList.remove(darkClass)
		}
		resolve()
	})
}

const loadLocalTheme = (localSetting) => {
	if (localSetting) {
		let { theme, themeColor } = localSetting
		themeColor = themeColor || config.COLOR
		theme = theme || config.THEME
		changeColor(themeColor, theme)
	}
}

/**
 * 获取本地保存的配置
 * @param loadTheme {boolean} 是否加载配置中的主题
 * @returns {Object}
 */
const getLocalSetting = (loadTheme) => {
	let localSetting = {}
	try {
		const theme = tool.data.get('KITEE_THEME')
		const themeColor = tool.data.get('KITEE_THEME_COLOR')
		localSetting = {
			theme,
			themeColor
		}
	} catch (e) {
		console.error(e)
	}
	if (loadTheme) {
		loadLocalTheme(localSetting)
	}
	return localSetting
}

export { loadLocalTheme, getLocalSetting, changeColor }
