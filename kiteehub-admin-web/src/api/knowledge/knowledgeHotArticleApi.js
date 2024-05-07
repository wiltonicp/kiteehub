import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/knowledge/article/` + url, ...arg)

/**
 * 热门动态Api接口管理器
 *
 * @author Ranger
 * @date  2024/01/04 09:54
 **/
export default {
	// 获取热门动态分页
	knowledgeHotArticlePage(data) {
		return request('page', data)
	},
	// 获取热门动态列表
	knowledgeHotArticleList(data) {
		return request('list', data, 'get')
	},
	// 提交热门动态表单 edit为true时为编辑，默认为新增
	knowledgeHotArticleSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除热门动态
	knowledgeHotArticleDelete(data) {
		return request('delete', data)
	},
	// 获取热门动态详情
	knowledgeHotArticleDetail(data) {
		return request('detail', data, 'get')
	}
}
