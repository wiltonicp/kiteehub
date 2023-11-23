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
import configApi from '@/api/dev/configApi'
import { message } from 'ant-design-vue'

const formData = ref({
	KITEE_SYS_LOGO: '',
	KITEE_SYS_BACK_IMAGE: '',
	KITEE_SYS_NAME: '',
	KITEE_SYS_VERSION: '',
	KITEE_SYS_COPYRIGHT: '',
	KITEE_SYS_COPYRIGHT_URL: '',
	KITEE_SYS_DEFAULT_FILE_ENGINE: 'LOCAL',
	KITEE_SYS_DEFAULT_CAPTCHA_OPEN: false,
	KITEE_SYS_DEFAULT_PASSWORD: ''
})

const param = {
	category: 'SYS_BASE'
}

const getSysBaseConfig = () => {
	configApi.configList(param).then((data) => {
		if (data) {
			data.forEach((item) => {
				formData.value[item.configKey] = item.configValue ? '' : item.configValue
			})
		} else {
			message.warning('表单项不存在，请初始化数据库')
		}
	})
}
