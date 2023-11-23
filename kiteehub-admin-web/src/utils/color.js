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
/* eslint-disable eqeqeq */
export default {
	// hex颜色转rgb颜色
	HexToRgb(str) {
		str = str.replace('#', '')
		const hxs = str.match(/../g)
		for (let i = 0; i < 3; i++) hxs[i] = parseInt(hxs[i], 16)
		return hxs
	},
	// rgb颜色转hex颜色
	RgbToHex(a, b, c) {
		const hexs = [a.toString(16), b.toString(16), c.toString(16)]
		for (let i = 0; i < 3; i++) {
			if (hexs[i].length == 1) hexs[i] = `0${hexs[i]}`
		}
		return `#${hexs.join('')}`
	},
	// 加深
	darken(color, level) {
		const rgbc = this.HexToRgb(color)
		for (let i = 0; i < 3; i++) rgbc[i] = Math.floor(rgbc[i] * (1 - level))
		return this.RgbToHex(rgbc[0], rgbc[1], rgbc[2])
	},
	// 变淡
	lighten(color, level) {
		const rgbc = this.HexToRgb(color)
		for (let i = 0; i < 3; i++) rgbc[i] = Math.floor((255 - rgbc[i]) * level + rgbc[i])
		return this.RgbToHex(rgbc[0], rgbc[1], rgbc[2])
	}
}
