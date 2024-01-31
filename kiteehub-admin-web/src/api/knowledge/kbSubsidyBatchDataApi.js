import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/subsidy/` + url, ...arg)

/**
 * 待遇补贴批次详情Api接口管理器
 *
 * @author Ranger
 * @date  2024/01/31 15:06
 **/
export default {
	// 获取待遇补贴批次详情分页
	kbSubsidyBatchDataPage(data) {
		return request('page', data, 'get')
	},
	// 获取待遇补贴批次详情列表
	kbSubsidyBatchDataList(data) {
		return request('list', data, 'get')
	},
	// 提交待遇补贴批次详情表单 edit为true时为编辑，默认为新增
	kbSubsidyBatchDataSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除待遇补贴批次详情
	kbSubsidyBatchDataDelete(data) {
		return request('delete', data)
	},
	// 获取待遇补贴批次详情详情
	kbSubsidyBatchDataDetail(data) {
		return request('detail', data, 'get')
	}
}
