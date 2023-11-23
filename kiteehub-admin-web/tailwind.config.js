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
const generatePrimaryColors = () => {
	const result = {
		primary: `var(--primary-color)`
	}
	for (let i = 0; i < 10; i++) {
		result[`primary-${i}`] = `var(--primary-${i})`
	}
	return result
}

const generateFontSize = () => {
	const result = {}
	for (let i = 10; i < 32; i++) {
		result[i] = `${i}px`
	}
	return result
}

const colors = require('tailwindcss/colors')

const filterWarnColors = (colors) => {
	const result = {}
	for (const key in colors) {
		if (['lightBlue', 'warmGray', 'trueGray', 'coolGray', 'blueGray'].indexOf(key) === -1) {
			result[key] = colors[key]
		}
	}
	return result
}

module.exports = {
	content: ['./src/**/*.vue', './src/**/*.js'],
	darkMode: 'class', // or 'media' or 'class'
	corePlugins: {
		preflight: false
	},
	theme: {
		extend: {},
		colors: {
			transparent: 'transparent',
			current: 'currentColor',
			...filterWarnColors(colors),
			...generatePrimaryColors()
		},
		fontWeight: {
			1: 100,
			2: 200,
			3: 300,
			4: 400,
			5: 500,
			6: 600,
			7: 700,
			8: 800,
			9: 900
		},
		fontSize: {
			...generateFontSize()
		}
	},
	variants: {},
	plugins: []
}
