<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="消息类型" name="msgType">
						<a-select v-model:value="searchFormState.msgType" placeholder="请选择消息类型" :options="msgTypeOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="客服ID" name="robotId">
						<a-input v-model:value="searchFormState.robotId" placeholder="请输入客服ID" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="消息内容" name="message">
						<a-input v-model:value="searchFormState.message" placeholder="请输入消息内容" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="创建时间" name="createTime">
						<a-range-picker v-model:value="searchFormState.createTime" show-time />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
					<a @click="toggleAdvanced" style="margin-left: 8px">
						{{ advanced ? '收起' : '展开' }}
						<component :is="advanced ? 'up-outlined' : 'down-outlined'"/>
					</a>
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('chatRecordAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('chatRecordBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchChatRecord"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'msgType'">
					{{ $TOOL.dictTypeData('CHAT_MSG_TYPE', record.msgType) }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('chatRecordEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['chatRecordEdit', 'chatRecordDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteChatRecord(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('chatRecordDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="chatai">
	import tool from '@/utils/tool'
	import Form from './form.vue'
	import chatRecordApi from '@/api/knowledge/chatRecordApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	// 查询区域显示更多控制
	const advanced = ref(false)
	const toggleAdvanced = () => {
		advanced.value = !advanced.value
	}
	const columns = [
		{
			title: '消息类型',
			dataIndex: 'msgType'
		},
		{
			title: '客服ID',
			dataIndex: 'robotId'
		},
		{
			title: '消息内容',
			dataIndex: 'message'
		},
		{
			title: '创建时间',
			dataIndex: 'createTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['chatRecordEdit', 'chatRecordDelete'])) {
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
		// createTime范围查询条件重载
		if (searchFormParam.createTime) {
			searchFormParam.startCreateTime = searchFormParam.createTime[0]
			searchFormParam.endCreateTime = searchFormParam.createTime[1]
			delete searchFormParam.createTime
		}
		return chatRecordApi.chatRecordPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteChatRecord = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		chatRecordApi.chatRecordDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchChatRecord = (params) => {
		chatRecordApi.chatRecordDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const msgTypeOptions = tool.dictList('CHAT_MSG_TYPE')
</script>
