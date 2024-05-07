<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="客服" name="robotId">
						<a-select v-model:value="searchFormState.robotId" placeholder="请选择客服" :options="robotIdOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="问题" name="question">
						<a-input v-model:value="searchFormState.question" placeholder="请输入问题" />
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
          <a-button type="primary" @click="formRef.onOpen('',robotIdOptions)" v-if="hasPerm('knowledgeRobotPresetAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('knowledgeRobotPresetBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchKnowledgeRobotPreset"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'robotId'">
          <a-tag color="green">{{getRobotName(record.robotId)}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record,robotIdOptions)" v-if="hasPerm('knowledgeRobotPresetEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['knowledgeRobotPresetEdit', 'knowledgeRobotPresetDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteKnowledgeRobotPreset(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('knowledgeRobotPresetDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="robotpreset">
	import tool from '@/utils/tool'
	import Form from './form.vue'
	import knowledgeRobotPresetApi from '@/api/knowledge/knowledgeRobotPresetApi'
  import knowledgeRobotApi from "@/api/knowledge/knowledgeRobotApi";
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
  const robotIdOptions = ref([])
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '客服',
			dataIndex: 'robotId'
		},
		{
			title: '问题',
			dataIndex: 'question'
		},
		{
			title: '答案',
			dataIndex: 'answer'
		},
		{
			title: '创建时间',
			dataIndex: 'createTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['knowledgeRobotPresetEdit', 'knowledgeRobotPresetDelete'])) {
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
  onMounted(() => {
    knowledgeRobotList()
  })
	const loadData = (parameter) => {
		const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
		return knowledgeRobotPresetApi.knowledgeRobotPresetPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
  const getRobotName = (id) => {
    const name = robotIdOptions.value.filter((item) => item.value === id)[0]
    return name.label
  }
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteKnowledgeRobotPreset = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		knowledgeRobotPresetApi.knowledgeRobotPresetDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchKnowledgeRobotPreset = (params) => {
		knowledgeRobotPresetApi.knowledgeRobotPresetDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const knowledgeRobotList = () => {
    knowledgeRobotApi.knowledgeRobotList().then((data) =>{
      robotIdOptions.value = data
    })
  }
</script>
