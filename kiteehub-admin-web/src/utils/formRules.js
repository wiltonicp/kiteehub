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
export const required = (message, trigger = ['blur', 'change']) => ({
	required: true,
	message,
	trigger
})

// 常用正则规则大全：https://any86.github.io/any-rule/

export const rules = {
	phone: {
		pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
		message: '请填写符合要求的11位手机号',
		trigger: 'blur'
	},
	email: {
		pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
		message: '请填写正确的邮箱号',
		trigger: 'blur'
	},
	idCard: {
		pattern:
			/(^\d{8}(0\d|10|11|12)([0-2]\d|30|31)\d{3}$)|(^\d{6}(18|19|20)\d{2}(0[1-9]|10|11|12)([0-2]\d|30|31)\d{3}(\d|X|x)$)/,
		message: '请填写符合要求的身份证号',
		trigger: 'blur'
	},
	lettersNum: {
		pattern: /^[A-Za-z0-9]+$/,
		message: '填写内容须是字母或数字组成',
		trigger: 'blur'
	},
	number: {
		pattern: /^\d{1,}$/,
		message: '填写内容必须是纯数字',
		trigger: 'blur'
	},
	price: {
		pattern: /(?:^[1-9]([0-9]+)?(?:\.[0-9]{1,2})?$)|(?:^(?:0)$)|(?:^[0-9]\.[0-9](?:[0-9])?$)/,
		message: '只支持正数金额',
		trigger: 'blur'
	}
}
