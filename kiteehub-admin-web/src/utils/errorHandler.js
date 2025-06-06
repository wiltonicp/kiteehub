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
 * 全局代码错误捕捉
 * 比如 null.length 就会被捕捉到
 */
import { notification } from 'ant-design-vue'

export default (error) => {
	// 过滤HTTP请求错误
	if (error.code) {
		return false
	}
	const errorMap = {
		InternalError: 'Javascript引擎内部错误',
		ReferenceError: '未找到对象',
		TypeError: '使用了错误的类型或对象',
		RangeError: '使用内置对象时，参数超范围',
		SyntaxError: '语法错误',
		EvalError: '错误的使用了Eval',
		URIError: 'URI错误'
	}
	const errorName = errorMap[error.name] || '未知错误'
	nextTick(() => {
		notification.error({
			message: '错误',
			description: errorName
		})
		console.error(error)
	})
}
