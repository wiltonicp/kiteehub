import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/notice/` + url, ...arg)

/**
 * 通知公告Api接口管理器
 *
 * @author Ranger
 * @date  2024/01/29 14:39
 **/
export default {
	// 获取通知公告分页
	knowledgeNoticePage(data) {
		return request('page', data, 'get')
	},
	// 获取通知公告列表
	knowledgeNoticeList(data) {
		return request('list', data, 'get')
	},
	// 提交通知公告表单 edit为true时为编辑，默认为新增
	knowledgeNoticeSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除通知公告
	knowledgeNoticeDelete(data) {
		return request('delete', data)
	},
	// 获取通知公告详情
	knowledgeNoticeDetail(data) {
		return request('detail', data, 'get')
	}
}
