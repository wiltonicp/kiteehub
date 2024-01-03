<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="客服名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入客服名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="知识分类" name="knowledgeGather">
						<a-select
							v-model:value="searchFormState.kid"
							placeholder="请选择知识分类"
							:options="knowledgeGatherOptions"
						/>
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('knowledgeRobotAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('knowledgeRobotBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchKnowledgeRobot"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'kids'">
					<a-tag v-for="textValue in record.kids" :key="textValue" color="green">{{ $TOOL.dictTypeData('KNOWLEDGE_GATHER', textValue) }}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'model'">
					{{ $TOOL.dictTypeData('AI_MODEL', record.model) }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('knowledgeRobotEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['knowledgeRobotEdit', 'knowledgeRobotDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKnowledgeRobot(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('knowledgeRobotDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="robot">
	import tool from '@/utils/tool'
	import Form from './form.vue'
	import knowledgeRobotApi from '@/api/knowledge/knowledgeRobotApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '客服名称',
			dataIndex: 'name'
		},
		{
			title: '描述',
			dataIndex: 'description',
			ellipsis: true
		},
		{
			title: '知识库',
			dataIndex: 'kids',
			width: '160px'
		},
		{
			title: 'AI 模型',
			dataIndex: 'model'
		},
		{
			title: '采样温度',
			dataIndex: 'temperature',
			width: '90px'
		},
		{
			title: '回复上限',
			dataIndex: 'maxTokens',
			width: '90px'
		},
		{
			title: '提示词',
			dataIndex: 'prompt',
			ellipsis: true
		},
		{
			title: '对话开场白',
			dataIndex: 'prologue',
			ellipsis: true
		},
		{
			title: '修改时间',
			dataIndex: 'updateTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['knowledgeRobotEdit', 'knowledgeRobotDelete'])) {
		columns.push({
			title: '操作',
			dataIndex: 'action',
			align: 'center',
			width: '130px'
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
		return knowledgeRobotApi.knowledgeRobotPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteKnowledgeRobot = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		knowledgeRobotApi.knowledgeRobotDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchKnowledgeRobot = (params) => {
		knowledgeRobotApi.knowledgeRobotDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const knowledgeGatherOptions = tool.dictList('KNOWLEDGE_GATHER')
</script>
