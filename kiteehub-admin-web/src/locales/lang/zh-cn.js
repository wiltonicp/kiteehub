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
import 'dayjs/locale/zh-cn'

export default {
	common: {
		searchButton: '查询',
		resetButton: '重置',
		addButton: '增加',
		editButton: '编辑',
		removeButton: '删除',
		batchRemoveButton: '批量删除',
		detailButton: '详情',
		searchKey: '关键词',
		imports: '导入',
		more: '更多',
		export: '导出',
	},
	model: {
		user: '用户',
		org: '机构',
		pos: '职位',
		role: '角色',
		bizUser: '人员'
	},
	login: {
		signInTitle: '用户登录',
		forgetPassword: '忘记密码',
		signIn: '登录',
		signInOther: '其他登录方式',
		accountPlaceholder: '请输入账号',
		accountError: '请输入账号',
		PWPlaceholder: '请输入密码',
		PWError: '请输入密码',
		validLaceholder: '请输入验证码',
		validError: '请输入验证码',
		accountPassword: '账号密码',
		phoneSms: '手机号登录',
		phonePlaceholder: '请输入手机号',
		smsCodePlaceholder: '请输入短信验证码',
		getSmsCode: '获取验证码',
		machineValidation: '机器验证',
		sendingSmsMessage: '短信发送中',
		newPwdPlaceholder: '请输入新密码',
		backLogin: '返回登录',
		restPassword: '重置密码',
		emailPlaceholder: '请输入邮箱号',
		emailCodePlaceholder: '请输入邮件验证码',
		restPhoneType: '手机号找回',
		restEmailType: '邮箱找回'
	},
	user: {
		userStatus: '用户状态',
		resetPassword: '重置密码',
		role: '角色',
		batchExportButton: '批量导出',
		grantRole: '授权角色',
		grantResource: '授权资源',
		grantPermission: '授权权限',
		exportUserInfo: '导出信息',
		placeholderNameAndSearchKey: '请输入姓名或关键词',
		placeholderUserStatus: '请选择状态',
		popconfirmDeleteUser: '确定要删除吗？',
		popconfirmResatUserPwd: '确定要重置吗？'
	}
}
