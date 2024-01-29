<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="主题" name="subject">
						<a-input v-model:value="searchFormState.subject" placeholder="请输入主题" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('knowledgeNoticeAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('knowledgeNoticeBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchKnowledgeNotice"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('knowledgeNoticeEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['knowledgeNoticeEdit', 'knowledgeNoticeDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKnowledgeNotice(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('knowledgeNoticeDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="notice">
	import Form from './form.vue'
	import knowledgeNoticeApi from '@/api/knowledge/knowledgeNoticeApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '主题',
			dataIndex: 'subject'
		},
		{
			title: '正文',
			dataIndex: 'content'
		},
		{
			title: '扩展信息',
			dataIndex: 'extJson'
		},
		{
			title: '创建时间',
			dataIndex: 'createTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['knowledgeNoticeEdit', 'knowledgeNoticeDelete'])) {
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
		return knowledgeNoticeApi.knowledgeNoticePage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteKnowledgeNotice = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		knowledgeNoticeApi.knowledgeNoticeDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchKnowledgeNotice = (params) => {
		knowledgeNoticeApi.knowledgeNoticeDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
