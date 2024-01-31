<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="10">
					<a-form-item label="业务分类" name="category">
						<a-tree-select
							v-model:value="searchFormState.category"
							show-search
							style="width: 100%"
							:dropdown-style="{ maxHeight: '600px', overflow: 'auto' }"
							placeholder="请选择业务分类"
							allow-clear
							tree-default-expand-all
							:tree-data="categoryOptions"
							tree-node-filter-prop="name"
							:fieldNames="{ children: 'children', label: 'name', value: 'id' }"
						>
							<template #title="{ value: id, name }">
								<b v-if="id === 'parent 1-1'" style="color: #08c">sss</b>
								<template v-else>{{ name }}</template>
							</template>
						</a-tree-select>
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
			<template #operator >
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('kbWorkGuideAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('kbWorkGuideBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchKbWorkGuide"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'category'">
					<div>{{ findNamePathById(record.category).slice(1).join('--->>') }}</div>
				</template>
				<template v-if="column.dataIndex === 'flowChart'">
					<a-image
						:width="60"
						:src="record.flowChart.replace('http://10.10.15.36:8299', 'https://kb.vihacker.top/api')"
					/>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('kbWorkGuideEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['kbWorkGuideEdit', 'kbWorkGuideDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKbWorkGuide(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('kbWorkGuideDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="guide">
import tool from '@/utils/tool'
import Form from './form.vue'
import kbWorkGuideApi from '@/api/knowledge/kbWorkGuideApi'
import { message, TreeSelect } from 'ant-design-vue'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const SHOW_PARENT = TreeSelect.SHOW_PARENT
const categoryOptions = ref([])
const searchValue = ref('')
const columns = [
	{
		title: '分类',
		dataIndex: 'category'
	},
	{
		title: '事项代码',
		dataIndex: 'eventCode'
	},
	{
		title: '办理对象',
		dataIndex: 'handleObj'
	},
	{
		title: '办理依据',
		dataIndex: 'handleBasis'
	},
	{
		title: '办理部门',
		dataIndex: 'handleDept'
	},
	{
		title: '办理时限',
		dataIndex: 'handleTime'
	},
	{
		title: '流程图',
		dataIndex: 'flowChart'
	}
]

onMounted(() => {
	getCategoryOptions()
})
let getCategoryOptions = () => {
	const DICT_TYPE_TREE_DATA = tool.data.get('DICT_TYPE_TREE_DATA')
	if (DICT_TYPE_TREE_DATA) {
		categoryOptions.value = [DICT_TYPE_TREE_DATA.find((item) => item.dictValue === 'OPERATION_FLOW')]
	}
}
// 操作栏通过权限判断是否显示
if (hasPerm(['kbWorkGuideEdit', 'kbWorkGuideDelete'])) {
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
	return kbWorkGuideApi.kbWorkGuidePage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}

const findNamePathById = (id, data = categoryOptions.value) => {
	for (const node of data) {
		if (node.id === id) {
			return [node.name]
		} else if (node.children && node.children.length > 0) {
			const childResult = findNamePathById(id, node.children)
			if (childResult) {
				return [node.name, ...childResult]
			}
		}
	}
	return null
}

// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteKbWorkGuide = (record) => {
	let params = [
		{
			id: record.id
		}
	]
	kbWorkGuideApi.kbWorkGuideDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchKbWorkGuide = (params) => {
	kbWorkGuideApi.kbWorkGuideDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
</script>
