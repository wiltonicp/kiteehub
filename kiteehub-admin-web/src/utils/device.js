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
import enquireJs from 'enquire.js'

export const DEVICE_TYPE = {
	DESKTOP: 'desktop',
	TABLET: 'tablet',
	MOBILE: 'mobile'
}

export const deviceEnquire = function (callback) {
	const matchDesktop = {
		match: () => {
			callback && callback(DEVICE_TYPE.DESKTOP)
		}
	}

	const matchTablet = {
		match: () => {
			callback && callback(DEVICE_TYPE.TABLET)
		}
	}

	const matchMobile = {
		match: () => {
			callback && callback(DEVICE_TYPE.MOBILE)
		}
	}

	// screen and (max-width: 1087.99px)
	enquireJs
		.register('screen and (max-width: 576px)', matchMobile)
		.register('screen and (min-width: 576px) and (max-width: 1199px)', matchTablet)
		.register('screen and (min-width: 1200px)', matchDesktop)
}
