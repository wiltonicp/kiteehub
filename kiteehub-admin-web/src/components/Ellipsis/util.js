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

/**
 * 获取字符串长度，英文字符 长度1，中文字符长度2
 * @param {*} str
 */
export const getStrFullLength = (str = '') =>
	str.split('').reduce((pre, cur) => {
		const charCode = cur.charCodeAt(0)
		if (charCode >= 0 && charCode <= 128) {
			return pre + 1
		}
		return pre + 2
	}, 0)

/**
 * 截取字符串，根据 maxLength 截取后返回
 * @param {*} str
 * @param {*} maxLength
 */
export const cutStrByFullLength = (str = '', maxLength) => {
	let showLength = 0
	return str.split('').reduce((pre, cur) => {
		const charCode = cur.charCodeAt(0)
		if (charCode >= 0 && charCode <= 128) {
			showLength += 1
		} else {
			showLength += 2
		}
		if (showLength <= maxLength) {
			return pre + cur
		}
		return pre
	}, '')
}
