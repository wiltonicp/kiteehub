import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/robotpreset/` + url, ...arg)

/**
 * 机器人预设问答Api接口管理器
 *
 * @author Ranger
 * @date  2024/02/02 10:39
 **/
export default {
	// 获取机器人预设问答分页
	knowledgeRobotPresetPage(data) {
		return request('page', data, 'get')
	},
	// 获取机器人预设问答列表
	knowledgeRobotPresetList(data) {
		return request('list', data, 'get')
	},
	// 提交机器人预设问答表单 edit为true时为编辑，默认为新增
	knowledgeRobotPresetSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除机器人预设问答
	knowledgeRobotPresetDelete(data) {
		return request('delete', data)
	},
	// 获取机器人预设问答详情
	knowledgeRobotPresetDetail(data) {
		return request('detail', data, 'get')
	}
}
