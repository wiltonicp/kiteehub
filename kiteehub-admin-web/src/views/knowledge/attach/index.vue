<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="知识名称" name="docName">
						<a-input v-model:value="searchFormState.docName" placeholder="请输入知识名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="状态" name="gatherState">
						<a-select
							v-model:value="searchFormState.gatherState"
							placeholder="请选择状态"
							:options="gatherStateOptions"
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
			v-if="sign"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('knowledgeAttachAdd')">
						<template #icon><plus-outlined /></template>
						新增/导入
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('knowledgeAttachBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchKnowledgeAttach"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'gatherState'">
					<a-tag color="grey" v-if="record.gatherState == 'PROGRESSING'">{{
						$TOOL.dictTypeData('Gather', record.gatherState)
					}}</a-tag>
					<a-tag color="green" v-else>{{ $TOOL.dictTypeData('Gather', record.gatherState) }}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="examine(record)">查看</a>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('knowledgeAttachEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['knowledgeAttachEdit', 'knowledgeAttachDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKnowledgeAttach(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('knowledgeAttachDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />

	<Details ref="detailsRef" @successful="detailsRef.refresh(true)" />
</template>

<script setup name="attach">
import router from '@/router'
import tool from '@/utils/tool'
import Form from './form.vue'
import Details from './details.vue'
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'
let searchFormState = reactive({})
const searchFormRef = ref()
const typeUrl = ref('')
const table = ref()
const formRef = ref()
const detailsRef = ref()
const sign = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const columns = [
	{
		title: '知识名称',
		dataIndex: 'docName'
	},
	{
		title: '知识类型',
		dataIndex: 'docType'
	},

	{
		title: '数据总量',
		dataIndex: 'totalData'
	},
	{
		title: '索引模型',
		dataIndex: 'indexModel'
	},
	{
		title: '知识处理模型',
		dataIndex: 'fileModel'
	},
	{
		title: '状态',
		dataIndex: 'gatherState'
	},
	{
		title: '最后修改时间',
		dataIndex: 'updateTime'
	}
]
// 操作栏通过权限判断是否显示
if (hasPerm(['knowledgeAttachEdit', 'knowledgeAttachDelete'])) {
	columns.push({
		title: '操作',
		dataIndex: 'action',
		align: 'center',
		width: '180px'
	})
}
const selectedRowKeys = ref([])

onMounted(() => {
	getParameUrl()
})

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
	parameter.kid = typeUrl.value
	console.log(parameter, 'parameter')
	return knowledgeAttachApi.knowledgeAttachPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteKnowledgeAttach = (record) => {
	let params = [
		{
			id: record.id
		}
	]
	knowledgeAttachApi.knowledgeAttachDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 查看
const examine = (record) => {
	console.log(record, 'examine(record)')
	detailsRef.value.onOpen(record)
}
// 批量删除
const deleteBatchKnowledgeAttach = (params) => {
	knowledgeAttachApi.knowledgeAttachDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
watch(
	() => router.currentRoute.value.path,
	(newValue, oldValue) => {
		console.log('watch', newValue)
		if (oldValue) {
			getParameUrl()
		}
	},
	{ immediate: true }
)
// url 参数
const getParameUrl = async () => {
	sign.value = false
	const url = window.location.href // 获取当前页面的URL
	const lastSegment = url.substring(url.lastIndexOf('/') + 1) // 获取最后一个斜杠后的内容
	typeUrl.value = lastSegment
	let parameter = {
		current: 1,
		size: 10,
		kid: typeUrl.value
	}
	await loadData(parameter)
	sign.value = true
}
const gatherStateOptions = tool.dictList('Gather')
</script>
