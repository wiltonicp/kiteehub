import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/robot/` + url, ...arg)

/**
 * 智能客服Api接口管理器
 *
 * @author Ranger
 * @date  2024/01/03 11:44
 **/
export default {
	// 获取智能客服分页
	knowledgeRobotPage(data) {
		return request('page', data, 'get')
	},
	// 获取智能客服列表
	knowledgeRobotList(data) {
		return request('list', data, 'get')
	},
	// 提交智能客服表单 edit为true时为编辑，默认为新增
	knowledgeRobotSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除智能客服
	knowledgeRobotDelete(data) {
		return request('delete', data)
	},
	// 获取智能客服详情
	knowledgeRobotDetail(data) {
		return request('detail', data, 'get')
	}
}
