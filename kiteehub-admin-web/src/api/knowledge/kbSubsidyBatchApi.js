import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/subsidy/` + url, ...arg)

/**
 * 补贴公示Api接口管理器
 *
 * @author Ranger
 * @date  2024/01/31 14:12
 **/
export default {
	// 获取补贴公示分页
	kbSubsidyBatchPage(data) {
		return request('page', data, 'get')
	},
	// 获取补贴公示列表
	kbSubsidyBatchList(data) {
		return request('list', data, 'get')
	},
	// 提交补贴公示表单 edit为true时为编辑，默认为新增
	kbSubsidyBatchSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	//导入
	kbSubsidyBatchImport(data){
		return request('import', data)
	},
	// 删除补贴公示
	kbSubsidyBatchDelete(data) {
		return request('delete', data)
	},
	// 获取补贴公示详情
	kbSubsidyBatchDetail(data) {
		return request('detail', data, 'get')
	},
	// 下载导入模板
	downloadImportSubsidyTemplate() {

    }
}
