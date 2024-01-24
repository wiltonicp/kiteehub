<template>
	<a-row>
		<a-col :span="5">
			<a-card class="cardImp" :bordered="false" :loading="cardLoading">
				<a-tree
					v-if="treeData.length > 0"
					v-model:expandedKeys="defaultExpandedKeys"
					:tree-data="treeData"
					:field-names="treeFieldNames"
					@select="treeSelect"
				>
				</a-tree>
				<a-empty v-else :image="Empty.PRESENTED_IMAGE_SIMPLE" />
			</a-card>
		</a-col>
		<a-col :span="19">
			<a-card :bordered="false">
				<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
					<a-row :gutter="24">
						<a-col :span="10">
							<a-form-item label="知识名称" name="docName">
								<a-input v-model:value="searchFormState.docName" placeholder="请输入知识名称" />
							</a-form-item>
						</a-col>
						<a-col :span="5">
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
				<div>
					<a-form-item label="保险类型" name="docName">
						<a-tabs v-model:activeKey="activeKey" type="card" @change="changeTabs">
							<a-tab-pane :tab="`${item.name} (${item.value || 0})`" v-for="item in typeList" :key="item.dictValue">
							</a-tab-pane>
						</a-tabs>
					</a-form-item>
				</div>

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
					<template #operator>
						<a-space>
							<a-button type="primary" @click="formRefOpen" v-if="hasPerm('knowledgeAttachAdd')">
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
								<a @click="formRef.onOpen(typeUrl, record)" v-if="hasPerm('knowledgeAttachEdit')">编辑</a>
								<a-divider type="vertical" v-if="hasPerm(['knowledgeAttachEdit', 'knowledgeAttachDelete'], 'and')" />
								<a-popconfirm title="确定要删除吗？" @confirm="deleteKnowledgeAttach(record)">
									<a-button type="link" danger size="small" v-if="hasPerm('knowledgeAttachDelete')">删除</a-button>
								</a-popconfirm>
							</a-space>
						</template>
					</template>
				</s-table>
			</a-card>
		</a-col>
	</a-row>
	<Form ref="formRef" @successful="successfulFormRef" />

	<Details ref="detailsRef" @successful="successfulDetailsRef" />
</template>

<script setup name="attach">
import router from '@/router'
import tool from '@/utils/tool'
import Form from './form.vue'
import Details from './details.vue'
import knowledgeAttachApi from '@/api/knowledge/knowledgeAttachApi'
import { Empty, message, TreeSelect } from 'ant-design-vue'
const SHOW_PARENT = TreeSelect.SHOW_PARENT
let searchFormState = reactive({})
const searchFormRef = ref()
const typeUrl = ref('')
const table = ref()
const formRef = ref()
const detailsRef = ref()
const sign = ref(false)
const typeList = ref([])
const activeKey = ref('')
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

// 默认展开的节点
let defaultExpandedKeys = ref([])
const treeData = ref([])
// 替换treeNode 中 title,key,children
const treeFieldNames = { children: 'children', title: 'label', key: 'id' }
const cardLoading = ref(true)

onMounted(async () => {
	await getType()
	loadTreeData()
	sign.value = true
})
// 类型
let getType = () => {
	try {
		return new Promise((resolve, reject) => {
			setTimeout(async () => {
				try {
					console.log('类型', await tool.data.get('DICT_TYPE_TREE_DATA'))
					const DICT_TYPE_TREE_DATA = await tool.data.get('DICT_TYPE_TREE_DATA')
					typeList.value =
						DICT_TYPE_TREE_DATA && DICT_TYPE_TREE_DATA.find((item) => item.id === '1742384659893030914').children
					activeKey.value = typeList.value[0].dictValue
					resolve()
				} catch (error) {
					reject(error)
				}
			}, 500)
		})
	} catch (error) {
		console.log(error)
	}
}

// 切换类型
let changeTabs = (val) => {
	sign.value = false
	activeKey.value = val
	setTimeout(() => {
		sign.value = true
	}, 100)
}
// 表单打开
let formRefOpen = () => {
	formRef.value.onOpen(activeKey.value)
	formRef.value.getAreaIds(searchFormState.areaIds)
}
// 表单更新
let successfulFormRef = () => {
	console.log('表单更新')
	loadTreeData()
	sign.value = false
	setTimeout(() => {
		sign.value = true
	}, 100)
}
// 详情更新
let successfulDetailsRef = () => {
	detailsRef.refresh(true)
}

// 加载左侧的树
const loadTreeData = () => {
	knowledgeAttachApi
		.cityTree()
		.then((res) => {
			cardLoading.value = false
			count()
			if (res !== null) {
				treeData.value = res
				// 默认展开2级
				treeData.value.forEach((item) => {
					// 因为0的顶级
					if (item.parentId === '0') {
						defaultExpandedKeys.value.push(item.id)
						// 取到下级ID
						if (item.children) {
							item.children.forEach((items) => {
								defaultExpandedKeys.value.push(items.id)
							})
						}
					}
				})
			}
		})
		.finally(() => {
			cardLoading.value = false
		})
}

// 点击树查询
const treeSelect = (selectedKeys) => {
	console.log(selectedKeys, 'selectedKeys')
	sign.value = false
	if (selectedKeys.length > 0) {
		searchFormState.areaIds = selectedKeys.toString()
	} else {
		delete searchFormState.parentId
	}
	count()
	setTimeout(() => {
		sign.value = true
	}, 100)
}

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
	parameter.kid = activeKey.value
	parameter.areaIds = searchFormState.areaIds || ''
	console.log(parameter, 'parameter')
	return knowledgeAttachApi.knowledgeAttachPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}

let count = async () => {
	let parame = {
		areaId: searchFormState.areaIds
	}
	console.log(parame, 'parame')
	let res = await knowledgeAttachApi.count(parame)
	typeList.value = typeList.value.map(item=>{
		return {
			...item,
			value:0
		}
	})
	// 遍历 b 数组
	res.forEach((itemB) => {
		// 找到 a 数组中与 b 数组对应 id 的对象
		var matchedObj = typeList.value.find((itemA) => {
			return itemA.dictValue === itemB.name
		})
		// 如果找到了对应的对象，则将 b 数组的 num 值赋值给 a 数组的对象
		if (matchedObj) {
			matchedObj.value = itemB.value
		}
	})
}
// 重置
const reset = () => {
	searchFormState.areaIds = ''
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
	detailsRef.value.onOpen(record)
}
// 批量删除
const deleteBatchKnowledgeAttach = (params) => {
	knowledgeAttachApi.knowledgeAttachDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
watch(searchFormState.areaId, () => {
	console.log('areaIds', searchFormState.areaId)
})

const gatherStateOptions = tool.dictList('Gather')
</script>
