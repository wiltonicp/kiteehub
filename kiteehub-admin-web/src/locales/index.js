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
import { createI18n } from 'vue-i18n'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import enGB from 'ant-design-vue/es/locale/en_GB'
import zh_cn from './lang/zh-cn.js'
import en from './lang/en.js'
import tool from '@/utils/tool'
import sysConfig from '@/config/index'

export const messages = {
	'zh-cn': {
		lang: zhCN,
		...zh_cn
	},
	en: {
		lang: enGB,
		...en
	}
}

const i18n = createI18n({
	locale: tool.data.get('APP_LANG') || sysConfig.LANG,
	fallbackLocale: 'zh-cn',
	globalInjection: true,
	messages
})

export default i18n
