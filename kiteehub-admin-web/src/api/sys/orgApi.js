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

const request = (url, ...arg) => baseRequest(`/sys/org/${url}`, ...arg)
/**
 * 机构
 *
 * @author yubaoshan
 * @date 2022-09-22 22:33:20
 */
export default {
	// 获取组织分页
	orgPage(data) {
		return request('page', data, 'get')
	},
	// 获取组织列表
	orgList(data) {
		return request('list', data, 'get')
	},
	// 获取组织树
	orgTree(data) {
		return request('tree', data, 'get')
	},
	// 提交表单 edit为true时为编辑，默认为新增
	submitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除组织
	orgDelete(data) {
		return request('delete', data)
	},
	// 获取组织详情
	orgDetail(data) {
		return request('detail', data, 'get')
	},
	// 获取组织树选择器
	orgOrgTreeSelector(data) {
		return request('orgTreeSelector', data, 'get')
	},
	// 获取用户选择器
	orgUserSelector(data) {
		return request('userSelector', data, 'get')
	}
}
