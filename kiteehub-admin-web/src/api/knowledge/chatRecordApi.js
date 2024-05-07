import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/chatai/` + url, ...arg)

/**
 * 客服记录Api接口管理器
 *
 * @author Ranger
 * @date  2024/01/23 17:00
 **/
export default {
	// 获取客服记录分页
	chatRecordPage(data) {
		return request('page', data, 'get')
	},
	// 获取客服记录列表
	chatRecordList(data) {
		return request('list', data, 'get')
	},
	// 提交客服记录表单 edit为true时为编辑，默认为新增
	chatRecordSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除客服记录
	chatRecordDelete(data) {
		return request('delete', data)
	},
	// 获取客服记录详情
	chatRecordDetail(data) {
		return request('detail', data, 'get')
	}
}
