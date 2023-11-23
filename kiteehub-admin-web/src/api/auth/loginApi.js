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
import { moduleRequest } from '@/utils/request'

const request = moduleRequest(`/auth/b/`)
/**
 * 登录
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// B端获取图片验证码
	getPicCaptcha(data) {
		return request('getPicCaptcha', data, 'get')
	},
	// B端获取手机验证码
	getPhoneValidCode(data) {
		return request('getPhoneValidCode', data, 'get')
	},
	// B端账号密码登录
	login(data) {
		return request('doLogin', data, 'post', false)
	},
	// B端手机验证码登录
	loginByPhone(data) {
		return request('doLoginByPhone', data, 'post', false)
	},
	// 退出
	logout(data) {
		return request('doLogout', data, 'get')
	},
	// 获取用户信息
	getLoginUser(data) {
		return request('getLoginUser', data, 'get')
	}
}
