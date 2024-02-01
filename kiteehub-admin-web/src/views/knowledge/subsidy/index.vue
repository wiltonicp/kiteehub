<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="补贴类型" name="subsidyType">
						<a-select
							v-model:value="searchFormState.subsidyType"
							placeholder="请选择补贴类型"
							:options="subsidyTypeOptions"
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('kbSubsidyBatchAdd')">
						<template #icon><plus-outlined /></template>
						导入
					</a-button>
					<!--          <xn-batch-delete-->
					<!--              v-if="hasPerm('kbSubsidyBatchBatchDelete')"-->
					<!--              :selectedRowKeys="selectedRowKeys"-->
					<!--              @batchDelete="deleteBatchKbSubsidyBatch"-->
					<!--          />-->
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'subsidyType'">
					{{ $TOOL.dictTypeData('SUBSIDY_TYPE', record.subsidyType) }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="examine(record)">查看</a>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('kbSubsidyBatchEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['kbSubsidyBatchEdit', 'kbSubsidyBatchDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKbSubsidyBatch(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('kbSubsidyBatchDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />

	<Details ref="detailsRef" @successful="successfulDetailsRef" />
</template>

<script setup name="subsidy">
import tool from '@/utils/tool'
import Form from './form.vue'
import Details from './details.vue'
import kbSubsidyBatchApi from '@/api/knowledge/kbSubsidyBatchApi'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const detailsRef = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const columns = [
	{
		title: '补贴类型',
		dataIndex: 'subsidyType'
	},
	{
		title: '批次',
		dataIndex: 'batch'
	},
	{
		title: '最新时间',
		dataIndex: 'updateTime'
	}
]
// 操作栏通过权限判断是否显示
if (hasPerm(['kbSubsidyBatchEdit', 'kbSubsidyBatchDelete'])) {
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
	return kbSubsidyBatchApi.kbSubsidyBatchPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}

// 查看
const examine = (record) => {
  console.log(record,'record')
	detailsRef.value.onOpen(record)
}

// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteKbSubsidyBatch = (record) => {
	let params = [
		{
			id: record.id
		}
	]
	kbSubsidyBatchApi.kbSubsidyBatchDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchKbSubsidyBatch = (params) => {
	kbSubsidyBatchApi.kbSubsidyBatchDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
const subsidyTypeOptions = tool.dictList('SUBSIDY_TYPE')
</script>
