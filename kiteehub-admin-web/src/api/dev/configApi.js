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
import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/dev/config/${url}`, ...arg)
/**
 * 配置
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 获取配置分页
	configPage(data) {
		return request('page', data, 'get')
	},
	// 获取配置列表
	configList(data) {
		return request('list', data, 'get')
	},
	// 提交表单 edit为true时为编辑，默认为新增
	submitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除配置
	configDelete(data) {
		return request('delete', data)
	},
	// 获取配置详情
	configDetail(data) {
		return request('detail', data, 'get')
	},
	// 配置批量更新
	configEditForm(data) {
		return request('editBatch', data)
	},
	// 获取系统基础配置
	configSysBaseList(data) {
		return request('sysBaseList', data, 'get')
	}
}
