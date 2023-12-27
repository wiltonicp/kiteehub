import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/knowledge/` + url, ...arg)

/**
 * 知识库Api接口管理器
 *
 * @author Ranger
 * @date  2023/12/27 10:20
 **/
export default {
	// 获取知识库分页
	knowledgePage(data) {
		return request('page', data, 'get')
	},
	// 获取知识库列表
	knowledgeList(data) {
		return request('list', data, 'get')
	},
	// 提交知识库表单 edit为true时为编辑，默认为新增
	knowledgeSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除知识库
	knowledgeDelete(data) {
		return request('delete', data)
	},
	// 获取知识库详情
	knowledgeDetail(data) {
		return request('detail', data, 'get')
	}
}
