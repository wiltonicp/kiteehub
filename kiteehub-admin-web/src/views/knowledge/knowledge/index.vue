<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="名称" name="kname">
						<a-input v-model:value="searchFormState.kname" placeholder="请输入名称" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('knowledgeAdd')">
						<template #icon><plus-outlined /></template>
						新建
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('knowledgeBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchKnowledge"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('knowledgeEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['knowledgeEdit', 'knowledgeDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKnowledge(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('knowledgeDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="knowledge">
	import Form from './form.vue'
	import knowledgeApi from '@/api/knowledge/knowledgeApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: 'KID',
			dataIndex: 'kid'
		},
		{
			title: '名称',
			dataIndex: 'kname'
		},
    {
      title: '官网',
      dataIndex: 'website'
    },
		// {
		// 	title: '区域',
		// 	dataIndex: 'area'
		// },
		// {
		// 	title: '险别ID',
		// 	dataIndex: 'insureTypeId'
		// },
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['knowledgeEdit', 'knowledgeDelete'])) {
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
		return knowledgeApi.knowledgePage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteKnowledge = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		knowledgeApi.knowledgeDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchKnowledge = (params) => {
		knowledgeApi.knowledgeDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
