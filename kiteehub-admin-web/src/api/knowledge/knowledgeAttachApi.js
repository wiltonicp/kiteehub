import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/attach/` + url, ...arg)


const requestSelf = (url, ...arg) => baseRequest(`/knowledge/attach-chunk/` + url, ...arg)

/**
 * 知识库附件Api接口管理器
 *
 * @author Ranger
 * @date  2023/12/27 14:00
 **/
export default {
	// 获取知识库附件分页
	knowledgeAttachPage(data) {
		return request('page', data)
	},
	// 获取知识库附件列表
	knowledgeAttachList(data) {
		return request('list', data, 'get')
	},
	// 提交知识库附件表单 edit为true时为编辑，默认为新增
	knowledgeAttachSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除知识库附件
	knowledgeAttachDelete(data) {
		return request('delete', data)
	},
	// 获取知识库附件详情
	knowledgeAttachDetail(data) {
		return request('detail', data, 'get')
	},
	
	// 获取知识库附件分页
	knowledAttachChunk(data) {
		return requestSelf('page', data, 'get')
	},
	// 删除知识库分片
	knowledAttachDelete(data) {
		return requestSelf('delete', data)
	},
}
