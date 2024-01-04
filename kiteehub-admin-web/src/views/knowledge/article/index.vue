<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="知识类别" name="kid">
						<a-select v-model:value="searchFormState.kid" placeholder="请选择知识类别" :options="kidOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="标题" name="title">
						<a-input v-model:value="searchFormState.title" placeholder="请输入标题" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
		<s-table
			ref="table"
			:columns="columns"
			:data="loadData"
			:alert="options.alert.show"
			bordered
			:row-key="(record) => record.id"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('knowledgeHotArticleAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('knowledgeHotArticleBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchKnowledgeHotArticle"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'kid'">
					{{ $TOOL.dictTypeData('KNOWLEDGE_GATHER', record.kid) }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('knowledgeHotArticleEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['knowledgeHotArticleEdit', 'knowledgeHotArticleDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKnowledgeHotArticle(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('knowledgeHotArticleDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="article">
	import tool from '@/utils/tool'
	import Form from './form.vue'
	import knowledgeHotArticleApi from '@/api/knowledge/knowledgeHotArticleApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '知识类别',
			dataIndex: 'kid'
		},
		{
			title: '标题',
			dataIndex: 'title'
		},
		{
			title: '正文',
			dataIndex: 'content'
		},
		{
			title: '创建时间',
			dataIndex: 'createTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['knowledgeHotArticleEdit', 'knowledgeHotArticleDelete'])) {
		columns.push({
			title: '操作',
			dataIndex: 'action',
			align: 'center',
			width: '150px'
		})
	}
	const selectedRowKeys = ref([])
	// 列表选择配置
	const options = {
		// columns数字类型字段加入 needTotal: true 可以勾选自动算账
		alert: {
			show: true,
			clear: () => {
				selectedRowKeys.value = ref([])
			}
		},
		rowSelection: {
			onChange: (selectedRowKey, selectedRows) => {
				selectedRowKeys.value = selectedRowKey
			}
		}
	}
	const loadData = (parameter) => {
		const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
		return knowledgeHotArticleApi.knowledgeHotArticlePage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteKnowledgeHotArticle = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		knowledgeHotArticleApi.knowledgeHotArticleDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchKnowledgeHotArticle = (params) => {
		knowledgeHotArticleApi.knowledgeHotArticleDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const kidOptions = tool.dictList('KNOWLEDGE_GATHER')
</script>
