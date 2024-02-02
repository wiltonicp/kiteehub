<template>
	<xn-form-container :title="record.docName" :width="900" :visible="visible" :destroy-on-close="true" @close="onClose">
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
			<template #bodyCell="{ column, record }">
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

		<template #footer>
			<div class="pagination">
				<a-pagination
					v-model:current="current"
					v-model:pageSize="size"
					show-size-changer
					:total="totalVal"
					@showSizeChange="onShowSizeChange"
				/>
			</div>
		</template>
	</xn-form-container>
</template>

<script setup name="knowledgeAttachForm">
import { ExclamationCircleOutlined } from '@ant-design/icons-vue'
import { Modal } from 'ant-design-vue'
import { createVNode } from 'vue'
import tool from '@/utils/tool'
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import kbSubsidyBatchApi from '@/api/knowledge/kbSubsidyBatchApi'
const size = ref(10)
const current = ref(1)
const totalVal = ref(0)
const recordsList = ref([])
const open = ref(false)
let searchFormState = reactive({})
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const columns = [
	{
		title: 'a',
		dataIndex: 'subsidyType'
	},
	{
		title: 'b',
		dataIndex: 'batch'
	},
	{
		title: 'c',
		dataIndex: 'updateTime'
	}
]

let dataObj = reactive({
	record: {}
})

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
	return kbSubsidyBatchApi.kbSubsidyBatchDataPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}

const onShowSizeChange = (currentVal, pageSize) => {
	console.log(currentVal, pageSize)
	size.value = pageSize
	current.value = currentVal
}
// watch(size, () => {
// 	console.log('size', size.value)
// 	knowledAttachChunk()
// })
// watch(current, () => {
// 	console.log('current', current.value)
// 	knowledAttachChunk()
// })
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })

// 打开抽屉
const onOpen = (record) => {
	console.log('打开抽屉', record)
	dataObj.record = record
	visible.value = true
	// knowledAttachChunk()
}
// 关闭抽屉
const onClose = () => {
	visible.value = false
}

// const knowledAttachChunk = async () => {
// 	let parame = {
// 		id: dataObj.record.id,
// 		current: current.value,
// 		size: size.value
// 	}
// 	let { total, records } = await knowledgeAttachApi.knowledAttachChunk(parame)
// 	totalVal.value = total
// 	recordsList.value = records
// }

// 删除某一项
const del = (item) => {
	Modal.confirm({
		title: '提示',
		icon: createVNode(ExclamationCircleOutlined),
		content: `是否删除ID为：${item.id}的文本？`,
		okText: '确认',
		cancelText: '取消',
		async onOk() {
			await knowledgeAttachApi.knowledAttachDelete([
				{
					id: item.id
				}
			])
			knowledAttachChunk()
		}
	})
}

const { record } = toRefs(dataObj)

// 抛出函数
defineExpose({
	onOpen
})
</script>

<style  scoped>
.chunk {
	height: 130px;
	overflow: auto;
}
*::-webkit-scrollbar {
	/*滚动条整体样式*/
	width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
	height: 1px;
}
*::-webkit-scrollbar-thumb {
	/*滚动条里面深色条*/
	border-radius: 10px;
	box-shadow: inset 0 0 5px rgba(236, 236, 236, 0.1);
	background: #ccc;
}
*::-webkit-scrollbar-track {
	/*滚动条里面轨道*/
	box-shadow: inset 0 0 5px rgba(236, 236, 236, 0.1);
	border-radius: 10px;
	background: #ededed;
}
</style>

