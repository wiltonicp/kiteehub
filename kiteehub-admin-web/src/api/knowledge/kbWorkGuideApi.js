import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/guide/` + url, ...arg)

/**
 * 业务指南Api接口管理器
 *
 * @author Ranger
 * @date  2024/01/29 14:15
 **/
export default {
	// 获取业务指南分页
	kbWorkGuidePage(data) {
		return request('page', data, 'get')
	},
	// 获取业务指南列表
	kbWorkGuideList(data) {
		return request('list', data, 'get')
	},
	// 提交业务指南表单 edit为true时为编辑，默认为新增
	kbWorkGuideSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除业务指南
	kbWorkGuideDelete(data) {
		return request('delete', data)
	},
	// 获取业务指南详情
	kbWorkGuideDetail(data) {
		return request('detail', data, 'get')
	}
}
