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
const DEFAULT_CONFIG = {
	// 首页地址
	DASHBOARD_URL: '/index',

	// 接口地址
	API_URL: import.meta.env.VITE_API_BASEURL,

	// 请求超时
	TIMEOUT: 60000,

	// TokenName // Authorization
	TOKEN_NAME: 'token',

	// Token前缀，注意最后有个空格，如不需要需设置空字符串 // Bearer
	TOKEN_PREFIX: '',

	// 追加其他头
	HEADERS: {},

	// 请求是否开启缓存
	REQUEST_CACHE: false,

	// 布局 经典：classical，双排菜单：doublerow
	KITEE_LAYOUT: 'doublerow',

	// 菜单是否折叠
	KITEE_MENU_COLLAPSE: false,

	// 模块坞
	KITEE_MODULE_UNFOLD_OPEN: true,

	// 是否开启多标签
	KITEE_LAYOUT_TAGS_OPEN: true,

	// 是否开启展示面包屑
	KITEE_BREADCRUMD_OPEN: false,

	// 顶栏是否应用主题色
	KITEE_TOP_HANDER_THEME_COLOR_OPEN: false,

	// 顶栏主题色通栏
	KITEE_TOP_HANDER_THEME_COLOR_SPREAD: false,

	// 侧边菜单是否排他展开
	KITEE_SIDE_UNIQUE_OPEN: true,

	// 语言
	LANG: 'zh-cn',

	// 主题颜色
	COLOR: '#1890FF',

	// 默认整体主题
	KITEE_THEME: 'dark',

	// 整体表单风格
	KITEE_FORM_STYLE: 'drawer',

	// 成功色
	success: '#52c41a',

	// 警告色
	warning: '#faad14',

	// 错误色
	error: '#f5222f',

	// 系统基础配置，这些是数据库中保存起来的
	SYS_BASE_CONFIG: {
		// 默认logo
		KITEE_SYS_LOGO: '/img/logo.png',
		// 背景图
		KITEE_SYS_BACK_IMAGE: '',
		// 系统名称
		KITEE_SYS_NAME: '智能咨询服务平台',
		// 版本
		KITEE_SYS_VERSION: '2.0',
		// 版权
		KITEE_SYS_COPYRIGHT: '知识库 ©2022 Created by 智能咨询服务平台',
		// 版权跳转URL
		KITEE_SYS_COPYRIGHT_URL: 'https://www.kitee.com',
		// 默认文件存储
		KITEE_SYS_DEFAULT_FILE_ENGINE: 'LOCAL',
		// 是否开启验证码
		KITEE_SYS_DEFAULT_CAPTCHA_OPEN: 'false',
		// 默认重置密码
		KITEE_SYS_DEFAULT_PASSWORD: '123456'
	}
}

export default DEFAULT_CONFIG
